package com.ajouton.tortee.ui.state

import com.ajouton.tortee.data.ViewType

data class TorteeUIState (
    val currentViewType: ViewType = ViewType.MentorList
)