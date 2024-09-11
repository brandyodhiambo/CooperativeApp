package com.brandy.cooperativeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandy.cooperativeapp.data.local.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preference: Preference
) : ViewModel() {

    // Convert to a hot state flow
    val userName = preference.getUserData
        .map { it?.username ?: "" }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

    fun logOut() {
        viewModelScope.launch {
            preference.clear()
        }
    }
}