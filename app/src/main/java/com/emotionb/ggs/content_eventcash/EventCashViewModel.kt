package com.emotionb.ggs.content_eventcash

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emotionb.ggs.model.EventData
import com.emotionb.ggs.service.HttpRequestService
import com.emotionb.ggs.service.HttpRoutes
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class EventCashViewModel : ViewModel() {

    private val service = HttpRequestService.create()
    private val _content = mutableStateOf("")
    val content: State<String> = _content
    val eventUrl = "https://maplestory.nexon.com/News/Event"
    val cashUrl = "https://maplestory.nexon.com/News/CashShop"

    private lateinit var _eventResponse: HttpResponse
    private var _document: Document = Document("")

    private var _eventContents: List<EventData> = emptyList()
    var eventContents = MutableLiveData(_eventContents)

    init {
        getEventData()
    }

    private fun getEventData() {
        viewModelScope.launch {
            _eventResponse = service.getResponse(HttpRoutes.EVENT_URL)
            refactorEventData()
        }
    }

    private suspend fun refactorEventData() {
        _document = Jsoup.parse(_eventResponse.readText())
        val elements = _document.select("div.event_board ul li")
        for(element: Element in elements) {
//            Log.d("ELEMENT INFO", element.html())
            val imgSrc = element.select("div.event_list_wrap dl dt img").attr("src")
            val title = element.select("dd.data p a").text()
            val period = element.select("dd.date p").text()
            val singleData = EventData(
                imgSrc = imgSrc,
                title = title,
                period = period
            )
            _eventContents = _eventContents.plus(singleData)
            eventContents.value = _eventContents
        }
        Log.d("EVENTCONTENTS", _eventContents.toString())
    }
    fun getEventText() {
    }
}