package com.vectorinc.task.repository

import com.vectorinc.task.domain.model.Character
import com.vectorinc.task.domain.repository.CharacterRepository
import com.vectorinc.task.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository(private val characters: List<Character>) : CharacterRepository {
    override suspend fun getCharacter(query: String): Flow<Resource<List<Character>>> {
        return flow {
            emit(Resource.Success(characters))
        }
    }
}