package com.spx.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {
    companion object {
        var retrofit: Retrofit? = null
        var client: OkHttpClient? = null
        fun get(): DouBanApi? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                        .client(getCusHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }
            return retrofit!!.create(DouBanApi::class.java)
        }

        fun getCusHttpClient(): OkHttpClient {
            if (client != null) {
                return client!!
            }
            client = OkHttpClient.Builder()
                    .sslSocketFactory(HttpsTrustManager.createSSLSocketFactory())
                    .hostnameVerifier(HttpsTrustManager.TrustAllHostnameVerifier())
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build()
            return client!!
        }
    }
}