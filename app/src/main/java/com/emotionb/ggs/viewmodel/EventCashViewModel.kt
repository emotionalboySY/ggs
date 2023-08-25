package com.emotionb.ggs.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emotionb.ggs.model.CashShopData
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
    private lateinit var _cashShopResponse: HttpResponse
    private var _document: Document = Document("")

    private val _eventContents = MutableStateFlow<List<EventData>>(emptyList())
    val eventContents: StateFlow<List<EventData>> = _eventContents

    private val _cashShopContents = MutableStateFlow<List<CashShopData>>(emptyList())
    val cashShopContents: StateFlow<List<CashShopData>> = _cashShopContents

    init {
        getEventData()
        getCashShopData()
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
            val link = element.select("div.event_list_wrap dl dt a").attr("href")
            val singleData = EventData(
                imgSrc = imgSrc,
                title = title,
                period = period,
                link = link
            )
            Log.d("EVENTCONTENT_SINGLE", "${singleData.imgSrc} // ${singleData.title} // ${singleData.period} // ${singleData.link}")
            _eventContents.emit(_eventContents.value.plus(singleData))
        }
        Log.d("EVENTCONTENTS", _eventContents.value.toString())
    }

    private fun getCashShopData() {
        viewModelScope.launch {
            _cashShopResponse = service.getResponse(HttpRoutes.CASH_URL)
            refactorCashShopData()
        }
    }

    private suspend fun refactorCashShopData() {
        _document = Jsoup.parse(_cashShopResponse.readText())
        val elements = _document.select("div.cash_board ul li")
        for(element: Element in elements) {
            val imgSrc = element.select("div.cash_list_wrap dl dt a img").attr("src")
            val update = element.select("dd.data p a span").text()
            val title = element.select("dd.data p a").text().trim().split("업데이트")[1].trim()
            val period = element.select("dd.date p").text()
            val link = element.select("div.cash_list_wrap dl dt a").attr("href")
            val singleData = CashShopData(
                imgSrc = imgSrc,
                title = title,
                update = update,
                period = period,
                link = link
            )
            Log.d("CASHCONTEBT_SINGLE", "${singleData.imgSrc} // ${singleData.title} // ${singleData.update} // ${singleData.period} // ${singleData.link}")
            _cashShopContents.emit(_cashShopContents.value.plus(singleData))
        }
    }
}