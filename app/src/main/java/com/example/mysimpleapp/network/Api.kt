package com.example.mysimpleapp.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val API_KEY = "1cadce9a7e6a42babfcdaa255c6ff598"
    private const val BASE_URL = "https://newsapi.org/v2/"
    private val logging = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor {
                chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-Api-key", API_KEY)
                return@Interceptor chain.proceed(builder.build())
            }
        )
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(httpClient).addConverterFactory(GsonConverterFactory.create()).build()
    val retrofitService: NewsService by lazy { retrofit.create(NewsService::class.java) }
}