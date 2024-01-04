package com.example.islami.api.model

import com.google.gson.annotations.SerializedName

data class RadiosItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("url")
    val url: String? = null
)