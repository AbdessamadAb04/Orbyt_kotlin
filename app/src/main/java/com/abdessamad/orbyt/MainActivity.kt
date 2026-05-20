package com.abdessamad.orbyt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abdessamad.orbyt.ui.navigation.NavDestination
import com.abdessamad.orbyt.ui.navigation.OrbytNavGraph
import com.abdessamad.orbyt.ui.theme.OrbytTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Home : BottomNavItem(NavDestination.Dashboard.route, "Home", Icons.Outlined.Home, Icons.Filled.Home)
    object Schedule : BottomNavItem(NavDestination.Agenda.route, "Schedule", Icons.Outlined.DateRange, Icons.Filled.DateRange)
    object Profile : BottomNavItem(NavDestination.Profile.route, "Profile", Icons.Outlined.Person, Icons.Filled.Person)
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OrbytTheme {
                val navController = rememberNavController()
                
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val currentRoute = currentDestination?.route

                val bottomItems = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Schedule,
                    BottomNavItem.Profile
                )

                Scaffold(
                    bottomBar = {
                        // Only show bottom bar if we are not on the Welcome screen
                        if (currentRoute != NavDestination.Welcome.route) {
                            NavigationBar {
                                bottomItems.forEach { item ->
                                    val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                if (selected) item.selectedIcon else item.icon,
                                                contentDescription = item.label
                                            )
                                        },
                                        label = { Text(item.label) },
                                        selected = selected,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    OrbytNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        taskViewModel = viewModel(),
                        habitViewModel = viewModel(),
                        appointmentViewModel = viewModel(),
                        noteViewModel = viewModel()
                    )
                }
            }
        }
    }
}
