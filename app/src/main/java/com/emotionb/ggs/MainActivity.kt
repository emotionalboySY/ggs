package com.emotionb.ggs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emotionb.ggs.pages.content_eventcash.NavEventCashScreen
import com.emotionb.ggs.pages.content_home.NavHome
import com.emotionb.ggs.pages.content_simulation.NavSimulation
import com.emotionb.ggs.pages.content_mypage.NavMypage
import com.emotionb.ggs.ui.theme.GgsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            GgsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                BottomNavigation(navController = navController)
            }
        }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavigation(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavItem.Quest,
        BottomNavItem.Simulation,
        BottomNavItem.Home,
        BottomNavItem.EventCash,
        BottomNavItem.Mypage
    )

    androidx.compose.material3.NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF3F414E),
        modifier = Modifier.height(80.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    if (item.screenRoute == HOME) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title),
                            modifier = Modifier
                                .width(45.dp)
                                .height(45.dp)
                                .border(
                                    border = BorderStroke(
                                        if (currentRoute == item.screenRoute) 3.dp else 1.dp,
                                        color = if (currentRoute == item.screenRoute) Color(
                                            0xFF9B94FF
                                        ) else Color.Black
                                    ),
                                    shape = CircleShape,
                                )
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title),
                            modifier = Modifier
                                .width(26.dp)
                                .height(26.dp)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                ),
                label = {
                    Text(stringResource(id = item.title), fontSize = 13.sp)
                },
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                interactionSource = MutableInteractionSource()
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Quest.screenRoute) {
            NavHome()
        }
        composable(BottomNavItem.Simulation.screenRoute) {
            NavSimulation()
        }
        composable(BottomNavItem.Home.screenRoute) {
            NavHome()
        }
        composable(BottomNavItem.EventCash.screenRoute) {
            NavEventCashScreen()
        }
        composable(BottomNavItem.Mypage.screenRoute) {
            NavMypage()
        }
    }
}

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
) {
    data object Quest :
        BottomNavItem(R.string.nav_title_quest, R.drawable.ic_launcher_foreground, QUEST)

    data object Simulation :
        BottomNavItem(R.string.nav_title_simulation, R.drawable.ic_launcher_foreground, SIMULATION)

    data object Home :
        BottomNavItem(R.string.nav_title_home, R.drawable.ic_launcher_foreground, HOME)

    data object EventCash :
        BottomNavItem(R.string.nav_title_eventcash, R.drawable.ic_launcher_foreground, EVENTCASH)

    data object Mypage :
        BottomNavItem(R.string.nav_title_mypage, R.drawable.ic_launcher_foreground, MYPAGE)
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

@Preview(showBackground = true)
@Composable
fun GGSPreview() {
    GgsTheme {
        MainScreen()
    }
}