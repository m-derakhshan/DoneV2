package m.derakhshan.done.feature_home.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_home.domain.model.InspirationQuoteModel
import m.derakhshan.done.feature_task.domain.model.TaskModel

interface HomeRepository {

    fun getInspirationQuote(): Flow<InspirationQuoteModel?>
    fun getUserName(): Flow<String?>
    fun userInfo(): Flow<UserModel?>
    fun getTodayTasks(): Flow<List<TaskModel>?>
    suspend fun updateInspirationQuotes()

}