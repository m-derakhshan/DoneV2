package m.derakhshan.done.feature_home.presentation

data class HomeState(
    val inspirationQuoteAuthor: String = "Unknown",
    val inspirationQuote: String = "Try your best!",
    val greetings: Map<String, String> = mapOf(Pair("", "")),
    val taskListOffset: Float = 400f
)