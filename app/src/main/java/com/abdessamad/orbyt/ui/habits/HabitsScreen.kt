package com.abdessamad.orbyt.ui.habits

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdessamad.orbyt.data.local.entity.Habit
import com.abdessamad.orbyt.ui.components.EmptyState
import com.abdessamad.orbyt.ui.components.OrbytCard
import com.abdessamad.orbyt.ui.theme.*
import com.abdessamad.orbyt.ui.viewmodel.HabitViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(habitViewModel: HabitViewModel) {
    val habits by habitViewModel.allHabits.collectAsState()
    val todayLogs by habitViewModel.todayLogs.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp).statusBarsPadding()) {
                Text("Mes Habitudes", style = MaterialTheme.typography.displayLarge)
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
        if (habits.isEmpty()) {
            Box(modifier = Modifier.padding(padding)) {
                EmptyState(
                    emoji = "⭐",
                    title = "Construisez votre routine",
                    subtitle = "Créez votre première habitude pour commencer à suivre vos progrès.",
                    ctaText = "Créer une habitude",
                    onCtaClick = { showBottomSheet = true }
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(habits) { habit ->
                    val isDone = todayLogs.any { it.habitId == habit.id && it.isDone }
                    HabitItem(
                        habit = habit,
                        isDone = isDone,
                        onToggle = { habitViewModel.toggleHabit(habit.id, !isDone) },
                        onDelete = { habitViewModel.deleteHabit(habit) }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            AddHabitBottomSheetContent(
                onDismiss = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) showBottomSheet = false
                    }
                },
                onConfirm = { name, emoji ->
                    habitViewModel.insertHabit(Habit(name = name, emoji = emoji))
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) showBottomSheet = false
                    }
                }
            )
        }
    }
}

@Composable
private fun HabitItem(
    habit: Habit,
    isDone: Boolean,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    OrbytCard(
        onClick = onToggle
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = if (isDone) SuccessGreen.copy(alpha = 0.1f) else PrimaryLight,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = habit.emoji, fontSize = 24.sp)
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = if (isDone) TextSecondaryLight else TextPrimaryLight
                    )
                )
                Text(
                    text = if (isDone) "Complétée aujourd'hui" else "À faire aujourd'hui",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isDone) SuccessGreen else TextSecondaryLight
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Supprimer",
                    tint = DangerRed.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddHabitBottomSheetContent(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var emoji by remember { mutableStateOf("⭐") }

    val emojis = listOf("⭐", "💧", "🏃", "📚", "🧘", "🥗", "⏰", "🍎", "💊", "💪", "🚶", "💻")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Nouvelle habitude", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Nom de l'habitude") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrbytBlue,
                unfocusedBorderColor = Color(0xFFE2E8F0)
            )
        )

        Text("Icône", style = MaterialTheme.typography.labelMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            emojis.take(6).forEach { e ->
                Surface(
                    onClick = { emoji = e },
                    shape = CircleShape,
                    color = if (emoji == e) OrbytBlue.copy(alpha = 0.1f) else Color.Transparent,
                    border = BorderStroke(1.dp, if (emoji == e) OrbytBlue else Color(0xFFE2E8F0)),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = e)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { if (name.isNotBlank()) onConfirm(name, emoji) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OrbytBlue),
            enabled = name.isNotBlank()
        ) {
            Text("Créer l'habitude", fontWeight = FontWeight.SemiBold)
        }
    }
}
