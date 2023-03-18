package com.ajouton.tortee.model

import com.ajouton.tortee.R

data class User(
    var imageResId: Int = R.drawable.user_icon,
    val rankResId: Int = R.drawable.baseline_search_24,
    var email: String = "email",
    var info: String = "default info",
    val name: String = "name",
    var warningCount: Int = 0,
    var technics: List<String> = listOf()
)
