package com.abdessamad.orbyt.ui.tasks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdessamad.orbyt.data.local.entity.Priority
import com.abdessamad.orbyt.data.local.entity.Status
import com.abdessamad.orbyt.data.local.entity.Task
import com.abdessamad.orbyt.ui.components.EmptyState
import com.abdessamad.orbyt.ui.components.ModuleHeader
import com.abdessamad.orbyt.ui.components.OrbytCard
import com.abdessamad.orbyt.ui.theme.*
import com.abdessamad.orbyt.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.allTasks.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp).statusBarsPadding()) {
                Text("Mes Tâches", style = MaterialTheme.typography.displayLarge)
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
        if (tasks.isEmpty()) {
            Box(modifier = Modifier.padding(padding)) {
                EmptyState(
                    emoji = "✅",
                    title = "Tout est fait !",
                    subtitle = "Aucune tâche pour le moment.",
                    ctaText = "Ajouter une tâche",
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
                val grouped = tasks.sortedBy { it.status == Status.DONE }
                items(grouped, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onToggle = { taskViewModel.updateTask(task.copy(status = if (task.status == Status.DONE) Status.TODO else Status.DONE)) },
                        onDelete = { taskViewModel.deleteTask(task) }
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
            AddTaskBottomSheetContent(
                onDismiss = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) showBottomSheet = false
                    }
                },
                onConfirm = { title, priority ->
                    taskViewModel.insertTask(Task(title = title, priority = priority))
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) showBottomSheet = false
                    }
                }
            )
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    val isDone = task.status == Status.DONE
    val alpha by animateFloatAsState(if (isDone) 0.5f else 1f, label = "alpha")

    OrbytCard(
        modifier = Modifier.alpha(alpha),
        onClick = onToggle
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isDone) SuccessGreen else Color.Transparent)
                    .border(
                        width = 2.dp,
                        color = if (isDone) SuccessGreen else TextSecondaryLight.copy(alpha = 0.5f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isDone) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                when (task.priority) {
                                    Priority.HIGH -> DangerRed
                                    Priority.NORMAL -> OrbytBlue
                                    Priority.LOW -> SuccessGreen
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
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
private fun AddTaskBottomSheetContent(
    onDismiss: () -> Unit,
    onConfirm: (String, Priority) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.NORMAL) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Nouvelle tâche", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("Qu'allez-vous faire ?") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrbytBlue,
                unfocusedBorderColor = Color(0xFFE2E8F0)
            )
        )

        Text("Priorité", style = MaterialTheme.typography.labelMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Priority.values().forEach { p ->
                val isSelected = priority == p
                val color = when (p) {
                    Priority.HIGH -> DangerRed
                    Priority.NORMAL -> OrbytBlue
                    Priority.LOW -> SuccessGreen
                }
                Surface(
                    onClick = { priority = p },
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSelected) color.copy(alpha = 0.1f) else Color.Transparent,
                    border = BorderStroke(1.dp, if (isSelected) color else Color(0xFFE2E8F0)),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(modifier = Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = when (p) {
                                Priority.HIGH -> "Haute"
                                Priority.NORMAL -> "Normale"
                                Priority.LOW -> "Basse"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) color else TextSecondaryLight
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { if (title.isNotBlank()) onConfirm(title, priority) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OrbytBlue),
            enabled = title.isNotBlank()
        ) {
            Text("Ajouter la tâche", fontWeight = FontWeight.SemiBold)
        }
    }
}
