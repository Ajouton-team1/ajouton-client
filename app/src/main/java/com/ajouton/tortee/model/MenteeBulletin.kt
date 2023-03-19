package com.ajouton.tortee.model

data class MenteeBulletin(
    val writer: String = "name",
    var title: String = "Test Title",
    val writeDate: String = "xxxx:xx:xx",
    var content: String = "Test Content maybe test content should be long long long long long long long long long " +
            "long long long long long long long long long long long long long long long long long long long long " +
            "long long long long long long long long long long long long long long long long long long long long " +
            "long long long long long long long long long long long long long long long long long enough",
    var tag: String = ""
)