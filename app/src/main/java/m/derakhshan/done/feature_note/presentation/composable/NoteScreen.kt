package m.derakhshan.done.feature_note.presentation.composable

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import m.derakhshan.done.R
import m.derakhshan.done.core.presentation.composable.DefaultRadioButton
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType
import m.derakhshan.done.feature_note.presentation.NoteEvent
import m.derakhshan.done.feature_note.presentation.NoteViewModel
import m.derakhshan.done.ui.theme.spacing

@Composable
fun NoteScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: NoteViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            TopAppBar {
                Text(
                    text = stringResource(id = R.string.notes),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // TODO: navigate to add or edit note screen
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add note")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = { viewModel.onEvent(NoteEvent.ToggleOrderSectionVisibility) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(imageVector = Icons.Default.Sort, contentDescription = "sort")
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {

                Column {

                    Text(
                        text = stringResource(id = R.string.order_notes_by),
                        style = MaterialTheme.typography.h5
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DefaultRadioButton(
                            selected = state.selectedOrderType == NoteOrderType.Date,
                            text = stringResource(id = R.string.date)
                        ) {
                            viewModel.onEvent(NoteEvent.OnNoteOrderTypeChange(NoteOrderType.Date))
                        }
                        DefaultRadioButton(
                            selected = state.selectedOrderType == NoteOrderType.Title,
                            text = stringResource(id = R.string.title)
                        ) {
                            viewModel.onEvent(NoteEvent.OnNoteOrderTypeChange(NoteOrderType.Title))
                        }
                        DefaultRadioButton(
                            selected = state.selectedOrderType == NoteOrderType.Color,
                            text = stringResource(id = R.string.color)
                        ) {
                            viewModel.onEvent(NoteEvent.OnNoteOrderTypeChange(NoteOrderType.Color))
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        DefaultRadioButton(
                            textStyle = MaterialTheme.typography.body2,
                            selected = state.selectedOrderSortType == NoteOrderSortType.Ascending,
                            text = stringResource(id = R.string.ascending)
                        ) {
                            viewModel.onEvent(NoteEvent.OnNoteOrderSortTypeChange(NoteOrderSortType.Ascending))
                        }

                        DefaultRadioButton(
                            textStyle = MaterialTheme.typography.body2,
                            selected = state.selectedOrderSortType == NoteOrderSortType.Descending,
                            text = stringResource(id = R.string.descending)
                        ) {
                            viewModel.onEvent(NoteEvent.OnNoteOrderSortTypeChange(NoteOrderSortType.Descending))
                        }

                    }
                }

            }
        }

    }
}