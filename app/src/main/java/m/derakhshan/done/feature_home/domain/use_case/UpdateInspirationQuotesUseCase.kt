package m.derakhshan.done.feature_home.domain.use_case

import m.derakhshan.done.feature_home.domain.repository.HomeRepository
import javax.inject.Inject

class UpdateInspirationQuotesUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    suspend operator fun invoke() {
        repository.updateInspirationQuotes()
    }
}