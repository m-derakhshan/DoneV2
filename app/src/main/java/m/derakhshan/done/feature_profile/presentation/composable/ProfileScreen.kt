package m.derakhshan.done.feature_profile.presentation.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import m.derakhshan.done.R
import m.derakhshan.done.core.data.data_source.Setting
import m.derakhshan.done.feature_authentication.presentation.AuthenticationNavGraph
import m.derakhshan.done.feature_home.presentation.HomeNavGraph
import m.derakhshan.done.feature_profile.presentation.ProfileEvent
import m.derakhshan.done.feature_profile.presentation.ProfileViewModel
import m.derakhshan.done.ui.theme.Blue
import m.derakhshan.done.ui.theme.DarkBlue
import m.derakhshan.done.ui.theme.White
import m.derakhshan.done.ui.theme.spacing

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state = viewModel.state.value

    Scaffold {

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
                            data = "https://www.constrack.ng/uploads/icon-user-default.png",
                            builder = { transformations(CircleCropTransformation()) }),
                        contentDescription = "profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(MaterialTheme.spacing.small)
                            .size(120.dp)
                            .border(2.dp, color = DarkBlue, shape = CircleShape)
                            .padding(2.dp)
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
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
                        )
                ) {

                    Button(
                        onClick = { viewModel.onEvent(ProfileEvent.Logout) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = White)
                    ) {
                        Text(
                            text = stringResource(id = R.string.log_out),
                            modifier = Modifier.padding(MaterialTheme.spacing.small)
                        )
                    }

                    Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))

                    Button(
                        onClick = { viewModel.onEvent(ProfileEvent.ApplyChanges) },
                        modifier = Modifier.weight(0.75f),
                    ) {
                        Text(
                            text = stringResource(id = R.string.apply_changes),
                            modifier = Modifier.padding(MaterialTheme.spacing.small)
                        )
                    }
                }

            }


        }

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