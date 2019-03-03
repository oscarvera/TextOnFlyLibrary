package com.oscarvera.textonflylibrary.data.online

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import okhttp3.logging.HttpLoggingInterceptor



class ClientRetrofit {

    companion object {
        fun getHttpClient(): OkHttpClient.Builder {

            val httpClient = OkHttpClient.Builder()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder()
                        .addHeader("Postman-Token", "ab377f56-dd23-4fdb-9639-d324408acf75").build()
                    return chain.proceed(request)
                }
            })

            httpClient.addInterceptor(interceptor)

            return httpClient
        }

    }

}