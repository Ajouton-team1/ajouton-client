package com.ajouton.tortee

import androidx.lifecycle.ViewModel
import com.ajouton.tortee.data.BoardDataProvider
import com.ajouton.tortee.data.Bulletin
import com.ajouton.tortee.data.ViewType
import com.ajouton.tortee.ui.state.TorteeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TorTeeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TorteeUIState())
    private val _isBulletinContentShowing = MutableStateFlow(false)
    private val _isBulletinWriterShowing = MutableStateFlow(false)
    private val _selectedBulletin = MutableStateFlow(Bulletin("", "", ""))

    val uiState: StateFlow<TorteeUIState> = _uiState
    val isBulletinContentShowing: StateFlow<Boolean> = _isBulletinContentShowing
    val isBulletinWriterShowing: StateFlow<Boolean> = _isBulletinWriterShowing
    val selectedBulletin: MutableStateFlow<Bulletin> = _selectedBulletin

    fun updateCurrentView(viewType: ViewType) {
        _uiState.update {
            it.copy(
                currentViewType = viewType
            )
        }
    }

    fun setBulletinWriterVisibility() {

    }

    fun setBulletinContentVisibility(visibility: Boolean, bulletin: Bulletin? = null) {
        _isBulletinContentShowing.update {
            visibility
        }
        if(visibility) {
            _selectedBulletin.update {
                bulletin ?: Bulletin("empty", "empty", "empty")
            }
        }
    }

    fun getBulletinList(): List<Bulletin> {
        return BoardDataProvider.bulletinList
    }
}