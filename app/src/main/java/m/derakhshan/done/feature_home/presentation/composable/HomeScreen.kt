package m.derakhshan.done.feature_home.presentation.composable

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import m.derakhshan.done.R
import m.derakhshan.done.feature_home.presentation.HomeEvent
import m.derakhshan.done.feature_home.presentation.HomeViewModel
import m.derakhshan.done.ui.theme.LightGray
import m.derakhshan.done.ui.theme.VeryLightBlue
import m.derakhshan.done.ui.theme.White
import m.derakhshan.done.ui.theme.spacing


@OptIn(ExperimentalUnitApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    var taskHeight by remember {
        mutableStateOf(150f)
    }
    val focusRequest = remember { FocusRequester() }
    var enableBackHandler by remember { mutableStateOf(false) }
    val state = viewModel.state.value
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.add_note_lottie))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = state.isAddNoteAnimationPlaying,
        speed = state.addNoteAnimationSpeed,
        restartOnPlay = false,
        clipSpec = LottieClipSpec.Frame(max = 45)
    )
    BackHandler(enabled = enableBackHandler) {
        if (state.isNoteFieldVisible)
            viewModel.onEvent(HomeEvent.CloseNoteField).also {
                enableBackHandler = false
            }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(paddingValues)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //--------------------(greetings)--------------------//
            Text(
                text = state.greetings,
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
                        .shadow(2.dp, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                        .background(
                            VeryLightBlue,
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                        .padding(MaterialTheme.spacing.small),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //--------------------(analog clock)--------------------//
                    AnalogClock(modifier = Modifier.size(150.dp))

                    //--------------------(add new note)--------------------//
                    Button(
                        onClick = {
                            viewModel.onEvent(HomeEvent.OnAddClicked)
                            enableBackHandler = true
                        },
                        modifier = Modifier
                            .padding(MaterialTheme.spacing.small)
                            .size(50.dp)
                            .align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add note",
                        )
                    }

                    //--------------------(inspiration quote)--------------------//
                    Box(
                        modifier = Modifier.padding(MaterialTheme.spacing.medium)
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(vertical = MaterialTheme.spacing.medium)
                                .matchParentSize()
                                .border(
                                    3.dp,
                                    color = MaterialTheme.colors.onBackground,
                                    shape = RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp)
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

                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = taskHeight.dp)
                        .background(LightGray)
                        .draggable(
                            orientation = Orientation.Vertical,
                            state = rememberDraggableState { offset ->
                                // TODO: complete the today task list
                                if (taskHeight + offset > 0)
                                    taskHeight += offset * 0.35f
                                if (taskHeight > 450)
                                    taskHeight = 450f
                            }
                        )
                ) {

                }
            }


        }


        AnimatedVisibility(
            visible = state.isNoteFieldVisible,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
        ) {

            LaunchedEffect(true) {
                focusRequest.requestFocus()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp)
                    .background(White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedTextField(
                        value = state.noteFieldText,
                        onValueChange = { viewModel.onEvent(HomeEvent.OnNoteFieldChange(it)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .focusRequester(focusRequest),
                        label = {
                            Text(text = stringResource(id = R.string.write_something))
                        })
                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier
                            .clickable {
                                if (progress > 0.7f)
                                    viewModel.onEvent(HomeEvent.OnSaveNoteClicked)
                            }
                            .size(50.dp)
                    )
                }
            }

        }
    }
}