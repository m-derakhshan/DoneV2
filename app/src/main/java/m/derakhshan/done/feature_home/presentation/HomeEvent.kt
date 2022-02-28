package m.derakhshan.done.feature_home.presentation

sealed class HomeEvent {
    data class TaskListSwiped(val offset: Float) : HomeEvent()
}