package com.example.thefortnightly.application

import android.app.Application
import androidx.core.widget.NestedScrollView
import com.example.fortnightly.data._config.TFDatabase
import com.example.fortnightly.data.dao.ArticleDAO
import com.example.fortnightly.domain.repository.NewsLocalRepository
import com.example.fortnightly.domain.repository.NewsRemoteRepository
import com.example.fortnightly.domain.repository.NewsRepository
import com.example.fortnightly.domain.usecase.GetArticleOfCategoryUseCase
import com.example.fortnightly.domain.usecase.GetArticlesOfFirstPageUseCase
import com.example.fortnightly.domain.usecase.SearchNewsUseCase
import com.example.thefortnightly.viewmodel.CategoryNewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.*

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
                factory { SearchNewsUseCase(newsRepository = get()) }
            }

            val viewModule = module {
                factory { CategoryNewsViewModel(getArticleOfCategory = get()) }
            }

            modules(listOf(daoModule, repositoryModule, usecaseModule, viewModule))
        }
    }
}
















