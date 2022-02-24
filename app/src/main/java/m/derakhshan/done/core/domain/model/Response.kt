package m.derakhshan.done.core.domain.model

sealed class Response<T>(
    open val data: T? = null,
    open val responseCode: Int? = null,
    open val message: String? = null
) {
    data class Success<T>(override val data: T) : Response<T>(data)

    data class Loading<T>(override val data: T? = null) : Response<T>(data)

    data class Error<T>(
        override val message: String,
        override val responseCode: Int,
        override val data: T? = null
    ) :
        Response<T>(data = data, message = message, responseCode = responseCode)
}