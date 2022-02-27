package m.derakhshan.done.feature_home.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_home.domain.model.InspirationQuoteModel

interface HomeRepository {

    fun getInspirationQuote(): Flow<InspirationQuoteModel?>
    suspend fun getUserName(): String
    suspend fun updateInspirationQuotes()

}