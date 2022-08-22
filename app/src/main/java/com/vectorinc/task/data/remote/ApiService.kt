package com.vectorinc.task.data.remote

import com.vectorinc.task.data.remote.dto.CharacterDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("api/characters")
    suspend fun getCharacters(): Response<List<CharacterDto>>
}