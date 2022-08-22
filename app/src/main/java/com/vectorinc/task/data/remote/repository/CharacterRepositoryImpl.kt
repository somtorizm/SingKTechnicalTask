package com.vectorinc.task.data.remote.repository

import com.vectorinc.task.data.remote.ApiService
import com.vectorinc.task.data.remote.dto.CharacterDto
import com.vectorinc.task.data.remote.mapper.toCharacter
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
) : CharacterRepository {


    override suspend fun getCharacter(): Flow<Resource<List<Character>>> {
        return flow {

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

            remoteCharacters.let {

                val data = it?.body()?.map { items -> items.toCharacter() }
                emit(Resource.Success(data = data))

            }


        }

    }
}