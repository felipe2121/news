package com.example.thefortnightly.view.listener

import androidx.lifecycle.MutableLiveData
import com.example.fortnightly.core.util.Event
import com.example.fortnightly.data.ui.Article

interface ArticleClickListener {

    val onArticleCliked: MutableLiveData<Event.Data<Article>>

    fun onClickArticle (article: Article) {
        onArticleCliked.value = Event.Data(article)
    }
}