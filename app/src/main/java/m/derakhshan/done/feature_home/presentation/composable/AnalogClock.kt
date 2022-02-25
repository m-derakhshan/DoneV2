package m.derakhshan.done.feature_home.presentation.composable


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import kotlinx.coroutines.delay
import java.util.*


@Composable
fun AnalogClock(
    modifier: Modifier = Modifier,
    clockBackgroundColor: Color = MaterialTheme.colors.secondary,
    clockSmallDotBackgroundColor: Color = MaterialTheme.colors.onSecondary,
    secondHandColor: Color = MaterialTheme.colors.error,
    minuteHandColor: Color = MaterialTheme.colors.onSecondary
) {
    var second by remember { mutableStateOf(0) }
    var minute by remember { mutableStateOf(0) }
    var hour by remember { mutableStateOf(0) }

    LaunchedEffect(true) {
        while (true) {
            Calendar.getInstance().let {
                second = it.get(Calendar.SECOND)
                minute = it.get(Calendar.MINUTE)
                hour = it.get(Calendar.HOUR_OF_DAY)
            }
            delay(1000)
        }
    }




    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            val width = size.width
            val height = size.height

            //--------------------(big circle)--------------------//
            drawCircle(color = clockBackgroundColor, radius = (size.minDimension / 2) - 45)

            //--------------------(around small lines)--------------------//
            for (i in 0..59)
                rotate(degrees = i * 6f, pivot = Offset(x = width / 2, y = height / 2)) {
                    drawRect(
                        color = Color(ColorUtils.blendARGB(clockBackgroundColor.toArgb(), 0, 0.5f)),
                        topLeft = Offset(x = width / 2 - 2.5f, y = 0f),
                        size = Size(width = 5f, height = 35f),
                    )
                }

            //--------------------(second hand)--------------------//
            rotate(second * 6f, Offset(width / 2, height / 2)) {
                drawRoundRect(
                    color = secondHandColor,
                    topLeft = Offset(x = width / 2, y = 45f),
                    size = Size(width = 5f, height = (height / 2f) - 45)
                )
            }
            //--------------------(minute hand)--------------------//
            rotate(minute * 6f, Offset(width / 2, height / 2)) {
                drawRoundRect(
                    color = minuteHandColor,
                    topLeft = Offset(x = width / 2, y = 70f),
                    size = Size(width = 5f, height = (height / 2f) - 70)
                )
            }
            //--------------------(hour hand)--------------------//
            rotate(
                degrees = hour % 12 * 30f + minute * 0.5f,
                pivot = Offset(width / 2, height / 2)
            ) {
                drawRoundRect(
                    color = minuteHandColor,
                    topLeft = Offset(x = width / 2, y = 120f),
                    size = Size(width = 5f, height = (height / 2f) - 120)
                )
            }

            //--------------------(middle small circle)--------------------//
            drawCircle(color = clockSmallDotBackgroundColor, radius = size.minDimension / 25)

        })
    }

}