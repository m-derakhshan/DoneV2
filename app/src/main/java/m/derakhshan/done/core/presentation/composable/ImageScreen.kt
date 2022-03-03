package m.derakhshan.done.core.presentation.composable


import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavController
import coil.compose.rememberImagePainter


@Composable
fun ImageScreen(
    uri: String,
    paddingValues: PaddingValues,
    navController: NavController,
    title: String
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(0f) }
    var imageOffset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        if (scale * zoomChange > 1f && scale * zoomChange < 3f)
            scale *= zoomChange
        imageOffset += offsetChange
    }

    Scaffold(
        topBar = {
            TopAppBar {
                Box(modifier = Modifier.fillMaxWidth()) {

                    IconButton(
                        onClick = { navController.navigateUp() }, modifier = Modifier.align(
                            Alignment.CenterStart
                        )
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }

                    Text(
                        text = title,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            }
        },
        modifier = Modifier
            .padding(paddingValues)
            .transformable(state)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState(onDelta = { delta ->
                    offset += (delta * 0.2f)
                }
                ), onDragStopped = {
                    if (offset > 90)
                        navController.navigateUp()
                    offset = 0f
                }
            )
    ) {

        Image(
            painter = rememberImagePainter(data = uri),
            contentDescription = title,
            modifier = Modifier.graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = imageOffset.x,
                translationY = imageOffset.y
            )
        )

        BackSwipeGesture(offset = offset)
    }

}