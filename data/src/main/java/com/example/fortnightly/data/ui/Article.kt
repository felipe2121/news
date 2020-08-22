package com.example.fortnightly.data.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article (
    var author: String = "",
    var title: String = "",
    var description: String = "",
    var url: String? = null,
    var urlToImage: String? = null,
    var publisedAt: String = "",
    var content: String = "",
    var category: String = ""
): Parcelable {
    companion object {
        const val ARTICLE = "article"
    }
}