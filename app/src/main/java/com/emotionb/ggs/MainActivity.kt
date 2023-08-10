package com.emotionb.ggs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emotionb.ggs.content_eventcash.EventCashViewModel
import com.emotionb.ggs.content_eventcash.EventCashViewModelFactory
import com.emotionb.ggs.content_eventcash.NavEventCash
import com.emotionb.ggs.content_main.NavMain
import com.emotionb.ggs.content_profile.NavProfile
import com.emotionb.ggs.content_settings.NavSettings
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

    val eventCashViewModel = ViewModelProvider(
        this,
        EventCashViewModelFactory()
    )[EventCashViewModel::class.java]

    Scaffold (
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController, eventCashViewModel = eventCashViewModel)
        }
    }
}

@Composable
fun BottomNavigation(
    navController: NavHostController
) {
    var items = listOf<BottomNavItem>(
        BottomNavItem.Main,
        BottomNavItem.Profile,
        BottomNavItem.EventCash,
        BottomNavItem.Settings
    )

    androidx.compose.material3.NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF3F414E)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = {
                    Text(stringResource(id = item.title), fontSize = 9.sp)
                },
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = false,
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
fun NavigationGraph(navController: NavHostController, eventCashViewModel: EventCashViewModel) {

    NavHost(navController = navController, startDestination = BottomNavItem.Main.screenRoute) {
        composable(BottomNavItem.Main.screenRoute) {
            NavMain()
        }
        composable(BottomNavItem.Profile.screenRoute) {
            NavProfile()
        }
        composable(BottomNavItem.EventCash.screenRoute) {
            NavEventCash(viewModel = eventCashViewModel)
        }
        composable(BottomNavItem.Settings.screenRoute) {
            NavSettings()
        }
    }
}

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
) {
    object Main : BottomNavItem(R.string.nav_title_main, R.drawable.ic_launcher_foreground, MAIN)
    object Profile :
        BottomNavItem(R.string.nav_title_profile, R.drawable.ic_launcher_foreground, PROFILE)

    object EventCash :
        BottomNavItem(R.string.nav_title_eventcash, R.drawable.ic_launcher_foreground, EVENTCASH)

    object Settings :
        BottomNavItem(R.string.nav_title_settings, R.drawable.ic_launcher_foreground, SETTINGS)
}

@Preview(showBackground = true)
@Composable
fun GGSPreview() {
    GgsTheme {
        MainScreen()
    }
}