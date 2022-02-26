package m.derakhshan.done.feature_home.presentation

sealed class HomeEvent {
    object OnAddClicked : HomeEvent()
    object OnSaveNoteClicked : HomeEvent()
    object CloseNoteField : HomeEvent()
    data class OnNoteFieldChange(val text: String) : HomeEvent()
}