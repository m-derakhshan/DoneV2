package m.derakhshan.done.feature_home.domain.use_case

import m.derakhshan.done.feature_home.domain.repository.HomeRepository
import java.util.*

class GreetingsUseCase(
    private val repository: HomeRepository
) {


    suspend operator fun invoke(): String {
        val name = repository.getUserName()
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val minute = Calendar.getInstance().get(Calendar.MINUTE)
        return "${
            when {
                hour == 0 || hour == 24 && minute == 0 -> "Good midnight,"
                hour < 12 -> "Good morning,"
                hour == 12 && minute == 0 -> "Good noon,"
                hour < 18 -> "Good afternoon,"
                else -> "Good evening,"
            }
        } $name"

    }
}