package com.abdessamad.orbyt.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdessamad.orbyt.data.local.entity.Appointment
import com.abdessamad.orbyt.data.local.entity.Habit
import com.abdessamad.orbyt.data.local.entity.HabitLog
import com.abdessamad.orbyt.data.local.entity.Goal
import com.abdessamad.orbyt.data.local.entity.GoalStatus
import com.abdessamad.orbyt.ui.components.ModuleHeader
import com.abdessamad.orbyt.ui.components.OrbytCard
import com.abdessamad.orbyt.ui.navigation.NavDestination
import com.abdessamad.orbyt.ui.theme.*
import com.abdessamad.orbyt.ui.viewmodel.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(
    taskViewModel: TaskViewModel,
    habitViewModel: HabitViewModel,
    appointmentViewModel: AppointmentViewModel,
    noteViewModel: NoteViewModel,
    goalViewModel: GoalViewModel,
    onNavigate: (NavDestination) -> Unit
) {
    val pinnedNote by noteViewModel.pinnedNote.collectAsState()
    val allAppointments by appointmentViewModel.allAppointments.collectAsState()
    val habits by habitViewModel.allHabits.collectAsState()
    val todayLogs by habitViewModel.todayLogs.collectAsState()
    val goals by goalViewModel.allGoals.collectAsState()

    var selectedDate by remember { mutableStateOf(Date()) }

    Scaffold(
        topBar = { DashboardHeader() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 1. Pinned Note (Quote style)
            NoteWidget(
                content = pinnedNote?.content,
                onClick = { onNavigate(NavDestination.Notes) }
            )

            DashboardModuleContainer {
                // 2. Agenda Module (Full-width)
                AgendaModule(
                    appointments = allAppointments,
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it },
                    onClick = { onNavigate(NavDestination.Agenda) }
                )
            }

            DashboardModuleContainer {
                // 3. Habits (Top 5)
                HabitudesWidget(
                    habits = habits,
                    logs = todayLogs,
                    onClick = { onNavigate(NavDestination.Habits) }
                )
            }

            DashboardModuleContainer {
                // 4. Objectives (Top 5)
                GoalsWidget(
                    goals = goals,
                    onClick = { onNavigate(NavDestination.Goals) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DashboardModuleContainer(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = content
    )
}

@Composable
private fun DashboardHeader() {
    val today = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date())
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Orbyt",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = today.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondaryLight
        )
    }
}

@Composable
private fun ModuleHeader(
    title: String,
    onClick: () -> Unit,
    containerColor: Color? = null
) {
    com.abdessamad.orbyt.ui.components.ModuleHeader(
        title = title,
        onClick = onClick,
        containerColor = containerColor
    )
}

@Composable
private fun NoteWidget(
    content: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(OrbytBlue.copy(alpha = 0.04f))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.FormatQuote,
                contentDescription = null,
                tint = OrbytBlue.copy(alpha = 0.4f),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = content ?: "Explorez votre potentiel. Votre voyage commence ici.",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontStyle = FontStyle.Italic
                ),
                color = TextSecondaryLight,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun AgendaModule(
    appointments: List<Appointment>,
    selectedDate: Date,
    onDateSelected: (Date) -> Unit,
    onClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ModuleHeader(
            title = "Votre Agenda",
            onClick = onClick,
            containerColor = Color.Transparent
        )

        // Date Row
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val weekDates = List(7) {
            val date = calendar.time
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            date
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weekDates.forEach { date ->
                val isSelected = SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(date) == 
                               SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(selectedDate)
                val isToday = SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(date) == 
                             SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(Date())
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) OrbytBlue else Color.Transparent)
                        .clickable { onDateSelected(date) }
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = SimpleDateFormat("EEE", Locale.getDefault()).format(date).take(1),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected) Color.White else TextSecondaryLight
                    )
                    Text(
                        text = SimpleDateFormat("d", Locale.getDefault()).format(date),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else if (isToday) OrbytBlue else TextPrimaryLight
                    )
                }
            }
        }

        // Events for selected date
        val selectedDateStr = SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(selectedDate)
        val dayEvents = appointments.filter { 
            SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(Date(it.dateTime)) == selectedDateStr
        }.sortedBy { it.dateTime }

        if (dayEvents.isEmpty()) {
            OrbytCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent
            ) {
                Text(
                    "Aucun événement pour ce jour",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight
                )
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                dayEvents.forEach { event ->
                    OrbytCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onClick
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(event.dateTime))
                            Text(
                                text = time,
                                style = MaterialTheme.typography.labelLarge,
                                color = OrbytBlue,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(50.dp)
                            )
                            VerticalDivider(
                                modifier = Modifier.height(24.dp).padding(horizontal = 12.dp),
                                color = OrbytBlue.copy(alpha = 0.2f)
                            )
                            Text(
                                text = event.title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HabitudesWidget(
    habits: List<Habit>,
    logs: List<HabitLog>,
    onClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ModuleHeader(
            title = "Habitudes",
            onClick = onClick,
            containerColor = Color.Transparent
        )

        OrbytCard(onClick = onClick) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                habits.take(5).forEach { habit ->
                    val isDone = logs.any { it.habitId == habit.id && it.isDone }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (isDone) SuccessGreen else Color(0xFFE2E8F0))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = habit.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (isDone) TextSecondaryLight else TextPrimaryLight
                            )
                        }
                        if (isDone) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                        }
                    }
                }
                
                val progress = if (habits.isNotEmpty()) {
                    logs.count { it.isDone }.toFloat() / habits.size
                } else 0f
                
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                    color = SuccessGreen,
                    trackColor = Color(0xFFF1F5F9)
                )
            }
        }
    }
}

@Composable
private fun GoalsWidget(
    goals: List<Goal>,
    onClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ModuleHeader(
            title = "Objectifs",
            onClick = onClick,
            containerColor = Color.Transparent
        )

        OrbytCard(onClick = onClick) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                if (goals.isEmpty()) {
                    Text("Aucun objectif actif", style = MaterialTheme.typography.bodyMedium, color = TextSecondaryLight)
                } else {
                    goals.take(5).forEach { goal ->
                        val progress = if (goal.status == GoalStatus.ACHIEVED) 1f else 0.5f // Placeholder logic
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = goal.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "${(progress * 100).toInt()}%",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OrbytBlue
                                )
                            }
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape),
                                color = OrbytBlue,
                                trackColor = OrbytBlue.copy(alpha = 0.1f)
                            )
                        }
                    }
                }
            }
        }
    }
}


