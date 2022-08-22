package com.vectorinc.task.viewmodel

sealed class MainActivityEvents {
    data class OnSearchQueryChange(val query: String): MainActivityEvents()
}