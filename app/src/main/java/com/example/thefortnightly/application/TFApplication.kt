package com.example.thefortnightly.application

import android.app.Application
import com.example.fortnightly.data._config.TFDatabase
import com.example.fortnightly.domain.repository.ArticleLocalRepository
import com.example.fortnightly.domain.repository.ArticleRemoteRepository
import com.example.fortnightly.domain.repository.ArticleRepository
import com.example.fortnightly.domain.usecase.GetArticleOfCategoryUseCase
import com.example.fortnightly.domain.usecase.GetArticlesOfFirstPageUseCase
import com.example.fortnightly.domain.usecase.SearchArticleUseCase
import com.example.thefortnightly.viewmodel.ArticleViewModel
import com.example.thefortnightly.viewmodel.ArticlesByCategoryViewModel
import com.example.thefortnightly.viewmodel.FirstPageViewModel
import com.example.thefortnightly.viewmodel.SearchArticleViewModel
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TFApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        setUpKoin()
    }

    private fun setUpKoin () {

        startKoin {

            androidContext(this@TFApplication)

            val daoModule = module {

                single { TFDatabase.getInstance(get()) }
                single { get<TFDatabase>().articleDAO }
                single { get<TFDatabase>().transactionDAO }
            }

            val repositoryModule = module {
                factory { ArticleRemoteRepository() }
                factory { ArticleLocalRepository(articleDAO = get()) }
                factory { ArticleRepository(articleLocalRepository = get(), articleRemoteRepository = get()) }
            }

            val usecaseModule = module {
                factory { GetArticleOfCategoryUseCase(newsRepository = get()) }
                factory { GetArticlesOfFirstPageUseCase(newsReposity = get()) }
                factory { SearchArticleUseCase(newsRepository = get()) }
            }

            val viewModule = module {
                factory { ArticlesByCategoryViewModel(getArticleOfCategory = get()) }
                factory { ArticleViewModel() }
                factory { FirstPageViewModel(getArticlesFirstPage = get()) }
                factory { SearchArticleViewModel(searchArticle = get()) }
            }

            modules(listOf(daoModule, repositoryModule, usecaseModule, viewModule))
        }
    }
}
















