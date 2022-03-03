package m.derakhshan.done.feature_home.domain.use_case

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_home.domain.repository.HomeRepository
import java.util.*

class GreetingsUseCase(
    private val repository: HomeRepository
) {

    operator fun invoke(): Map<String, Flow<String?>> {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val minute = Calendar.getInstance().get(Calendar.MINUTE)
        return mapOf(
            Pair(
                when {
                    hour == 0 || hour == 24 && minute == 0 -> "Good midnight, "
                    hour < 12 -> "Good morning, "
                    hour == 12 && minute == 0 -> "Good noon, "
                    hour < 18 -> "Good afternoon, "
                    else -> "Good evening, "
                }, repository.getUserName()
            )
        )
    }
}