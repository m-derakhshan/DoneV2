package m.derakhshan.done.feature_home.domain.use_case

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_home.domain.model.InspirationQuoteModel
import m.derakhshan.done.feature_home.domain.repository.HomeRepository
import javax.inject.Inject

class GetInspirationQuoteUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    operator fun invoke(): Flow<InspirationQuoteModel?> {
        return repository.getInspirationQuote()
    }
}