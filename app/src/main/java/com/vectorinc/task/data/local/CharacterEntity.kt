package com.vectorinc.task.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    val img: String,
    val name: String,
    @PrimaryKey val _id: Int? = null
)
