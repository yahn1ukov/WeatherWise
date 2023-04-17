package com.ua.weatherwise.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ua.weatherwise.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    fun setCoordinates(coordinates: DoubleArray) {
        viewModelScope.launch {
            preferenceManager.setCoordinates(coordinates)
        }
    }
}