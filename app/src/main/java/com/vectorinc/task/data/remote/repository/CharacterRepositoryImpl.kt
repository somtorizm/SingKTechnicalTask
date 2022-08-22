package com.vectorinc.task.data.remote.repository

import com.vectorinc.task.data.local.Database
import com.vectorinc.task.data.remote.ApiService
import com.vectorinc.task.data.remote.mapper.toCharacter
import com.vectorinc.task.data.remote.mapper.toCharacterEntity
import com.vectorinc.task.domain.model.Character
import com.vectorinc.task.domain.repository.CharacterRepository
import com.vectorinc.task.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: ApiService,
    db : Database
) : CharacterRepository {

    private val dao = db.dao

    override suspend fun getCharacter(query : String): Flow<Resource<List<Character>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCharacters = dao.readCharactersList(query)

            emit(Resource.Success(data = localCharacters.map { it.toCharacter() }))

            val isDbEmpty = localCharacters.isEmpty()
            if(localCharacters.isEmpty() && query.isNotEmpty()){
                emit(Resource.Success(data = emptyList()))
                return@flow
            }

            val loadFromCache = !isDbEmpty
            if (loadFromCache){
                emit(Resource.Loading(false))

                return@flow
            }


            val remoteCharacters = try {
                api.getCharacters()


            } catch (e: IOException) {

                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))

                null
            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error(e.message().toString()))
                null
            }

            remoteCharacters?.let {

                dao.clearDatabase()
                dao.insertCharacterList(it.body()!!.map { it.toCharacterEntity() })

                val data = dao
                    .readCharactersList("")
                    .map { it.toCharacter() }
                emit(
                    Resource.Success(data = data
                ))
                emit(Resource.Loading(false))

            }


        }

    }
}