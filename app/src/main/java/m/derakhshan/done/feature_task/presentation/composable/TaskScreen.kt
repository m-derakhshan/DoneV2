package m.derakhshan.done.feature_task.presentation.composable



import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import m.derakhshan.done.R
import m.derakhshan.done.core.presentation.composable.BackSwipeGesture
import m.derakhshan.done.feature_note.presentation.note_screen.composable.isScrollingUp
import m.derakhshan.done.feature_task.domain.model.MyCalendar
import m.derakhshan.done.feature_task.domain.model.TaskDate
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
    var offset by remember { mutableStateOf(0f) }
    val scaffoldState = rememberScaffoldState()
    val infiniteTransition = rememberInfiniteTransition()
    val syncRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (state.isSyncIconRotating)360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    BackHandler(enabled = state.showAddTaskSection) {
        viewModel.onEvent(TaskEvent.NewTaskPanelClosed)
    }

    LaunchedEffect(viewModel.snackBar) {
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        viewModel.snackBar.collectLatest { message ->
            if (message.isNotBlank()) {
                val undo =
                    scaffoldState.snackbarHostState.showSnackbar(message, actionLabel = "Undo")
                if (undo == SnackbarResult.ActionPerformed)
                    viewModel.onEvent(TaskEvent.TaskUndo)
            }

        }
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
                    Box {

                        if (state.syncNumber > 0)
                            Box(
                                modifier = Modifier
                                    .size(21.dp)
                                    .clip(shape = CircleShape)
                                    .background(color = Red, shape = CircleShape)
                            ) {
                                Text(
                                    text = state.syncNumber.toString(),
                                    color = White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.align(Alignment.Center),
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        IconButton(
                            onClick = { viewModel.onEvent(TaskEvent.OnTaskSyncClicked) },
                            enabled = state.syncNumber > 0
                        ) {
                            Icon(
                                imageVector = state.syncIcon,
                                contentDescription = "sync",
                                modifier = Modifier.rotate(syncRotation)
                            )
                        }

                        Text(
                            text = stringResource(id = R.string.tasks),
                            style = MaterialTheme.typography.h6,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                        )
                    }

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
            ), scaffoldState = scaffoldState
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
                                    .padding(bottom = MaterialTheme.spacing.medium)
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
                                onItemSwiped = {
                                    viewModel.onEvent(TaskEvent.TaskDeleteClicked(task = item))
                                },
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
            exit = fadeOut(tween(200))
        ) {
            AddTaskSection(
                isAddTaskEnable = state.isAddTaskEnable,
                taskDescription = state.newTaskDescription,
                taskColor = state.newTaskColor,
                taskColorSelected = { color ->
                    viewModel.onEvent(
                        TaskEvent.NewTaskColorSelected(
                            color
                        )
                    )
                },
                taskDescriptionChanged = { viewModel.onEvent(TaskEvent.NewTaskDescriptionChanged(it)) },
                panelCloseListener = { viewModel.onEvent(TaskEvent.NewTaskPanelClosed) },
                saveTaskListener = { viewModel.onEvent(TaskEvent.NewTaskSaveClick) },
                selectedDateListener = { viewModel.onEvent(TaskEvent.NewTaskDateSelectedSelected(it)) }
            )
        }


    }


}

@Composable
private fun AddTaskSection(
    isAddTaskEnable: Boolean,
    taskDescription: String,
    taskColor: Color,
    taskDescriptionChanged: (String) -> Unit,
    taskColorSelected: (Color) -> Unit,
    panelCloseListener: () -> Unit,
    saveTaskListener: () -> Unit,
    selectedDateListener: (List<TaskDate>) -> Unit
) {

    var monthNumber by remember { mutableStateOf(0) }
    val horizontalScrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }
    var isDatePickerVisible by remember { mutableStateOf(false) }

    val animateOffset by animateDpAsState(targetValue = offset.dp)
    Column(
        modifier = Modifier
            .offset(y = animateOffset)
            .fillMaxWidth()
            .shadow(25.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(LightGray, shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState {
                    if (offset + (it * 0.25f) > 0)
                        offset += (it * 0.25f)
                },
                onDragStopped = {
                    if (offset > 70) panelCloseListener()
                    else
                        offset = 0f
                }
            )
    ) {

        Box(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .width(100.dp)
                .height(2.dp)
                .background(Blue, shape = RoundedCornerShape(10.dp))
                .align(Alignment.CenterHorizontally)
        )

        AnimatedVisibility(visible = isDatePickerVisible) {
            DatePicker(
                myCalendar = MyCalendar(monthNumber),
                onNexMonthClickListener = { monthNumber++ },
                onPreviousMonthClickListener = { monthNumber-- },
                selectedDateListener = selectedDateListener
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(horizontalScrollState),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                isDatePickerVisible = !isDatePickerVisible
            }) {
                Icon(
                    imageVector = Icons.Default.EventAvailable,
                    contentDescription = "add date",
                    tint = DarkBlue
                )
            }
            // TODO: adding task reminder
//            IconButton(onClick = { /*TODO open time picker*/ }) {
//                Icon(
//                    imageVector = Icons.Default.Schedule,
//                    contentDescription = "add time",
//                    tint = DarkBlue
//                )
//            }

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

        Row(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = taskDescription,
                    onValueChange = { taskDescriptionChanged(it) },
                    label = {
                        Text(text = stringResource(id = R.string.task_description))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            IconButton(onClick = saveTaskListener, enabled = isAddTaskEnable) {
                Icon(
                    modifier = Modifier.alpha(if (isAddTaskEnable) 1f else 0.5f),
                    imageVector = Icons.Default.Send,
                    contentDescription = "add task",
                    tint = DarkBlue
                )
            }
        }
    }
}