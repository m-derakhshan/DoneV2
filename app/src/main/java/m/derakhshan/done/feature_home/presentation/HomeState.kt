package m.derakhshan.done.feature_home.presentation

data class HomeState(
    val isNoteFieldVisible: Boolean = false,
    val inspirationQuoteAuthor: String = "Unknown",
    val inspirationQuote: String = "Try your best!",
    val noteFieldText: String = "",
    val isAddNoteAnimationPlaying: Boolean = false,
    val addNoteAnimationSpeed: Float = 1f
)