package com.abdessamad.orbyt.ui.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdessamad.orbyt.data.local.entity.Appointment
import com.abdessamad.orbyt.ui.components.EmptyState
import com.abdessamad.orbyt.ui.components.ModuleHeader
import com.abdessamad.orbyt.ui.components.OrbytCard
import com.abdessamad.orbyt.ui.theme.*
import com.abdessamad.orbyt.ui.viewmodel.AppointmentViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(appointmentViewModel: AppointmentViewModel) {
    val appointments by appointmentViewModel.allAppointments.collectAsState()
    val ddays = appointments.filter { it.isDDay }.sortedBy { it.dateTime }
    val regularEvents = appointments.filter { !it.isDDay }.sortedBy { it.dateTime }
    
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text("Mon Planning", style = MaterialTheme.typography.displayLarge)
                Text(
                    "Planifiez vos moments clés",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondaryLight
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true },
                containerColor = OrbytBlue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        if (appointments.isEmpty()) {
            Box(modifier = Modifier.padding(padding)) {
                EmptyState(
                    emoji = "📅",
                    title = "Rien de prévu",
                    subtitle = "Organisez votre emploi du temps et créez des comptes à rebours.",
                    ctaText = "Ajouter un événement",
                    onCtaClick = { showBottomSheet = true }
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                // D-Day Section
                if (ddays.isNotEmpty()) {
                    item {
                        ModuleHeader(
                            title = "Comptes à rebours",
                            onClick = { },
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(ddays) { dday ->
                                DDayCard(
                                    appointment = dday,
                                    onDelete = { appointmentViewModel.deleteAppointment(dday) }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                // Timeline Section
                item {
                    ModuleHeader(
                        title = "Événements à venir",
                        onClick = { },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                }

                if (regularEvents.isEmpty()) {
                    item {
                        Text(
                            "Aucun événement classique",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondaryLight,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                } else {
                    items(regularEvents) { event ->
                        TimelineItem(
                            appointment = event,
                            onDelete = { appointmentViewModel.deleteAppointment(event) }
                        )
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }, sheetState = sheetState) {
            AddAppointmentBottomSheetContent(
                onDismiss = { scope.launch { sheetState.hide() }.invokeOnCompletion { showBottomSheet = false } },
                onConfirm = { title, isDDay ->
                    appointmentViewModel.insertAppointment(
                        Appointment(
                            title = title,
                            dateTime = System.currentTimeMillis() + 86400000L, // Mock tomorrow
                            isDDay = isDDay
                        )
                    )
                    scope.launch { sheetState.hide() }.invokeOnCompletion { showBottomSheet = false }
                }
            )
        }
    }
}

@Composable
private fun DDayCard(appointment: Appointment, onDelete: () -> Unit) {
    val daysLeft = ((appointment.dateTime - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)).toInt()
    
    OrbytCard(modifier = Modifier.width(160.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = appointment.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (daysLeft > 0) "J-$daysLeft" else "Aujourd'hui",
                style = MaterialTheme.typography.displaySmall.copy(fontSize = 24.sp),
                color = WarningAmber,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(24.dp).align(Alignment.End)
            ) {
                Icon(Icons.Default.Delete, null, tint = DangerRed.copy(alpha = 0.5f), modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun TimelineItem(appointment: Appointment, onDelete: () -> Unit) {
    val date = Date(appointment.dateTime)
    val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
    val day = SimpleDateFormat("dd MMM", Locale.getDefault()).format(date)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(48.dp)) {
            Text(day, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Box(modifier = Modifier.width(2.dp).height(40.dp).background(Color(0xFFE2E8F0)))
            Surface(shape = CircleShape, color = OrbytBlue, modifier = Modifier.size(8.dp)) {}
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        OrbytCard(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(appointment.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                    Text("À $time", style = MaterialTheme.typography.labelMedium, color = TextSecondaryLight)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, null, tint = DangerRed.copy(alpha = 0.5f), modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
private fun AddAppointmentBottomSheetContent(onDismiss: () -> Unit, onConfirm: (String, Boolean) -> Unit) {
    var title by remember { mutableStateOf("") }
    var isDDay by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(24.dp).padding(bottom = 32.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Nouvel événement", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("Titre") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { isDDay = !isDDay }) {
            Checkbox(checked = isDDay, onCheckedChange = { isDDay = it })
            Text("Marquer comme Compte à rebours (D-Day)")
        }
        Button(
            onClick = { onConfirm(title, isDDay) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OrbytBlue),
            enabled = title.isNotBlank()
        ) {
            Text("Ajouter", fontWeight = FontWeight.Bold)
        }
    }
}
