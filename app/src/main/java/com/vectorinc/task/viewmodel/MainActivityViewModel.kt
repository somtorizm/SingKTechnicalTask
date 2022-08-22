package com.vectorinc.task.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vectorinc.task.domain.model.Character
import com.vectorinc.task.domain.repository.CharacterRepository
import com.vectorinc.task.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterStateSucess.Success(emptyList()))
    val uiState: StateFlow<CharacterStateSucess> = _uiState



    init {

        loadData("")
    }


    fun loadData(query: String) {
        viewModelScope.launch {
            repository.getCharacter(query).collect() { result ->
                when (result) {
                    is Resource.Error -> {

                    }
                    is Resource.Success -> {
                        result.data?.let { characters ->
                            _uiState.value = CharacterStateSucess.Success(characters)
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

}

sealed class CharacterStateSucess {
    data class Success(var character: List<Character>) : CharacterStateSucess()
}
