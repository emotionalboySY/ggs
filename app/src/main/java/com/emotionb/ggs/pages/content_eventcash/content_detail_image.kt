package com.emotionb.ggs.pages.content_eventcash

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.SubcomposeAsyncImage
import com.emotionb.ggs.viewmodel.EventCashViewModel

@Composable
fun EventCashDetailImage(
    listType: String,
    contentId: String,
    viewModel: EventCashViewModel
) {
    var link = "https://m.maplestory.nexon.com/News/"
    link += if(listType == "Event") {
        "Event/Ongoing/"
    } else "CashShop/Sale/"
    link += contentId

//    LaunchedEffect(null) {
//        link += if(listType == "Event") {
//            "Event/Ongoing/"
//        } else "CashShop/Sale/"
//        link += contentId
//
//        Log.d("ImageDetailSource", link)
//
//        viewModel.getImagesfromEventCashPage(link)
//    }

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(link)
        }
    }, update = {
        it.loadUrl(link)
    })

//    val imageSrcList by viewModel.imageSrcList.collectAsState(initial = emptyList())
//    var scale by remember {mutableStateOf(1f)}
//    var rotation by remember {mutableStateOf(0f)}
//    var offset by remember {mutableStateOf(Offset.Zero)}
//    var state = rememberTransformableState{ zoomChange, panChange, rotationChange ->
//        val newScale = scale * zoomChange
//        if(newScale in 1.0..1.5) {
//            scale = newScale
//            offset += panChange
//        } else if(newScale < 1.0) {
//            scale = 1.0f
//            offset = Offset.Zero
//        }
//    }
//
//
//    LazyColumn(
//        modifier = Modifier.fillMaxWidth()
//            .graphicsLayer(
//                scaleX = scale,
//                scaleY = scale,
//                translationX = offset.x,
//                translationY = offset.y
//            )
//            .transformable(state = state)
//            .scrollable(
//                state = rememberScrollState(),
//                orientation = Orientation.Vertical,
//                enabled = false
//            )
//    ) {
//        items(imageSrcList) {
//            SubcomposeAsyncImage(
//                model = it,
//                contentDescription = "Image Detail",
//                modifier = Modifier
//                    .fillMaxWidth(),
//                contentScale = ContentScale.FillWidth
//            )
//        }
//    }
}