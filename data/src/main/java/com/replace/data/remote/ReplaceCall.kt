import com.replace.data.common.FetchState
import com.replace.data.remote.CustomResult
import com.replace.data.remote.createCustomThrowableFromResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketException
import java.net.UnknownHostException

class ReplaceCall<T> constructor(
    private val callDelegate: Call<T>,
) : Call<CustomResult<T>> {
    override fun enqueue(callback: Callback<CustomResult<T>>) {
        callDelegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val locationHeader = response.headers()["Location"]
                    locationHeader?.substringAfterLast("/")?.let {
                        callback.onResponse(
                            this@ReplaceCall,
                            Response.success(CustomResult.Success(it as T)),
                        )
                    } ?: run {
                        response.body()?.let {
                            callback.onResponse(
                                this@ReplaceCall,
                                Response.success(CustomResult.Success(it)),
                            )
                        } ?: callback.onResponse(
                            this@ReplaceCall,
                            Response.success(CustomResult.NullCustomResult()),
                        )
                    }
                } else {
                    when (response.code()) {
                        in 400..599 -> {
                            runCatching {
                                createCustomThrowableFromResponse(response)
                            }.onSuccess {
                                callback.onResponse(
                                    this@ReplaceCall,
                                    Response.success(CustomResult.ApiError(it)),
                                )
                            }.onFailure {
                                callback.onResponse(
                                    this@ReplaceCall,
                                    Response.success(
                                        response.code(),
                                        CustomResult.UnKnownApiError(response.code()),
                                    ),
                                )
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val fetchState = when (t) {
                    is SocketException -> FetchState.BadInternet
                    is HttpException -> FetchState.ParseError
                    is UnknownHostException -> FetchState.WrongConnection
                    else -> FetchState.Fail
                }
                callback.onResponse(
                    this@ReplaceCall,
                    Response.success(CustomResult.NetworkError(fetchState)),
                )
            }
        })
    }
    override fun clone(): Call<CustomResult<T>> = ReplaceCall(callDelegate.clone())
    override fun execute(): Response<CustomResult<T>> =
        throw UnsupportedOperationException("ResponseCall does not support execute.")
    override fun isExecuted(): Boolean = callDelegate.isExecuted
    override fun cancel() = callDelegate.cancel()
    override fun isCanceled(): Boolean = callDelegate.isCanceled
    override fun request(): Request = callDelegate.request()
    override fun timeout(): Timeout = callDelegate.timeout()
}
