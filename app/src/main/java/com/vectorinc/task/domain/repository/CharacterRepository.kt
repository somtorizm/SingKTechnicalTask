package com.vectorinc.task.domain.repository

import com.vectorinc.task.domain.model.Character
import com.vectorinc.task.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacter(): Flow<Resource<List<Character>>>

}