package m.derakhshan.done.core.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle

@Composable
fun DefaultRadioButton(
    selected: Boolean,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    onSelection: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = text, style = textStyle)
        RadioButton(
            selected = selected,
            onClick = onSelection
        )
    }
}