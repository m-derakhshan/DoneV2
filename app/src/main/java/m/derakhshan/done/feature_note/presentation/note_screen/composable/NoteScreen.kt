package m.derakhshan.done.feature_note.presentation.note_screen.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import m.derakhshan.done.R
import m.derakhshan.done.core.presentation.composable.DefaultRadioButton
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType
import m.derakhshan.done.feature_note.presentation.note_screen.NoteEvent
import m.derakhshan.done.feature_note.presentation.note_screen.NoteViewModel
import m.derakhshan.done.ui.theme.spacing
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import m.derakhshan.done.feature_home.presentation.HomeNavGraph

@Composable
fun NoteScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: NoteViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val lazyState = rememberLazyListState()
    val noteIsDeleted = stringResource(id = R.string.note_is_deleted)
    val undo = stringResource(id = R.string.undo)
    val fabOffset by animateDpAsState(targetValue = state.fabOffset)

    Scaffold(
        scaffoldState = scaffoldState,
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
                navController.navigate(HomeNavGraph.AddEditNoteScreen.route)
            }, modifier = Modifier.offset(y = fabOffset)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add note")
            }
        }
    ) {
        Column {
            //--------------------(order section)--------------------//
            Column(
                modifier = Modifier
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                    )
                    .clip(shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .padding(MaterialTheme.spacing.small),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(id = R.string.order_notes),
                        style = MaterialTheme.typography.h5
                    )

                    IconButton(
                        onClick = { viewModel.onEvent(NoteEvent.ToggleOrderSectionVisibility) },
                    ) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "sort")
                    }
                }

                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            DefaultRadioButton(
                                selected = state.selectedOrderType is NoteOrderType.Date,
                                text = stringResource(id = R.string.date)
                            ) {
                                viewModel.onEvent(
                                    NoteEvent.OnNoteOrderTypeChange(NoteOrderType.Date)
                                )
                            }
                            DefaultRadioButton(
                                selected = state.selectedOrderType is NoteOrderType.Title,
                                text = stringResource(id = R.string.title)
                            ) {
                                viewModel.onEvent(NoteEvent.OnNoteOrderTypeChange(NoteOrderType.Title))
                            }
                            DefaultRadioButton(
                                selected = state.selectedOrderType is NoteOrderType.Color,
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
                                viewModel.onEvent(
                                    NoteEvent.OnNoteOrderSortTypeChange(
                                        NoteOrderSortType.Ascending
                                    )
                                )
                            }

                            DefaultRadioButton(
                                textStyle = MaterialTheme.typography.body2,
                                selected = state.selectedOrderSortType == NoteOrderSortType.Descending,
                                text = stringResource(id = R.string.descending)
                            ) {
                                viewModel.onEvent(
                                    NoteEvent.OnNoteOrderSortTypeChange(
                                        NoteOrderSortType.Descending
                                    )
                                )
                            }

                        }
                    }
                }
            }

            //--------------------(hide or show add button)--------------------//
            if (lazyState.isScrollingUp())
                viewModel.onEvent(NoteEvent.ListScrollUp)
            else
                viewModel.onEvent(NoteEvent.ListScrollDown)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyState,

                ) {
                items(
                    items = state.notes,
                    key = { it.id }
                ) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.medium)
                            .clickable {
                                navController.navigate(
                                    HomeNavGraph.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            }
                    ) {
                        viewModel.onEvent(NoteEvent.OnDeleteNoteClicked(note = note))
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = noteIsDeleted,
                                actionLabel = undo
                            )
                            if (result == SnackbarResult.ActionPerformed)
                                viewModel.onEvent(NoteEvent.RestoreNote)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousItemIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousItemIndex != firstVisibleItemIndex)
                previousItemIndex > firstVisibleItemIndex
            else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousItemIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value

}