package com.emotionb.ggs.content_eventcash

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.lang.IllegalArgumentException

class EventCashViewModel : ViewModel() {

    private val _content = mutableStateOf("")
    val content: State<String> = _content
    val eventUrl = "https://maplestory.nexon.com/News/Event"
    val cashUrl = "https://maplestory.nexon.com/News/CashShop"

    init {
        val eventDoc = Jsoup.connect(eventUrl).timeout(1000 * 10).get()
        val eventData: Elements = eventDoc.select("div div.content_wrap div.event_board ul li")
        for (data in eventData) {
            val element = data.select("div dl")
            val imageUrl = element.select("dt a img").attr("src")
            val name = element.select("dd.data p a").text()
            val period = element.select("dd.date p").text()
            _content.value = period
            Log.d("EVENTDATA", "imageUrl: $imageUrl\nname: $name\nperiod: $period")
        }
    }
}

class EventCashViewModelFactory() : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventCashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventCashViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}