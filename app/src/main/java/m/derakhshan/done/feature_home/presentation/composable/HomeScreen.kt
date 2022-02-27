package m.derakhshan.done.feature_home.presentation.composable

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import m.derakhshan.done.ui.theme.spacing


@OptIn(ExperimentalUnitApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val focusRequest = remember { FocusRequester() }
    var enableBackHandler by remember {
        mutableStateOf(false)
    }
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
            .padding(paddingValues)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnalogClock(modifier = Modifier.size(150.dp))

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
                    .shadow(2.dp)
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
                        },
                        keyboardOptions = KeyboardOptions(autoCorrect = false)
                    )
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