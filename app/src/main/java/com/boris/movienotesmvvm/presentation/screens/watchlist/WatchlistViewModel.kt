package com.boris.movienotesmvvm.presentation.screens.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.usecases.AddToFavoriteUseCase
import com.boris.movienotesmvvm.domain.usecases.AddToWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromFavoriteUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.GetSavedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val deleteFromWatchlistUseCase: DeleteFromWatchlistUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase
) :
    ViewModel() {

    private val _watchlistStateFlow = MutableStateFlow<List<Movie>>(emptyList())
    val watchlistStateFlow
        get() = _watchlistStateFlow.asStateFlow()

    init {
        getWatchlistMovies()
    }

     private fun getWatchlistMovies() = viewModelScope.launch(Dispatchers.IO) {
        getSavedMoviesUseCase.execute().collect { savedMovies ->
            _watchlistStateFlow.value = savedMovies.filter { it.isWatchlist }
        }
    }
    suspend fun addWatchlistMovie(movie: Movie) {
        addToWatchlistUseCase.execute(movie = movie)
    }

    suspend fun deleteWatchlistMovie(movie: Movie) {
        deleteFromWatchlistUseCase.execute(movie = movie)
    }

    suspend fun addFavoriteMovie(movie: Movie) {
        addToFavoriteUseCase.execute(movie = movie)
    }

    suspend fun deleteFavoriteMovie(movie: Movie) {
        deleteFromFavoriteUseCase.execute(movie = movie)
    }
}