package com.emotionb.ggs.content_eventcash

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.AsyncImage
import com.emotionb.ggs.R

@Composable
fun NavEventCash() {

    var viewModelFactory = EventCashViewModelFactory()
    var viewModel = LocalViewModelStoreOwner.current?.let {
        ViewModelProvider(it, viewModelFactory)
        .get(EventCashViewModel::class.java)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.error)
    ) {
        if (viewModel != null) {
            LazyColumn(

            ) {
                Log.d("COUNT", viewModel.eventContents.value!!.count().toString())
                items(viewModel.eventContents.value!!.count()) {
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        modifier = Modifier.padding(all = 5.dp)
                    ) {
                        Column(

                        ) {
                            AsyncImage(
                                model = viewModel.eventContents.value!![it].imgSrc,
                                contentDescription = "Event Image",
                                contentScale = ContentScale.Inside,
                                modifier = Modifier.clip(
                                    RectangleShape
                                )
                            )
                            Text(
                                text = viewModel.eventContents.value!![it].title,
                                textAlign = TextAlign.Center,
                                style = TextStyle.Default,
                            )
                            Text(
                                text = viewModel.eventContents.value!![it].period,
                                textAlign = TextAlign.Center,
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