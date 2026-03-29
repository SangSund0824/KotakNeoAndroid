package com.example.core.network.retrofit.adapter

import android.util.Log
import com.example.core.network.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

private const val TAG = "NetworkResponseCall"
private const val UNAUTHORIZED = 401
private const val BAD_REQUEST = 400
private const val IO_CANCELED = "canceled"

internal class NetworkResponseCall<R : Any>(
    private val delegate: Call<R>,
    private val scope: CoroutineScope,
    private val networkErrorHandler: NetworkErrorHandler,
    private val json: Json,
) : Call<NetworkResponse<R>> {

    override fun clone(): Call<NetworkResponse<R>> {
        return NetworkResponseCall(
            delegate.clone(),
            scope,
            networkErrorHandler,
            json,
        )
    }

    override fun execute(): Response<NetworkResponse<R>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    override fun enqueue(callback: Callback<NetworkResponse<R>>) {
        delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Error(NoBodyException))
                        )
                    }
                } else {
                    var errorBody: NetworkErrorBody? = null
                    try {
                        if (error != null && error.contentLength() > 0) {
                            errorBody = json.decodeFromStream<NetworkErrorBody>(error.byteStream())
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "JSON parsing error: ${e.message}", e)
                    }
                    when (response.code()) {
                        UNAUTHORIZED -> callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                NetworkResponse.Error(
                                    InvalidAuthTokenException(
                                        code = code,
                                        errorMessage = errorBody?.errorMsg() ?: response.message()
                                    )
                                ))
                        )

                        BAD_REQUEST -> if (errorBody?.errorMsg() == "invalid_grant") {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    NetworkResponse.Error(
                                        BadCredentialsException(
                                            code = code,
                                            errorMessage = errorBody.errorMsg() ?: response.message()
                                        )
                                    ))
                            )
                        } else {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    NetworkResponse.Error(
                                        TCVException(
                                            errorBody?.status ?: code,
                                            errorBody?.errorMsg() ?: response.message(),
                                            errorBody?.retriable ?: false,
                                            errorBody?.transactionId,
                                            null
                                        )
                                    )
                                )
                            )
                        }

                        else -> callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                NetworkResponse.Error(
                                    TCVException(
                                        errorBody?.status ?: code,
                                        errorBody?.errorMsg() ?: response.message(),
                                        errorBody?.retriable ?: false,
                                        errorBody?.transactionId,
                                        null
                                    )
                                )
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                Log.i(TAG, "onFailure: ${call.request().url}, $t")
                val networkResponse = when (t) {
                    is UnknownHostException -> NoInternetException
                    is TimeoutException -> TimedOutException
                    is SocketTimeoutException -> TimedOutException
                    is IOException -> {
                        if (t.message?.contains(IO_CANCELED, true) == true) {
                            CanceledException
                        } else {
                            NoInternetException
                        }
                    }
                    is HttpException -> when(val code = t.code()) {
                        BAD_REQUEST -> BadCredentialsException(
                            code,
                            t.message() ?: "Unknown error"
                        )
                        UNAUTHORIZED -> InvalidAuthTokenException(
                            code,
                            t.message() ?: "Unknown error"
                        )
                        else -> TCVException(errorCode = code, msg = t.message(), throwable = t)
                    }
                    else -> UnknownNetworkException(t)
                }
                handleError(networkResponse, callback)
            }
        })
    }

    private fun retryLast(callback: Callback<NetworkResponse<R>>) {
        cancel()
        this.clone().enqueue(callback)
    }

    private fun handleError(networkException: NetworkExceptions, callback: Callback<NetworkResponse<R>>) = scope.launch {
        val handled = networkErrorHandler.handleError(NetworkRetryError(
            networkException = networkException,
            retryAction = {
                scope.launch { networkErrorHandler.errorHandled() }
                retryLast(callback)
            }
        ))
        if (!handled)
            callback.onResponse(this@NetworkResponseCall,
                Response.success(NetworkResponse.Error(networkException))
            )
    }
}
