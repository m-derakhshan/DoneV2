package m.derakhshan.done.feature_home.domain.use_case

data class HomeUseCases(
    val getInsertInspirationQuoteUseCase: GetInspirationQuoteUseCase,
    val updateInspirationQuotesUseCase: UpdateInspirationQuotesUseCase,
    val greetingsUseCase: GreetingsUseCase
)
