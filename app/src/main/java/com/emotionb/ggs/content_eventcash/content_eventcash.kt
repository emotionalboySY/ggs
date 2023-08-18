package com.emotionb.ggs.content_eventcash

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.SubcomposeAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavEventCash() {

    val viewModelFactory = EventCashViewModelFactory()
    val viewModel = LocalViewModelStoreOwner.current?.let {
        ViewModelProvider(it, viewModelFactory)[EventCashViewModel::class.java]
    }
    val eventDataList by viewModel!!.eventContents.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.error)
    ) {
        if (viewModel != null) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 5.dp)
            ) {
                Log.d("COUNT", viewModel.eventContents.value.count().toString())
                items(eventDataList) {
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .fillMaxWidth(),
                        onClick = {

                        }
                    ) {
                        Column {
                            SubcomposeAsyncImage(
                                model = it.imgSrc,
                                contentDescription = "Event Image",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .clip(RectangleShape)
                                    .fillMaxWidth(),
                                loading = {
                                    Box(
                                        modifier = Modifier.size(100.dp)
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .size(20.dp),
                                            strokeWidth = 2.dp
                                        )
                                    }
                                }
                            )
                            Text(
                                modifier = Modifier.padding(
                                    top = 5.dp,
                                    bottom = 2.5.dp
                                ).fillMaxWidth(),
                                text = it.title,
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                fontSize = 25.sp,
                                style = TextStyle.Default,
                            )
                            Text(
                                modifier = Modifier.padding(
                                    top = 2.5.dp,
                                    bottom = 5.dp
                                ).fillMaxWidth(),
                                text = it.period,
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                fontSize = 15.sp,
                                style = TextStyle.Default
                            )
                        }
                    }
                }
            }
        }
        else {
            Text(
                text = "ViewModel is null",
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}