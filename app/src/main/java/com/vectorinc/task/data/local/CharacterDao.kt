package com.vectorinc.task.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CharacterDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterList(
        characterEntity: List<CharacterEntity>
    )

    @Query("DELETE FROM CharacterEntity")
    suspend fun clearDatabase()

    @Query(
        """
          SELECT * FROM  CharacterEntity
          WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR 
          UPPER(:query) == name
        """
    )
    suspend fun readCharactersList(query: String): List<CharacterEntity>


}