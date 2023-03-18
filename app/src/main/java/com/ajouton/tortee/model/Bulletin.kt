package com.ajouton.tortee.model

data class Bulletin (
    val writer: String = "name",
    val title: String = "Title",
    val writeDate: String = "xxxx:xx:xx",
    val content: String = "content",
    var tag: String = "Tag"
)