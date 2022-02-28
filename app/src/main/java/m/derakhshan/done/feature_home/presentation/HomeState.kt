package m.derakhshan.done.feature_home.presentation

data class HomeState(
    val inspirationQuoteAuthor: String = "Unknown",
    val inspirationQuote: String = "Try your best!",
    val greetings: String = "",
    val taskListOffset: Float = 400f
)