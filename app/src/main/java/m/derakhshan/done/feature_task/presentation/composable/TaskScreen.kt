package m.derakhshan.done.feature_task.presentation.composable


import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import m.derakhshan.done.R
import m.derakhshan.done.core.presentation.composable.BackSwipeGesture
import m.derakhshan.done.feature_note.presentation.add_edit_note_screen.AddEditNoteEvent
import m.derakhshan.done.feature_note.presentation.note_screen.NoteEvent
import m.derakhshan.done.feature_note.presentation.note_screen.composable.isScrollingUp
import m.derakhshan.done.feature_task.domain.model.TaskModel
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

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
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
            modifier = Modifier.draggable(
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
                            TaskItem(
                                task = item,
                                modifier = Modifier.animateItemPlacement(),
                                onCheckChange = {
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

        AnimatedVisibility(visible = state.showAddTaskSection,
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { 5 * it } + fadeOut()
        ) {
            AddTaskSection(
                taskColor = LightBlue,
                taskColorSelected = {},
                panelCloseListener = { viewModel.onEvent(TaskEvent.NewTaskPanelClosed) }
            )
        }


    }


}

@Composable
private fun AddTaskSection(
    taskColor: Color,
    taskColorSelected: (Color) -> Unit,
    panelCloseListener: () -> Unit
) {
    val horizontalScrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }


    val animateOffset by animateDpAsState(targetValue = offset.dp)
    Column(
        modifier = Modifier
            .offset(y = animateOffset)
            .fillMaxWidth()
            .shadow(25.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(LightGray, shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .padding(MaterialTheme.spacing.small)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState {
                    if (offset + (it * 0.25f) > 0)
                        offset += (it * 0.25f)
                },
                onDragStopped = {
                    if (offset > 65) panelCloseListener()
                    offset = 0f
                }
            )
    ) {

        Box(
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.small)
                .width(100.dp)
                .height(2.dp)
                .background(Blue, shape = RoundedCornerShape(10.dp))
                .align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(horizontalScrollState),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.EventAvailable,
                    contentDescription = "add date",
                    tint = DarkBlue
                )
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "add time",
                    tint = DarkBlue
                )
            }

            TaskModel.colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .background(color = color, shape = CircleShape)
                        .border(
                            3.dp, color = if (taskColor == color)
                                Color(
                                    ColorUtils.blendARGB(
                                        LightBlack.toArgb(), color.toArgb(), 0.7f
                                    )
                                )
                            else
                                Color.Transparent, shape = CircleShape
                        )
                        .clip(CircleShape)
                        .clickable {
                            taskColorSelected(color)
                        }
                )
                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))

            }
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .background(color = VeryLightBlue, shape = RoundedCornerShape(10.dp))
                    .weight(1f)
                    .padding(MaterialTheme.spacing.medium)
            ) {
                BasicTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "add task",
                    tint = DarkBlue
                )
            }
        }

    }
}