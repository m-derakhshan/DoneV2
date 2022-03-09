package m.derakhshan.done.feature_task.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DayItem(
    text: String,
    selectedDayColor: Color,
    textColor: Color,
    isActive: Boolean,
    isToday: Boolean,
    isSelected: Boolean,
    onSelectListener: () -> Unit
) {

    Box(
        modifier = Modifier
            .alpha(if (isActive) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable(enabled = isActive, onClick = onSelectListener)
                .background(
                    if (isSelected) selectedDayColor else Color.Transparent,
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color = if (isToday) selectedDayColor else Color.Transparent,
                    shape = CircleShape
                )
        )

        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = if (isSelected) Color.White else textColor,
        )

    }
}