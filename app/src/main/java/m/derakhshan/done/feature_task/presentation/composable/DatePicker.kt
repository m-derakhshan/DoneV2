package m.derakhshan.done.feature_task.presentation.composable

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import m.derakhshan.done.feature_task.domain.model.MyCalendar
import m.derakhshan.done.feature_task.domain.model.TaskDate


private val weekDayList = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
private val weekend = listOf("Sat")

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun DatePicker(
    titleBackground: Color = MaterialTheme.colors.primary,
    myCalendar: MyCalendar,
    onNexMonthClickListener: () -> Unit,
    onPreviousMonthClickListener: () -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(titleBackground),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPreviousMonthClickListener) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "previous month",
                    tint = MaterialTheme.colors.onPrimary

                )
            }

            AnimatedContent(
                targetState = myCalendar.month,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(initialOffsetX = { it }) + fadeIn() with
                                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
                    } else {
                        slideInHorizontally(initialOffsetX = { -it }) + fadeIn() with
                                slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }
            ) { month ->
                Text(
                    text = "${myCalendar.year}  ${myCalendar.getMonthName(month)}",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp),
                    color = MaterialTheme.colors.onPrimary
                )
            }


            IconButton(onClick = onNexMonthClickListener) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "next month",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        LazyVerticalGrid(cells = GridCells.Fixed(7)) {
            items(weekDayList) { day ->
                Text(
                    text = day,
                    textAlign = TextAlign.Center,
                    color = if (day in weekend) Color.Red else Color.Black
                )
            }
        }

        DaysList(days = myCalendar.generateDays())

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DaysList(days: List<TaskDate>) {
    val selectedList = remember { mutableStateListOf(TaskDate.today) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(7),
            contentPadding = PaddingValues(top = 8.dp, start = 4.dp, end = 4.dp)
        ) {
            items(days) { day ->

                DayItem(
                    text = day.day.toString(),
                    textColor = Color.Black,
                    selectedDayColor = Color.Blue,
                    isSelected = day in selectedList,
                    isActive = day.day != -1,
                    isToday = day == TaskDate.today
                ) {

                    if (day in selectedList)
                        selectedList.remove(day)
                    else
                        selectedList.add(day)

                    if (selectedList.isEmpty())
                        selectedList.add(TaskDate.today)

                }

                Spacer(modifier = Modifier.size(40.dp))

            }
        }
    }

}





