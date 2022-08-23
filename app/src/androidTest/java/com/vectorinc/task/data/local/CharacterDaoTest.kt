package com.vectorinc.task.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CharacterDaoTest {
    private lateinit var database: Database
    private lateinit var dao: CharacterDao
    lateinit var fakeItems: ArrayList<CharacterEntity>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        fakeItems = ArrayList()
        var characters = CharacterEntity(name = "Victor", img = "https://www.google.com")
        fakeItems.add(characters)
        characters = CharacterEntity(name = "Somto", img = "https://www.google.comx")
        fakeItems.add(characters)
        characters = CharacterEntity(name = "Hector1", img = "https://www.stackOverflow")
        fakeItems.add(characters)
        characters = CharacterEntity(name = "Somito", img = "https://www.google.comx")
        fakeItems.add(characters)


        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCharacter() = runBlocking {
        val expected = 4
        dao.insertCharacterList(fakeItems)
        val actual = dao.readCharactersList("").size
        assertEquals(expected, actual)
    }

    @Test
    fun readCharacter() = runBlocking {

    dao.insertCharacterList(fakeItems)
        val expectedNameAtIndex = "Hector1"
        val actualName = dao.readCharactersList("He")[0].name
        assertNotNull(actualName)
        assertEquals(expectedNameAtIndex, actualName)
    }

    @Test
    fun deleteCharacter() = runBlocking {
        dao.insertCharacterList(fakeItems)
        dao.clearDatabase()
        val expected = 0
        val actual = dao.readCharactersList("").size
        assertNotNull(actual)
        assertEquals(expected, actual)
    }


}