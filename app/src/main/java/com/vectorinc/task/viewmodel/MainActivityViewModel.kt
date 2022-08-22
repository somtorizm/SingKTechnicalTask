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

    private val _uiState = MutableStateFlow(CharacterState.Success(emptyList()))
    val uiState: StateFlow<CharacterState> = _uiState

    init {

        loadData()
    }


    fun loadData() {
        viewModelScope.launch {
            repository.getCharacter().collect(){ state->
                when(state){
                    is Resource.Error -> {
                        //TODO Handle Error
                    }
                    is Resource.Success -> {
                           _uiState.value = CharacterState.Success(state.data ?: emptyList())
                    }
                    is Resource.isLoading -> {
                        //TODO Handle Loading
                    }
                }
            }
        }
    }
}
sealed class CharacterState {
    data class Success(var character: List<Character>): CharacterState()
    data class Error(val exception: Throwable): CharacterState()
    data class Loading(var loadingState: Boolean?= false): CharacterState()
}