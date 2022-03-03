package m.derakhshan.done.feature_profile.presentation.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.flow.collectLatest
import m.derakhshan.done.R
import m.derakhshan.done.core.presentation.composable.BackSwipeGesture
import m.derakhshan.done.core.presentation.composable.ImagePicker
import m.derakhshan.done.core.presentation.composable.LoadingButton
import m.derakhshan.done.feature_home.presentation.HomeNavGraph
import m.derakhshan.done.feature_profile.presentation.ProfileEvent
import m.derakhshan.done.feature_profile.presentation.ProfileViewModel
import m.derakhshan.done.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavController
) {

    var swipeOffset by remember { mutableStateOf(0f) }
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(true) {
        viewModel.snackBar.collectLatest { message ->
            if (message.isNotBlank()) {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                scaffoldState.snackbarHostState.showSnackbar(message)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.padding(paddingValues)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState(onDelta = { delta ->
                        swipeOffset += (delta * 0.2f)
                    }),
                    onDragStopped = {
                        if (swipeOffset > 90)
                            navController.navigateUp()
                        swipeOffset = 0f
                    }
                ),
            contentAlignment = Alignment.BottomCenter
        )
        {
            //--------------------(profile image and profile form)--------------------//
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //--------------------(profile image)--------------------//
                Box(
                    modifier = Modifier.height(200.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    TopBanner(userName = state.name)
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier.offset(y = 20.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = state.profileImage,
                                builder = { transformations(CircleCropTransformation()) }),
                            contentDescription = "profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(MaterialTheme.spacing.small)
                                .size(120.dp)
                                .border(2.dp, color = DarkBlue, shape = CircleShape)
                                .padding(2.dp)
                                .clickable {
                                    navController.navigate(
                                        HomeNavGraph.ImageScreen.route
                                                + "?uri=${state.profileImage}&title=${state.name}"
                                    )
                                }
                        )
                        IconButton(
                            onClick = { viewModel.onEvent(ProfileEvent.ImageSelectionOpen) },
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colors.background,
                                    shape = CircleShape
                                )
                                .border(2.dp, shape = CircleShape, color = DarkBlue)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = "add new photo"
                            )
                        }
                    }
                }

                //--------------------(profile form)--------------------//
                Column(
                    modifier = Modifier.padding(
                        vertical = MaterialTheme.spacing.extraLarge,
                        horizontal = MaterialTheme.spacing.small
                    )
                ) {

                    TextField(
                        value = state.name,
                        onValueChange = { viewModel.onEvent(ProfileEvent.OnNameChanged(it)) },
                        label = { Text(text = stringResource(id = R.string.name_family)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.small),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "person") }
                    )

                    TextField(
                        value = state.email,
                        onValueChange = {},
                        enabled = false,
                        label = { Text(text = stringResource(id = R.string.email)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.small),
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "email") }
                    )

                    TextField(
                        value = stringResource(id = R.string.reset_password),
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.small)
                            .clickable { viewModel.onEvent(ProfileEvent.OnPasswordChangeClicked) },
                        enabled = false,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "reset password"
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.RestartAlt,
                                contentDescription = "reset password"
                            )
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = MaterialTheme.spacing.large,
                                horizontal = MaterialTheme.spacing.small
                            ),
                        horizontalArrangement = Arrangement.Center
                    ) {


                        LoadingButton(
                            buttonText = stringResource(id = R.string.log_out),
                            isExpanded = state.isLogoutExpanded,
                            modifier =
                            if (state.isLogoutExpanded)
                                Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(0.35f)
                            else
                                Modifier.align(Alignment.CenterVertically),
                            backgroundColor = White
                        ) {
                            viewModel.onEvent(ProfileEvent.Logout)
                        }


                        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))

                        LoadingButton(
                            buttonText = stringResource(id = R.string.apply_changes),
                            isExpanded = state.isApplyChangesExpanded,
                            modifier =
                            if (state.isApplyChangesExpanded)
                                Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(0.65f)
                            else Modifier.align(Alignment.CenterVertically)
                        ) {
                            viewModel.onEvent(ProfileEvent.ApplyChanges)
                        }

                    }

                }

            }

            //--------------------(Image picker)--------------------//
            ImagePicker(
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.background,
                        shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
                    )
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .shadow(2.dp, RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)),
                openSelection = state.isImagePickerOpen,
                height = 550.dp,
                context = LocalContext.current,
                onCloseListener = { viewModel.onEvent(ProfileEvent.ImageSelectionClose) }) { uri ->
                viewModel.onEvent(ProfileEvent.ImageSelected(uri = uri))
            }
        }

        //--------------------(swipe back gesture)--------------------//
        BackSwipeGesture(
            offset = swipeOffset,
            arcColor = LightBlue
        )

    }

}


@Composable
private fun TopBanner(userName: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            drawRect(
                color = Blue,
                topLeft = Offset(0f, 0f),
                size = Size(width = size.width, height = 300f)
            )
            val path = Path().apply {
                moveTo(0.0f, 300f)
                lineTo(size.width / 2, size.height)
                lineTo(size.width, 300f)

            }
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    outline = Outline.Generic(path = path),
                    paint = Paint().apply {
                        color = Blue
                        pathEffect = PathEffect.cornerPathEffect(size.height)
                    }
                )
            }
        })

        Text(
            text = userName,
            style = MaterialTheme.typography.h5,
            color = White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.medium)
                .fillMaxWidth()
        )

    }

}