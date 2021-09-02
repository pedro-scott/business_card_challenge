package com.github.pedroscott.yourbusinesscard.model

import android.net.Uri

data class CardViewParams(
    val name: String,
    val companyName: String,
    val phone: String,
    val email: String,
    val colorBackground: Int,
    val colorText: Int,
    val logo: String
)