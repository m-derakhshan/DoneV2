package m.derakhshan.done.feature_note.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import m.derakhshan.done.R
import m.derakhshan.done.core.utils.plus
import m.derakhshan.done.ui.theme.spacing

@Composable
fun NoteScreen(navController: NavController, paddingValues: PaddingValues) {
    Scaffold(topBar = {
        TopAppBar {
            Text(
                text = stringResource(id = R.string.notes),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }) {
        Column(
            modifier = Modifier
                .padding(paddingValues + MaterialTheme.spacing.small)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TODO: implement note screen UI here

        }

    }
}