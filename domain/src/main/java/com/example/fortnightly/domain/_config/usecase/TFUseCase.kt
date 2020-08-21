package com.example.fortnightly.domain._config.usecase

import com.example.fortnightly.core.exception.TFException
import com.example.fortnightly.core.util.TFResult
import com.example.fortnightly.core.util.onFailure
import com.example.fortnightly.core.util.onSuccess
import kotlinx.coroutines.*

interface ITFUseCase<PARAMS, RESULT, LIVERESULT> {
    suspend fun liveResult(params: PARAMS? = null): LIVERESULT
    suspend fun execute(params: PARAMS? = null): TFResult<RESULT>
}

abstract class TFUseCase<PARAMS, RESULT, out EXECUTOR : TFUseCase.UseCaseExecutor<PARAMS, RESULT>, LIVERESULT> private constructor() : ITFUseCase<PARAMS, RESULT, LIVERESULT> {

    abstract class UseCaseExecutor<PARAMS, RESULT>(var params: PARAMS? = null): CoroutineScope by MainScope() {
        abstract suspend fun execute()
    }

    abstract class Completable<PARAMS, RESULT, LIVERESULT> : TFUseCase<PARAMS, RESULT, Completable<PARAMS, RESULT, LIVERESULT>.CompletableUseCaseExecutor, LIVERESULT>() {

        inner class CompletableUseCaseExecutor : UseCaseExecutor<PARAMS, RESULT>() {

            private var _onStarted = { }
            private var _onSuccess: (RESULT) -> Unit = { }
            private var _onFailure: (TFException) -> Unit = { }
            private var _onFinish = { }

            fun onStarted(callback: () -> Unit) = apply { _onStarted = callback }

            fun onSuccess(callback: (RESULT) -> Unit) = apply { _onSuccess = callback }

            fun onFailure(callback: (TFException) -> Unit) = apply { _onFailure = callback }

            fun onFinish(callback: () -> Unit) = apply { _onFinish = callback }

            override suspend fun execute() {

                launch {
                    _onStarted()
                    try{
                        withContext(Dispatchers.Default) { execute(params) }
                            .onSuccess {
                                _onSuccess(it)
                            }
                            .onFailure {
                                if (it is TFException) _onFailure(it) else _onFailure(TFException(cause = it))
                            }
                    } catch (e: Exception) {
                        if (e is TFException) _onFailure(e) else _onFailure(TFException(cause = e))
                    }
                    _onFinish()
                }
            }
        }

        sealed class State {
            object Idle : State()
            object Executing : State()
            class Success(val hasContent: Boolean) : State()
            sealed class Error(val exception: TFException) : State() {
                class Connection(exception: TFException) : Error(exception)
                class Generic(exception: TFException) : Error(exception)
            }
        }

        override val useCaseExecutor = CompletableUseCaseExecutor()
    }

    abstract val useCaseExecutor: EXECUTOR

    override suspend fun liveResult(params: PARAMS?): LIVERESULT {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun execute(params: PARAMS?): TFResult<RESULT> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    operator fun invoke(params: PARAMS? = null): EXECUTOR {
        return useCaseExecutor.apply { this.params = params }
    }
}