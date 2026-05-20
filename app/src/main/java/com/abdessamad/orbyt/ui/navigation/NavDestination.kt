package com.abdessamad.orbyt.ui.navigation

sealed class NavDestination(val route: String) {
    object Welcome : NavDestination("welcome")
    object Dashboard : NavDestination("dashboard")
    object Tasks : NavDestination("tasks")
    object Habits : NavDestination("habits")
    object Agenda : NavDestination("agenda") // Repurposed for "Schedule" tab
    object Notes : NavDestination("notes")
    object Goals : NavDestination("goals")
    object Profile : NavDestination("profile")
}