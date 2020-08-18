package br.daniel.fortnightly.domain._config.result

import java.io.Serializable

/**
 * A discriminated union that encapsulates successful outcome with a value of type [T]
 * or a failure with an arbitrary [Throwable] exception.
 */
@Suppress("NON__PRIMARY_CONSTRUCTOR_OF__CLASS")
open class TFResult<out T> (val value: Any?) : Serializable {

    /**
     * Returns `true` if this instance represents successful outcome.
     * In this case [isFailure] returns `false`.
     */
    val isSuccess: Boolean get() = value !is Failure

    /**
     * Returns `true` if this instance represents failed outcome.
     * In this case [isSuccess] returns `false`.
     */
    val isFailure: Boolean get() = value is Failure

    // value & exception retrieval

    /**
     * Returns the encapsulated value if this instance represents [success][TFResult.isSuccess] or `null`
     * if it is [failure][TFResult.isFailure].
     *
     */
    fun getOrNull(): T? =
        when {
            isFailure -> null
            else -> value as T
        }

    /**
     * Returns the encapsulated exception if this instance represents [failure][isFailure] or `null`
     * if it is [success][isSuccess].
     *
     */
    fun exceptionOrNull(): Throwable? =
        when (value) {
            is Failure -> value.exception
            else -> null
        }

    /**
     * Returns a string `Success(v)` if this instance represents [success][TFResult.isSuccess]
     * where `v` is a string representation of the value or a string `Failure(x)` if
     * it is [failure][isFailure] where `x` is a string representation of the exception.
     */
    override fun toString(): String =
        when (value) {
            is Failure -> value.toString() // "Failure($exception)"
            else -> "Success($value)"
        }

    // companion with constructors

    /**
     * Companion object for [TFResult] class that contains its constructor functions
     * [success] and [failure].
     */
    companion object {
        /**
         * Returns an instance that encapsulates the given [value] as successful value.
         */

        fun <T> success(value: T): TFResult<T> =
            TFResult(value)

        /**
         * Returns an instance that encapsulates the given [exception] as failure.
         */

        fun <T> failure(exception: Throwable): TFResult<T> =
            TFResult(createFailure(exception))
    }

    class Failure(@JvmField val exception: Throwable) : Serializable {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }
}

/**
 * Creates an instance of internal marker [TFResult.Failure] class to
 * make sure that this class is not exposed in ABI.
 */
internal fun createFailure(exception: Throwable): Any =
    TFResult.Failure(exception)

/**
 * Throws exception if the NewResult is failure. This internal function minimizes
 * d bytecode for [getOrThrow] and makes sure that in the future we can
 * add some exception-augmenting logic here (if needed).
 */
fun TFResult<*>.throwOnFailure() {
    if (value is TFResult.Failure) throw value.exception
}

// -- extensions ---

/**
 * Returns the encapsulated value if this instance represents [success][TFResult.isSuccess] or throws the encapsulated exception
 * if it is [failure][TFResult.isFailure].
 *
 * This function is shorthand for `getOrElse { throw it }` (see [getOrElse]).
 */
@Suppress("UNCHECKED_CAST")
fun <T> TFResult<T>.getOrThrow(): T {
    throwOnFailure()
    return value as T
}

// transformation

/**
 * Returns the encapsulated NewResult of the given [transform] function applied to encapsulated value
 * if this instance represents [success][TFResult.isSuccess] or the
 * original encapsulated exception if it is [failure][TFResult.isFailure].
 *
 * Note, that an exception thrown by [transform] function is rethrown by this function.
 * See [mapCatching] for an alternative that encapsulates exceptions.
 */
@Suppress("UNCHECKED_CAST")
fun <R, T> TFResult<T>.map(transform: (value: T) -> R): TFResult<R> {
    return when {
        isSuccess -> TFResult.success(transform(value as T))
        else -> TFResult(value)
    }
}

fun <R> TFResult<*>.mapFailure(): TFResult<R> {
    return this as TFResult<R>
}

/**
 * Performs the given [action] on encapsulated exception if this instance represents [failure][TFResult.isFailure].
 * Returns the original `NewResult` unchanged.
 */
suspend fun <T> TFResult<T>.onFailure(action: suspend (exception: Throwable) -> Unit): TFResult<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

/**
 * Performs the given [action] on encapsulated value if this instance represents [success][TFResult.isSuccess].
 * Returns the original `NewResult` unchanged.
 */
@Suppress("UNCHECKED_CAST")
suspend fun <T> TFResult<T>.onSuccess(action: suspend (value: T) -> Unit): TFResult<T> {
    if (isSuccess) action(value as T)
    return this
}