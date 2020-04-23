package ru.gc986.dataproviders.net.common

import com.grapesnberries.curllogger.CurlLoggerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.gc986.logs.Logs
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class BaseNetConstructor(showDebugInfo: Boolean) {

    val TIMEOUT_FOR_REQUESTS = 5L

    private val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())

    init {
        tmf.init(null as KeyStore?)
    }

    private val httpClient = if (showDebugInfo)
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor { message -> Logs.i("interceptor : $message") }
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(CurlLoggerInterceptor())
    else
        OkHttpClient()
            .newBuilder()

    fun <S> create(mainServerUrl: String, serviceClass: Class<S>): S {
        val retrofit = Retrofit.Builder()

        try {
            retrofit.baseUrl(mainServerUrl)
        } catch (error: IllegalArgumentException) {
            retrofit.baseUrl("$mainServerUrl/")
        }

        return retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                httpClient
                    .connectTimeout(TIMEOUT_FOR_REQUESTS, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_FOR_REQUESTS, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_FOR_REQUESTS, TimeUnit.SECONDS)
                    .build()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(serviceClass)
    }

}