package m.derakhshan.done.core.presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import m.derakhshan.done.ui.theme.LightBlue
import m.derakhshan.done.ui.theme.White


@Composable
fun BackSwipeGesture(offset: Float, arcColor: Color = LightBlue) {

    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        val path = Path().apply {
            moveTo(0.0f, 0.0f)
            lineTo(offset, size.height / 2f)
            lineTo(0f, size.height)
        }
        drawIntoCanvas { canvas ->
            canvas.drawOutline(
                outline = Outline.Generic(path = path),
                paint = Paint().apply {
                    color = arcColor
                    pathEffect = PathEffect.cornerPathEffect(size.height / 2)
                }
            )
        }
    })

    //--------------------(arrow back for swipe gesture)--------------------//
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = "Back",
            tint = White,
            modifier = Modifier
                .offset(
                    x = (-50 + offset * 0.35).dp, 0.dp
                )
                .width(40.dp)
        )
    }
}