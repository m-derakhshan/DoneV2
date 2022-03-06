package m.derakhshan.done.feature_task.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.model.TaskStatus
import m.derakhshan.done.feature_task.domain.model.toDarkColor
import m.derakhshan.done.ui.theme.*

@Composable
fun TaskItem(task: TaskModel, onCheckChange: (Boolean) -> Unit) {

    Box(
        modifier = Modifier
            .alpha(if (task.status == TaskStatus.Done) 0.5f else 1f)
            .padding(MaterialTheme.spacing.small)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color(task.color), shape = RoundedCornerShape(10.dp))

    ) {

        Column(
            modifier = Modifier
                .offset(x = 5.dp)
                .background(LightGray, shape = RoundedCornerShape(10.dp))
                .padding(MaterialTheme.spacing.small)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.EventAvailable,
                        contentDescription = "calendar",
                        tint = task.toDarkColor()
                    )
                    Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
                    Text(text = task.date, style = MaterialTheme.typography.caption)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "clock",
                        tint = task.toDarkColor()
                    )
                    Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
                    Text(text = task.time, style = MaterialTheme.typography.caption)
                }
            }

            Box(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.extraSmall)
                    .alpha(0.25f)
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Gray)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.description,
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
                    .padding(vertical = MaterialTheme.spacing.extraSmall)
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
                        .size(10.dp)
                        .clip(shape = CircleShape)
                        .background(
                            color =
                            when (task.status) {
                                is TaskStatus.Done -> Green
                                is TaskStatus.InProgress -> Blue
                                is TaskStatus.Postpone -> Orange
                            }, shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
                Text(text = task.status.status, style = MaterialTheme.typography.caption)
            }
        }


    }
}