package com.emotionb.ggs.model

import kotlinx.serialization.Serializable

@Serializable
data class EventData(
    val title: String,
    val period: String,
    val imgSrc: String
)
