package com.example.islami.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hadeeth(val title: String, val content: String) : Parcelable
