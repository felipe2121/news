package com.example.fortnightly.domain._config.exception

import com.example.fortnightly.core.exception.TFException
import com.example.fortnightly.core.util.StringWrapper
import com.example.fortnightly.domain.R
import retrofit2.HttpException
import java.net.*

fun Throwable.toNetworkException() = TFNetworkException(message, this)

fun Throwable.toNetworkExceptionCode(): TFNetworkException.NetworkExceptionCode {

    return when (this) {

        is HttpException -> {
            when (code()) {
                HttpURLConnection.HTTP_BAD_REQUEST -> TFNetworkException.NetworkExceptionCode.BAD_REQUEST
                HttpURLConnection.HTTP_UNAUTHORIZED -> TFNetworkException.NetworkExceptionCode.UNAUTHORIZED
                HttpURLConnection.HTTP_FORBIDDEN -> TFNetworkException.NetworkExceptionCode.FORBIDDEN
                HttpURLConnection.HTTP_NOT_FOUND -> TFNetworkException.NetworkExceptionCode.NOT_FOUND
                in 400..499 -> TFNetworkException.NetworkExceptionCode.CLIENT
                in 500..599 -> TFNetworkException.NetworkExceptionCode.SERVER
                else -> TFNetworkException.NetworkExceptionCode.UNEXPECTED
            }
        }
        is SocketTimeoutException -> TFNetworkException.NetworkExceptionCode.TIMEOUT
        is UnknownHostException -> TFNetworkException.NetworkExceptionCode.UNKNOWN_HOST
        is ConnectException -> TFNetworkException.NetworkExceptionCode.CONNECTION
        is SocketException -> TFNetworkException.NetworkExceptionCode.CANCELED
        else -> TFNetworkException.NetworkExceptionCode.UNEXPECTED
    }
}

fun createNetworkException(errorCode: TFNetworkException.NetworkExceptionCode): TFNetworkException {
    return TFNetworkException().apply { this.errorCode = errorCode }
}

class TFNetworkException : TFException {

    /**
     *  Códigos de erro:
     *
     *  #CLIENT - Um erro na faixa de 400 .. 499
     *  #SERVER - Um erro na faixa de 500 .. 599
     *
     *  #400/BAD_REQUEST - O request é invalido ou não pode ser servido. Geralmente o JSON pode não ser
     *  válido.
     *  #401/UNAUTHORIZED - A requisição requer autenticação do usuário.
     *  #403/FORBIDDEN - O servidor entende a requisição mas o acesso não está liberado.
     *  #404/NOT_FOUND - Não foi encontrado o que se procura.
     *
     *  #TIMEOUT - Esgotou o tempo limite da requisição.
     *  #UNKNOWN_HOST - O endereço IP do host não foi encontrado, acontece também quando não está
     *  conectado a internet.
     *  #CONNECTION - Sem conexão com a internet.
     *  #CANCELED - A requisição foi cancelada, normalmente o usuário recebeu UNAUTHORIZED em outra
     *  requisição paralela a essa.
     *
     *  #UNEXPECTED - Um erro inesperado.
     *
     * */

    enum class NetworkExceptionCode(val message: StringWrapper) {
        CLIENT(StringWrapper(R.string.network_error_try_again)),
        SERVER(StringWrapper(R.string.network_error_server)),
        BAD_REQUEST(StringWrapper(R.string.network_error_bad_request)),
        UNAUTHORIZED(StringWrapper(R.string.network_error_unauthorized)),
        FORBIDDEN(StringWrapper(R.string.network_error_try_again)),
        NOT_FOUND(StringWrapper(R.string.network_error_not_found)),
        TIMEOUT(StringWrapper(R.string.network_error_try_again)),
        UNKNOWN_HOST(StringWrapper(R.string.network_error_connection)),
        CONNECTION(StringWrapper(R.string.network_error_connection)),
        CANCELED(StringWrapper(R.string.network_error_canceled)),
        UNEXPECTED(StringWrapper(R.string.network_error_try_again))
    }

    override var errorMessage: StringWrapper

    var errorCode: NetworkExceptionCode

    constructor() : super()
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    init {
        errorCode = cause?.toNetworkExceptionCode() ?: NetworkExceptionCode.UNEXPECTED
        errorMessage = errorCode.message
    }
}