package com.abdessamad.orbyt.ui.goals

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdessamad.orbyt.data.local.entity.Goal
import com.abdessamad.orbyt.data.local.entity.GoalDomain
import com.abdessamad.orbyt.data.local.entity.GoalStatus
import com.abdessamad.orbyt.ui.components.EmptyState
import com.abdessamad.orbyt.ui.components.OrbytButton
import com.abdessamad.orbyt.ui.components.OrbytCard
import com.abdessamad.orbyt.ui.theme.DangerRed
import com.abdessamad.orbyt.ui.theme.OrbytBlue
import com.abdessamad.orbyt.ui.theme.SuccessGreen
import com.abdessamad.orbyt.ui.viewmodel.GoalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(goalViewModel: GoalViewModel) {
    val goals by goalViewModel.allGoals.collectAsState()
    var selectedDomain by remember { mutableStateOf<GoalDomain?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val filteredGoals = if (selectedDomain == null) goals else goals.filter { it.domain == selectedDomain }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .statusBarsPadding()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Objectifs",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedDomain == null,
                            onClick = { selectedDomain = null },
                            label = { Text("Tous") },
                            shape = RoundedCornerShape(20.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = OrbytBlue,
                                selectedLabelColor = Color.White
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = Color(0xFFE2E8F0),
                                selectedBorderColor = Color.Transparent,
                                enabled = true,
                                selected = selectedDomain == null
                            )
                        )
                    }
                    items(GoalDomain.values()) { domain ->
                        FilterChip(
                            selected = selectedDomain == domain,
                            onClick = { selectedDomain = domain },
                            label = {
                                Text(when (domain) {
                                    GoalDomain.ETUDES -> "📚 Études"
                                    GoalDomain.TRAVAIL -> "💼 Travail"
                                    GoalDomain.SPORT -> "🏃 Sport"
                                    GoalDomain.PERSO -> "🌱 Perso"
                                })
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = OrbytBlue,
                                selectedLabelColor = Color.White
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = Color(0xFFE2E8F0),
                                selectedBorderColor = Color.Transparent,
                                enabled = true,
                                selected = selectedDomain == domain
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true },
                containerColor = OrbytBlue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter un objectif")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (filteredGoals.isEmpty()) {
                EmptyState(
                    emoji = "🎯",
                    title = "Aucun objectif",
                    subtitle = if (selectedDomain == null) "Fixez-vous un nouvel objectif pour rester motivé !" else "Aucun objectif dans cette catégorie.",
                    ctaText = if (selectedDomain == null) "Créer un objectif" else null,
                    onCtaClick = { showBottomSheet = true }
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredGoals) { goal ->
                        GoalItem(
                            goal = goal,
                            onAchieve = { goalViewModel.markGoalAchieved(goal) },
                            onDelete = { goalViewModel.deleteGoal(goal) }
                        )
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            AddGoalSheetContent(
                onDismiss = { showBottomSheet = false },
                onConfirm = { title, domain ->
                    goalViewModel.insertGoal(Goal(title = title, domain = domain))
                    showBottomSheet = false
                }
            )
        }
    }
}

@Composable
private fun GoalItem(
    goal: Goal,
    onAchieve: () -> Unit,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    OrbytCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            when (goal.status) {
                                GoalStatus.ACHIEVED -> SuccessGreen.copy(alpha = 0.1f)
                                else -> OrbytBlue.copy(alpha = 0.1f)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (goal.domain) {
                            GoalDomain.ETUDES -> "📚"
                            GoalDomain.TRAVAIL -> "💼"
                            GoalDomain.SPORT -> "🏃"
                            GoalDomain.PERSO -> "🌱"
                        },
                        fontSize = 20.sp
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = goal.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (goal.status == GoalStatus.ACHIEVED) 
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f) 
                        else 
                            MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = when (goal.status) {
                            GoalStatus.IN_PROGRESS -> "En cours"
                            GoalStatus.ACHIEVED -> "Complété"
                            GoalStatus.ABANDONED -> "Abandonné"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = when (goal.status) {
                            GoalStatus.ACHIEVED -> SuccessGreen
                            GoalStatus.ABANDONED -> DangerRed
                            else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        }
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (goal.status == GoalStatus.IN_PROGRESS) {
                        TextButton(
                            onClick = onAchieve,
                            colors = ButtonDefaults.textButtonColors(contentColor = SuccessGreen)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Marquer comme atteint")
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = DangerRed.copy(alpha = 0.6f))
                    }
                }
            }
        }
    }
}

@Composable
private fun AddGoalSheetContent(
    onDismiss: () -> Unit,
    onConfirm: (String, GoalDomain) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var domain by remember { mutableStateOf(GoalDomain.PERSO) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Nouvel objectif",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titre de l'objectif") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrbytBlue,
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Text("Domaine", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(GoalDomain.values()) { d ->
                FilterChip(
                    selected = domain == d,
                    onClick = { domain = d },
                    label = {
                        Text(when (d) {
                            GoalDomain.ETUDES -> "Études"
                            GoalDomain.TRAVAIL -> "Travail"
                            GoalDomain.SPORT -> "Sport"
                            GoalDomain.PERSO -> "Perso"
                        })
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = OrbytBlue,
                        selectedLabelColor = Color.White
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = Color(0xFFE2E8F0),
                        selectedBorderColor = Color.Transparent,
                        enabled = true,
                        selected = domain == d
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OrbytButton(
            text = "Créer l'objectif",
            onClick = { if (title.isNotBlank()) onConfirm(title, domain) },
            containerColor = OrbytBlue
        )
    }
}
