package com.emotionb.ggs.model

import kotlinx.serialization.Serializable

@Serializable
data class CashShopData(
    val title: String,
    val update: String,
    val period: String,
    val imgSrc: String,
    val link: String
)
