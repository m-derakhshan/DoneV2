package m.derakhshan.done.feature_home.presentation.composable


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.flow.collectLatest
import m.derakhshan.done.R
import m.derakhshan.done.feature_home.presentation.HomeEvent
import m.derakhshan.done.feature_home.presentation.HomeViewModel
import m.derakhshan.done.feature_task.presentation.composable.TaskItem
import m.derakhshan.done.ui.theme.*


@OptIn(ExperimentalUnitApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {

    val state = viewModel.state.value
    val offset by animateDpAsState(targetValue = state.taskListOffset.dp)
    val scroll = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_task))
    val lottieProgress by animateLottieCompositionAsState(
        lottieComposition,
        restartOnPlay = false,
        iterations = LottieConstants.IterateForever,
        clipSpec = LottieClipSpec.Progress(min = 0.1f)
    )

    LaunchedEffect(viewModel.snackBar) {
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        viewModel.snackBar.collectLatest { message ->
            if (message.isNotBlank()) {
                val undo =
                    scaffoldState.snackbarHostState.showSnackbar(message, actionLabel = "Undo")
                if (undo == SnackbarResult.ActionPerformed)
                    viewModel.onEvent(HomeEvent.TaskUndo)
            }

        }
    }

    Scaffold(modifier = Modifier.padding(paddingValues), scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                //--------------------(greetings)--------------------//
                Text(
                    buildAnnotatedString {
                        this.withStyle(
                            style = SpanStyle(
                                color = Blue, fontSize = TextUnit(
                                    20f,
                                    TextUnitType.Sp
                                )
                            )
                        ) {
                            append(state.greetings.keys.first())
                        }
                        this.withStyle(
                            style = SpanStyle(
                                color = Black, fontSize = TextUnit(
                                    18f,
                                    TextUnitType.Sp
                                ), fontWeight = FontWeight.Normal
                            )
                        ) {
                            append(state.greetings.values.first())
                        }
                    },
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.small)
                        .fillMaxWidth()
                )

                Box(contentAlignment = Alignment.BottomCenter) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = ((-state.taskListOffset + 400) * 0.1).dp)
                            .shadow(
                                2.dp,
                                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                            )
                            .background(
                                VeryLightBlue,
                                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                            )
                            .padding(MaterialTheme.spacing.small)
                            .verticalScroll(scroll),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //--------------------(analog clock)--------------------//
                        AnalogClock(
                            modifier = Modifier.size(150.dp),
                            clockBackgroundColor = LightBlue
                        )

                        //--------------------(inspiration quote)--------------------//
                        Box(
                            modifier = Modifier.padding(
                                top = MaterialTheme.spacing.medium,
                                start = MaterialTheme.spacing.medium,
                                end = MaterialTheme.spacing.medium,
                            )
                        ) {

                            Box(
                                modifier = Modifier
                                    .padding(vertical = MaterialTheme.spacing.medium)
                                    .matchParentSize()
                                    .border(
                                        3.dp,
                                        color = Blue,
                                        shape = RoundedCornerShape(
                                            topStart = 20.dp,
                                            bottomEnd = 20.dp
                                        )
                                    )
                            )


                            Box(
                                modifier = Modifier
                                    .padding(start = MaterialTheme.spacing.large)
                                    .width(80.dp)
                                    .height(20.dp)
                                    .background(MaterialTheme.colors.background)
                            )

                            Box(
                                modifier = Modifier
                                    .padding(end = MaterialTheme.spacing.large)
                                    .width(80.dp)
                                    .height(20.dp)
                                    .background(MaterialTheme.colors.background)
                                    .align(Alignment.BottomEnd)
                            )

                            Column(
                                modifier = Modifier
                                    .padding(MaterialTheme.spacing.medium)
                                    .align(Alignment.Center)
                            ) {

                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = TextUnit(
                                                value = 30f,
                                                type = TextUnitType.Sp
                                            )
                                        )
                                    ) { append("“ ") }
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = TextUnit(
                                                value = 22f,
                                                type = TextUnitType.Sp
                                            )
                                        )
                                    ) {
                                        append(state.inspirationQuote)
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = TextUnit(
                                                value = 30f,
                                                type = TextUnitType.Sp
                                            )
                                        )
                                    ) { append(" ”") }
                                }, modifier = Modifier.padding(top = MaterialTheme.spacing.medium))
                                Text(
                                    text = state.inspirationQuoteAuthor,
                                    style = MaterialTheme.typography.caption,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .padding(vertical = MaterialTheme.spacing.small)
                                        .fillMaxWidth()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(270.dp))
                    }


                    //--------------------(today task list)--------------------//
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = offset)
                            .background(
                                White,
                                shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
                            )
                            .shadow(
                                2.dp,
                                shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
                            )
                            .draggable(
                                orientation = Orientation.Vertical,
                                state = rememberDraggableState { offset ->
                                    if (state.taskListOffset + offset > 0 && state.taskListOffset + offset < 400)
                                        viewModel.onEvent(HomeEvent.TaskListSwiped(state.taskListOffset + offset * 0.35f))
                                },
                                onDragStopped = {
                                    if (offset > 250.dp)
                                        viewModel.onEvent(HomeEvent.TaskListSwiped(400f))
                                    else
                                        viewModel.onEvent(HomeEvent.TaskListSwiped(0f))
                                }
                            )
                            .padding(MaterialTheme.spacing.small),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 100.dp, height = 2.dp)
                                .background(DarkBlue)
                        )


                        // TODO: show or hide this section based on that today we have task to do or not.

                        if (state.todayTaskList.isNullOrEmpty())
                            Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
                                Text(
                                    text = stringResource(id = R.string.today_no_task),
                                    style = MaterialTheme.typography.h5,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                LottieAnimation(
                                    composition = lottieComposition,
                                    progress = lottieProgress,
                                    alignment = Alignment.TopCenter
                                )
                            }

                        LazyColumn(modifier = Modifier.padding(top = MaterialTheme.spacing.medium)) {
                            items(
                                items = (state.todayTaskList),
                                key = { it.id }
                            ) { item ->
                                TaskItem(
                                    task = item,
                                    modifier = Modifier.animateItemPlacement()
                                        .draggable(
                                            orientation = Orientation.Vertical,
                                            state = rememberDraggableState { offset ->
                                                if (state.taskListOffset + offset > 0 && state.taskListOffset + offset < 400)
                                                    viewModel.onEvent(HomeEvent.TaskListSwiped(state.taskListOffset + offset * 0.35f))
                                            },
                                            onDragStopped = {
                                                if (offset > 250.dp)
                                                    viewModel.onEvent(HomeEvent.TaskListSwiped(400f))
                                                else
                                                    viewModel.onEvent(HomeEvent.TaskListSwiped(0f))
                                            }
                                        ),
                                    onItemSwiped = {
                                        viewModel.onEvent(HomeEvent.TaskDeleteClicked(task = item))
                                    },
                                    onCheckChange = {
                                        viewModel.onEvent(
                                            HomeEvent.OnTaskCheckClicked(
                                                task = item,
                                                checked = it
                                            )
                                        )
                                    })
                            }
                        }
                    }
                }
            }
        }
    }

}