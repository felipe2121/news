package com.example.thefortnightly.view.listener

import androidx.lifecycle.MutableLiveData
import com.example.fortnightly.core.util.Event
import com.example.fortnightly.data.ui.Article

interface ArticleClickListener {

    val onArticleClicked: MutableLiveData<Event.Data<Article>>

    fun onClickArticle (article: Article) {
        onArticleClicked.value = Event.Data(article)
    }
}