package com.example.islami.api

import android.util.Log
import com.example.islami.api.model.WebServices
import com.example.islami.ui.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    companion object {
        private var retrofit: Retrofit? = null


        fun getInstance(): Retrofit {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.e("api", message)
                    }

                })
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }
            return retrofit!!
        }

        fun getWebService(): WebServices {
            return getInstance().create(WebServices::class.java)
        }
    }

//    @Provides
//    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
//        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
//            override fun log(message: String) {
//                Log.e("api", message)
//            }
//        })
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        return loggingInterceptor
//    }
//
//    @Provides
//    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .build()
//    }
//
//    @Provides
//    fun provideGsonConverterFactory(): GsonConverterFactory {
//        return GsonConverterFactory.create()
//    }
//
//    @Provides
//    fun provideRetrofit(
//        okHttpClient: OkHttpClient,
//        gsonConverterFactory: GsonConverterFactory
//    ): Retrofit {
//        return Retrofit.Builder()
//            .addConverterFactory(gsonConverterFactory)
//            .client(okHttpClient)
//            .baseUrl(Constants.URL)
//            .build()
//    }
//
//    fun getWebServices(retrofit: Retrofit): WebServices {
//        return retrofit.create(WebServices::class.java)
//    }
}