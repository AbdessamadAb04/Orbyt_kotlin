package com.abdessamad.orbyt.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdessamad.orbyt.data.local.entity.Note
import com.abdessamad.orbyt.ui.components.EmptyState
import com.abdessamad.orbyt.ui.components.OrbytCard
import com.abdessamad.orbyt.ui.components.OrbytButton
import com.abdessamad.orbyt.ui.theme.DangerRed
import com.abdessamad.orbyt.ui.theme.OrbytBlue
import com.abdessamad.orbyt.ui.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(noteViewModel: NoteViewModel) {
    val notes by noteViewModel.notes.collectAsState()
    val searchQuery by noteViewModel.searchQuery.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { noteViewModel.onSearchQueryChange(it) },
                    placeholder = { Text("Rechercher dans vos notes...", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = OrbytBlue) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrbytBlue,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedContainerColor = Color(0xFFF8FAFC),
                        unfocusedContainerColor = Color(0xFFF8FAFC)
                    ),
                    singleLine = true
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true },
                containerColor = OrbytBlue,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter une note")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (notes.isEmpty()) {
                EmptyState(
                    emoji = "📝",
                    title = if (searchQuery.isEmpty()) "Aucune note" else "Aucun résultat",
                    subtitle = if (searchQuery.isEmpty()) "Commencez à capturer vos idées en appuyant sur le bouton +" else "Nous n'avons rien trouvé pour \"$searchQuery\"",
                    ctaText = if (searchQuery.isEmpty()) "Créer une note" else null,
                    onCtaClick = { showBottomSheet = true }
                )
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalItemSpacing = 12.dp
                ) {
                    items(notes) { note ->
                        NoteItem(
                            note = note,
                            onPin = { noteViewModel.togglePin(note) },
                            onDelete = { noteViewModel.deleteNote(note) }
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
            AddNoteSheetContent(
                onDismiss = { showBottomSheet = false },
                onConfirm = { content, category ->
                    noteViewModel.insertNote(Note(content = content, category = category))
                    showBottomSheet = false
                }
            )
        }
    }
}

@Composable
private fun NoteItem(
    note: Note,
    onPin: () -> Unit,
    onDelete: () -> Unit
) {
    OrbytCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = note.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = OrbytBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(OrbytBlue.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
                Icon(
                    imageVector = if (note.isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                    contentDescription = "Pin",
                    tint = if (note.isPinned) OrbytBlue else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { onPin() }
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Supprimer",
                        tint = DangerRed.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AddNoteSheetContent(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Général") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Nouvelle note",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Catégorie") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrbytBlue,
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Votre note") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrbytBlue,
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        OrbytButton(
            text = "Enregistrer la note",
            onClick = { if (content.isNotBlank()) onConfirm(content, category) },
            containerColor = OrbytBlue
        )
    }
}
