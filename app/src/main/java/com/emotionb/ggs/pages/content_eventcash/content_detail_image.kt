package com.emotionb.ggs.pages.content_eventcash

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.emotionb.ggs.viewmodel.EventCashViewModel

@Composable
fun EventCashDetailImage(
    listType: String,
    contentId: String,
    viewModel: EventCashViewModel
) {
    LaunchedEffect(null) {
        var link = "https://maplestory.nexon.com/News/"
        link += if(listType == "Event") {
            "Event/Ongoing/"
        } else "CashShop/Sale/"
        link += contentId

        Log.d("ImageDetailSource", link)

        viewModel.getImagesfromEventCashPage(link)
    }

    val imageSrcList by viewModel.imageSrcList.collectAsState(initial = emptyList())

    LazyColumn() {
        items(imageSrcList) {
            SubcomposeAsyncImage(
                model = it,
                contentDescription = "Image Detail",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}