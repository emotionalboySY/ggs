package com.emotionb.ggs.content_eventcash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emotionb.ggs.model.EventData
import com.emotionb.ggs.service.HttpRequestService
import com.emotionb.ggs.service.HttpRoutes
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class EventCashViewModel : ViewModel() {

    private val service = HttpRequestService.create()

    private lateinit var _eventResponse: HttpResponse
    private var _document: Document = Document("")

    private val _eventContents = MutableStateFlow<List<EventData>>(emptyList())
    val eventContents: StateFlow<List<EventData>> = _eventContents

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
            Log.d("EVENTCONTENT_SINGLE", "${singleData.imgSrc} // ${singleData.title} // ${singleData.period}")
            _eventContents.emit(_eventContents.value.plus(singleData))
        }
        Log.d("EVENTCONTENTS", _eventContents.value.toString())
    }
}