package com.ua.weatherwise.presentation.settings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ua.weatherwise.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    val getUnits = preferenceManager.getUnits.asLiveData(Dispatchers.IO)

    fun setUnits(units: String) {
        viewModelScope.launch {
            preferenceManager.setUnits(units)
        }
    }
}