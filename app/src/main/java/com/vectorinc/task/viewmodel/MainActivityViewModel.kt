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

    private val _query = MutableStateFlow(CharacterState.Query(""))
    val query: StateFlow<CharacterState> = _query




    init {

        loadData("")
    }


    fun loadData(query : String) {
        viewModelScope.launch {
            repository.getCharacter(query).collect(){ state->
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

    fun onEvent(event: MainActivityEvents){
        when(event){
            is MainActivityEvents.OnSearchQueryChange -> {
                Log.d("Query", event.query.toString())
                loadData(event.query)
            }
        }
    }

}
sealed class CharacterState {
    data class Success(var character: List<Character>): CharacterState()
    data class Error(val exception: Throwable): CharacterState()
    data class Query(var query: String): CharacterState()
    data class Loading(var loadingState: Boolean?= false): CharacterState()
}