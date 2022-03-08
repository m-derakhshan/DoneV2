package m.derakhshan.done.feature_task.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import m.derakhshan.done.R
import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.model.TaskStatus
import m.derakhshan.done.ui.theme.*
import java.util.*


@Composable
fun TaskItem(
    task: TaskModel,
    modifier: Modifier = Modifier,
    onItemSwiped: () -> Unit,
    onCheckChange: (Boolean) -> Unit,
) {
    var changedOffset by remember { mutableStateOf(5f) }
    val foregroundOffset by animateDpAsState(targetValue = changedOffset.dp)
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.delete))


    Box(
        modifier = modifier
            .alpha(if (task.status == TaskStatus.Done) 0.5f else 1f)
            .padding(bottom = MaterialTheme.spacing.small)
            .fillMaxWidth()
            .background(Color(task.color))
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    val value = changedOffset + delta * 0.25f
                    if (value > 5 && value < 100)
                        changedOffset = value
                },
                onDragStopped = {
                    if (changedOffset > 55)
                        onItemSwiped()
                    changedOffset = 5f
                }
            )


    ) {

        LottieAnimation(
            composition,
            changedOffset/110f,
            modifier = Modifier
                .offset(x = 10.dp)
                .size(50.dp)
                .align(Alignment.CenterStart),
        )

        Column(
            modifier = Modifier
                .offset(x = foregroundOffset)
                .background(White)
                .padding(MaterialTheme.spacing.small)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.description.replaceFirstChar { it.titlecase(Locale.getDefault()) },
                    style = TextStyle(
                        textDecoration =
                        if (task.status == TaskStatus.Done) TextDecoration.LineThrough
                        else TextDecoration.None
                    ) + MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = task.status == TaskStatus.Done, onCheckedChange = onCheckChange)
            }
            Box(
                modifier = Modifier
                    .padding(bottom = MaterialTheme.spacing.extraSmall)
                    .alpha(0.25f)
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Gray)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .padding(end = MaterialTheme.spacing.extraSmall)
                        .size(10.dp)
                        .clip(shape = CircleShape)
                        .background(
                            color =
                            when (task.status) {
                                is TaskStatus.Done -> Green
                                is TaskStatus.InProgress -> Blue
                            }, shape = CircleShape
                        )
                )

                Text(
                    text = "${task.date}  ${task.time}",
                    style = MaterialTheme.typography.caption,
                    color = DarkGray,
                )


            }


        }
    }

}
//
//@Composable
//fun TaskItem(task: TaskModel, modifier: Modifier = Modifier, onCheckChange: (Boolean) -> Unit) {
//
//    Box(
//        modifier = modifier
//            .alpha(if (task.status == TaskStatus.Done) 0.5f else 1f)
//            .padding(MaterialTheme.spacing.small)
//            .fillMaxWidth()
//            .clip(shape = RoundedCornerShape(10.dp))
//            .background(Color(task.color), shape = RoundedCornerShape(10.dp))
//
//    ) {
//
//        Column(
//            modifier = Modifier
//                .offset(x = 5.dp)
//                .background(White, shape = RoundedCornerShape(10.dp))
//                .padding(MaterialTheme.spacing.small)
//        ) {
//
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Default.EventAvailable,
//                        contentDescription = "calendar",
//                        tint = task.toDarkColor()
//                    )
//                    Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
//                    Text(text = task.date, style = MaterialTheme.typography.caption)
//                }
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Default.Schedule,
//                        contentDescription = "clock",
//                        tint = task.toDarkColor()
//                    )
//                    Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
//                    Text(text = task.time, style = MaterialTheme.typography.caption)
//                }
//            }
//
//            Box(
//                modifier = Modifier
//                    .padding(vertical = MaterialTheme.spacing.extraSmall)
//                    .alpha(0.25f)
//                    .fillMaxWidth()
//                    .height(2.dp)
//                    .background(Gray)
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = task.description,
//                    style = TextStyle(
//                        textDecoration =
//                        if (task.status == TaskStatus.Done) TextDecoration.LineThrough
//                        else TextDecoration.None
//                    ) + MaterialTheme.typography.body1,
//                    modifier = Modifier.weight(1f)
//                )
//                Checkbox(checked = task.status == TaskStatus.Done, onCheckedChange = onCheckChange)
//            }
//
//            Box(
//                modifier = Modifier
//                    .padding(vertical = MaterialTheme.spacing.extraSmall)
//                    .alpha(0.25f)
//                    .fillMaxWidth()
//                    .height(2.dp)
//                    .background(Gray)
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(10.dp)
//                        .clip(shape = CircleShape)
//                        .background(
//                            color =
//                            when (task.status) {
//                                is TaskStatus.Done -> Green
//                                is TaskStatus.InProgress -> Blue
//                            }, shape = CircleShape
//                        )
//                )
//                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
//                Text(text = task.status.status, style = MaterialTheme.typography.caption)
//            }
//        }
//
//
//    }
//}