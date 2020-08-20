package com.example.fortnightly.data.ui

import androidx.room.PrimaryKey

data class Article (
    var author: String = "",
    var title: String = "",
    var description: String = "",
    var url: String? = null,
    var urlToImage: String? = null,
    var publisedAt: String = "",
    var content: String = "",
    var category: String = ""
)