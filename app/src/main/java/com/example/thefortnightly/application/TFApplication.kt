package com.example.thefortnightly.application

import android.app.Application
import com.example.fortnightly.data._config.TFDatabase
import com.example.fortnightly.domain.repository.NewsLocalRepository
import com.example.fortnightly.domain.repository.NewsRemoteRepository
import com.example.fortnightly.domain.repository.NewsRepository
import com.example.fortnightly.domain.usecase.GetArticleOfCategoryUseCase
import com.example.fortnightly.domain.usecase.GetArticlesOfFirstPageUseCase
import com.example.fortnightly.domain.usecase.SearchArticleUseCase
import com.example.thefortnightly.viewmodel.ArticleViewModel
import com.example.thefortnightly.viewmodel.CategoryNewsViewModel
import com.example.thefortnightly.viewmodel.FirstPageViewModel
import com.example.thefortnightly.viewmodel.SearchArticleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TFApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin () {

        startKoin {

            androidContext(this@TFApplication)

            val daoModule = module {

                single { TFDatabase.getInstance(get()) }

                single { get<TFDatabase>().articleDAO }

            }

            val repositoryModule = module {
                factory { NewsRemoteRepository() }
                factory { NewsLocalRepository(articleDAO = get()) }
                factory { NewsRepository(newsLocalRepository = get(), newsRemoteRepository = get()) }
            }

            val usecaseModule = module {
                factory { GetArticleOfCategoryUseCase(newsRepository = get()) }
                factory { GetArticlesOfFirstPageUseCase(newsReposity = get()) }
                factory { SearchArticleUseCase(newsRepository = get()) }
            }

            val viewModule = module {
                factory { CategoryNewsViewModel(getArticleOfCategory = get()) }
                factory { ArticleViewModel() }
                factory { FirstPageViewModel(getArticlesFirstPage = get()) }
                factory { SearchArticleViewModel(searchArticle = get()) }
            }

            modules(listOf(daoModule, repositoryModule, usecaseModule, viewModule))
        }
    }
}
















