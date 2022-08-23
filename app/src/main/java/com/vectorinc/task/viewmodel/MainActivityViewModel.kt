package com.vectorinc.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vectorinc.task.domain.model.Character
import com.vectorinc.task.domain.repository.CharacterRepository
import com.vectorinc.task.utils.DispatchersProvider
import com.vectorinc.task.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val dispatchers: DispatchersProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterState.Success(emptyList()))
    val uiState: StateFlow<CharacterState> = _uiState

    private val _size = MutableStateFlow(0)
    val size: StateFlow<Int> = _size




    init {

        loadData("")
    }


    fun loadData(query: String) {
        viewModelScope.launch(dispatchers.main) {
            repository.getCharacter(query).collect() { result ->
                when (result) {
                    is Resource.Error -> {
                    }
                    is Resource.Success -> {
                        result.data?.let { characters ->
                            _size.value = characters.size
                            _uiState.value = CharacterState.Success(characters)
                        }

                    }
                    is Resource.Loading -> {


                    }
                }
            }
        }
    }

    fun onEvent(event: MainActivityEvents) {
        when (event) {
            is MainActivityEvents.OnSearchQueryChange -> {
                loadData(event.query)
            }
        }
    }

    fun listSize() : Int{
        return _size.value
    }

}

sealed class CharacterState {
    data class Success(var character: List<Character>) : CharacterState()

}
