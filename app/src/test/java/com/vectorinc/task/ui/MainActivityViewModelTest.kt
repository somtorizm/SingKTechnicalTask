package com.vectorinc.task.ui

import app.cash.turbine.test
import com.vectorinc.task.domain.model.Character
import com.vectorinc.task.repository.FakeRepository
import com.vectorinc.task.util.TestDispatchers
import com.vectorinc.task.utils.Constants
import com.vectorinc.task.viewmodel.CharacterState
import com.vectorinc.task.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainActivityViewModelTest {

    lateinit var repository: FakeRepository
    lateinit var viewModel: MainActivityViewModel
    lateinit var testDispatchers: TestDispatchers
    lateinit var fakeItems: ArrayList<Character>


    @Before
    fun setup() {
        fakeItems = ArrayList()
        testDispatchers = TestDispatchers()

        var characters = Character(name = "Victor", img = "https://www.google.com")
        fakeItems.add(characters)
        characters = Character(name = "Somto", img = "https://www.google.comx")
        fakeItems.add(characters)
        characters = Character(name = "Hector1", img = "https://www.stackOverflow")
        fakeItems.add(characters)
        characters = Character(name = "Somito", img = "https://www.google.comx")
        fakeItems.add(characters)
        repository = FakeRepository(fakeItems)
        viewModel = MainActivityViewModel(repository, testDispatchers)

    }

    @Test
    fun `base url test`() {
        val expected = "https://breakingbadapi.com/"
        val actual = Constants.BASE_URL

        assertEquals(expected, actual)
    }

    @Test
    fun `test repo size`() = runBlocking {
        val job = launch {
            val expected = 4
            val actual = viewModel.listSize()
            assertEquals(expected, actual)

        }
        job.join()
        job.cancel()
    }

    @Test
    fun `test emit success values from the view model state`() = runBlocking {
        val job = launch {

        viewModel.loadData("")
            viewModel.uiState.test {
                val emission = awaitItem()
                assertEquals(emission, CharacterState.Success(fakeItems))
            }

        }
        job.join()
        job.cancel()
    }


}