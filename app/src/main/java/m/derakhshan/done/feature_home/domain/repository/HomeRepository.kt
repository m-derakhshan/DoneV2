package m.derakhshan.done.feature_home.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_home.domain.model.InspirationQuoteModel

interface HomeRepository {

    fun getInspirationQuote(): Flow<InspirationQuoteModel?>
    fun getUserName(): Flow<String?>
    suspend fun updateInspirationQuotes()
    fun userInfo(): Flow<UserModel?>

}