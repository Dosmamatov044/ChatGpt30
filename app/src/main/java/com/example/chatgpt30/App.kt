package com.example.chatgpt30

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit


class App : Application() {
    private lateinit var retrofit: Retrofit
    override fun onCreate() {
        super.onCreate()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).addInterceptor{chain->


                val request:Request=chain.request().newBuilder().addHeader("Content-Type","application/json").addHeader("Authorization","Bearer ${Constants.API_KEY}").build()


                chain.proceed(request)

            }
            .addInterceptor(logger)

            .build()


        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL).client(okHttpClient)//Базовая часть адреса
            .addConverterFactory(GsonConverterFactory.create(gson)) //Конвертер, необходимый для преобразования JSON'а в объекты
            .build()
        api =
            retrofit.create(ChatGptInterfaceApi::class.java) //Создаем объект, при помощи которого будем выполнять запросы
    }
    companion object {
        var api: ChatGptInterfaceApi? = null
            private set
    }



}