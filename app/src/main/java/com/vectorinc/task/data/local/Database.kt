package com.vectorinc.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterEntity::class], version = 1,
)
abstract class Database : RoomDatabase() {
    abstract val dao: CharacterDao
}