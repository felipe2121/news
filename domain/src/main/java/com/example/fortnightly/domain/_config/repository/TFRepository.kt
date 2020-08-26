package com.example.fortnightly.domain._config.repository

import com.example.fortnightly.core.util.TFResult
import com.example.fortnightly.data._config.dao.TransactionDAO
import com.example.fortnightly.domain.BuildConfig
import com.example.fortnightly.domain._config.exception.toNetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class TFRepository {

    companion object {
        const val API_KEY = "X-Api-Key"
    }

    abstract class Remote {

        fun getHttpCaller(): Retrofit {

            val clientBuilder = OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
            }

            return Retrofit.Builder()
                .baseUrl("http://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build()

        }

        inline fun<reified T> retrofit() = lazy {
            getHttpCaller().create(T::class.java)
        }

        suspend fun <API, T> executeRequest(api: API, request: suspend API.() -> T): TFResult<T> {

            var repositoryResult: TFResult<T>? = null

            CoroutineScope(Dispatchers.IO).launch {
                repositoryResult = try {
                    TFResult.success(api.request())
                } catch (error: Exception) {
                    TFResult.failure(error.toNetworkException())
                }
            }.join()

            return repositoryResult!!
        }
    }

    abstract class Local: KoinComponent {

        private val transactionDAO by inject<TransactionDAO>()

        suspend fun executeTransaction(transaction: suspend () -> Unit) {
            transactionDAO.executeTransaction { transaction() }
        }
    }

}