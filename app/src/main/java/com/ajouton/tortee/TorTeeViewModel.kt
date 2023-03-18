package com.ajouton.tortee

import androidx.lifecycle.ViewModel
import com.ajouton.tortee.data.ViewType
import com.ajouton.tortee.ui.state.TorteeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TorTeeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TorteeUIState())
    val uiState: StateFlow<TorteeUIState> = _uiState

    fun updateCurrentView(viewType: ViewType) {
        _uiState.update {
            it.copy(
                currentViewType = viewType
            )
        }
    }
}