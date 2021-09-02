package com.github.pedroscott.yourbusinesscard.extension

import com.github.pedroscott.yourbusinesscard.db.CardEntity
import com.github.pedroscott.yourbusinesscard.model.Card

fun Card.toCardEntity() =
    CardEntity(
        id = id,
        name = name,
        companyName = companyName,
        phone = phone,
        email = email,
        colorBackground = colorBackground,
        colorText = colorText,
        logo = logo
    )