package com.example.islami.api.model

import retrofit2.Call
import retrofit2.http.GET

interface WebServices {

    @GET("radios")
    fun getRadio(): Call<RadioResponse>

}