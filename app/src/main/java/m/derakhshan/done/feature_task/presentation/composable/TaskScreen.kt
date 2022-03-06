package m.derakhshan.done.feature_task.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import m.derakhshan.done.R
import m.derakhshan.done.core.presentation.composable.BackSwipeGesture
import m.derakhshan.done.feature_note.presentation.note_screen.composable.isScrollingUp
import m.derakhshan.done.feature_task.presentation.TaskEvent
import m.derakhshan.done.feature_task.presentation.TaskViewModel
import m.derakhshan.done.ui.theme.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: TaskViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val lazyState = rememberLazyListState()
    val fabOffset by animateDpAsState(targetValue = state.fabOffset)
    var offset by remember {
        mutableStateOf(0f)
    }

    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    text = stringResource(id = R.string.tasks),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(TaskEvent.OnNewTaskClicked) },
                modifier = Modifier.offset(y = fabOffset)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add note")
            }
        },
        modifier = Modifier
            .padding(paddingValues)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offset += (delta * 0.2f)
                },
                onDragStopped = {
                    if (offset > 90)
                        navController.navigateUp()
                    offset = 0f
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (lazyState.isScrollingUp())
                viewModel.onEvent(TaskEvent.ListScrollUp)
            else
                viewModel.onEvent(TaskEvent.ListScrollDown)

            LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyState) {
                for (taskGroup in state.tasks) {
                    stickyHeader {
                        Text(
                            text = taskGroup.title,
                            modifier = Modifier
                                .padding(bottom = MaterialTheme.spacing.small)
                                .fillMaxWidth()
                                .shadow(2.dp)
                                .background(LightBlue)
                                .padding(MaterialTheme.spacing.small),
                            textAlign = TextAlign.Start,
                            color = White
                        )
                    }
                    items(
                        items = (taskGroup.tasks),
                        key = { it.id }
                    ) { item ->
                        TaskItem(task = item, onCheckChange = {
                            viewModel.onEvent(
                                TaskEvent.OnTaskCheckClicked(
                                    task = item,
                                    checked = it
                                )
                            )
                        })
                    }
                }
            }
        }

        BackSwipeGesture(offset = offset)
    }
}