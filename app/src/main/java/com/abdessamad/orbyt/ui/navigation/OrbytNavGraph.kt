package com.abdessamad.orbyt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import com.abdessamad.orbyt.ui.agenda.AgendaScreen
import com.abdessamad.orbyt.ui.dashboard.DashboardScreen
import com.abdessamad.orbyt.ui.habits.HabitsScreen
import com.abdessamad.orbyt.ui.notes.NotesScreen
import com.abdessamad.orbyt.ui.profile.ProfileScreen
import com.abdessamad.orbyt.ui.tasks.TasksScreen
import com.abdessamad.orbyt.ui.viewmodels.*
import com.abdessamad.orbyt.ui.welcome.WelcomeScreen

@Composable
fun OrbytNavGraph(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    habitViewModel: HabitViewModel,
    appointmentViewModel: AppointmentViewModel,
    noteViewModel: NoteViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.Welcome.route,
        modifier = modifier
    ) {
        composable(NavDestination.Welcome.route) {
            WelcomeScreen(
                onContinue = {
                    navController.navigate(NavDestination.Dashboard.route) {
                        popUpTo(NavDestination.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(NavDestination.Dashboard.route) {
            DashboardScreen(
                taskViewModel = taskViewModel,
                habitViewModel = habitViewModel,
                appointmentViewModel = appointmentViewModel,
                noteViewModel = noteViewModel,
                onNavigate = { destination ->
                    navController.navigate(destination.route)
                }
            )
        }
        composable(NavDestination.Tasks.route) {
            TasksScreen(taskViewModel = taskViewModel)
        }
        composable(NavDestination.Habits.route) {
            HabitsScreen(habitViewModel = habitViewModel)
        }
        composable(NavDestination.Agenda.route) {
            AgendaScreen(appointmentViewModel = appointmentViewModel)
        }
        composable(NavDestination.Notes.route) {
            NotesScreen(noteViewModel = noteViewModel)
        }
        composable(NavDestination.Profile.route) {
            ProfileScreen()
        }
    }
}