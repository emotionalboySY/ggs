package com.emotionb.ggs.pages.content_eventcash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.emotionb.ggs.ui.theme.GgsTheme
import com.emotionb.ggs.viewmodel.EventCashViewModel
import com.emotionb.ggs.viewmodel.EventCashViewModelFactory
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavEventCash() {

    val viewModelFactory = EventCashViewModelFactory()
    val viewModel = LocalViewModelStoreOwner.current?.let {
        ViewModelProvider(it, viewModelFactory)[EventCashViewModel::class.java]
    }
    val eventDataList by viewModel!!.eventContents.collectAsState(initial = emptyList())
    val cashShopDataList by viewModel!!.cashShopContents.collectAsState(initial = emptyList())

    val pages = listOf("이벤트", "캐시샵 공지")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (viewModel != null) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                color = Color.White
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.primary
                    )
                }
            }

            HorizontalPager(
                count = pages.size,
                state = pagerState
            ) {
                if (it == 0) {
                    EventDetail(
                        eventDataList = eventDataList
                    )
                } else {
                    CashShopDetail(
                        cashShopDataList = cashShopDataList
                    )
                }
            }
        } else {
            Text(
                text = "ViewModel is null",
                textAlign = TextAlign.Center,
                color = Color.White,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventCashPreview() {
    GgsTheme {
        NavEventCash()
    }
}