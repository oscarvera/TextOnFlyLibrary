package com.oscarvera.textonflylibrary.data.online

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import okhttp3.logging.HttpLoggingInterceptor



class ClientRetrofit {

    companion object {
        fun getHttpClient(headers : HashMap<String,String>?): OkHttpClient.Builder {

            val httpClient = OkHttpClient.Builder()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder()
                    headers?.let {
                        headers.onEach {
                            request.addHeader(it.key, it.value)
                        }
                    }
                    return chain.proceed(request.build())
                }
            })

            httpClient.addInterceptor(interceptor)

            return httpClient
        }

    }

}