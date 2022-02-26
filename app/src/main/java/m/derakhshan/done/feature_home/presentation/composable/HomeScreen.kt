package m.derakhshan.done.feature_home.presentation.composable

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import m.derakhshan.done.feature_home.presentation.HomeEvent
import m.derakhshan.done.feature_home.presentation.HomeViewModel
import m.derakhshan.done.R
import m.derakhshan.done.ui.theme.spacing


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val focusRequest = remember { FocusRequester() }

    val state = viewModel.state.value
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.add_note_lottie))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = state.isAddNoteAnimationPlaying,
        speed = state.addNoteAnimationSpeed,
        restartOnPlay = false,
        clipSpec = LottieClipSpec.Frame(max = 45)
    )
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
                onClick = { viewModel.onEvent(HomeEvent.OnAddClicked) },
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
                        }
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