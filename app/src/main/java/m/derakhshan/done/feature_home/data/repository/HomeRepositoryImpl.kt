package m.derakhshan.done.feature_home.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_home.data.data_source.HomeApi
import m.derakhshan.done.feature_home.data.data_source.dao.InspirationQuoteDao
import m.derakhshan.done.feature_home.data.data_source.dto.toInspirationQuoteModel
import m.derakhshan.done.feature_home.domain.model.InspirationQuoteModel
import m.derakhshan.done.feature_home.domain.repository.HomeRepository
import java.util.concurrent.CancellationException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val inspirationQuoteDao: InspirationQuoteDao,
    private val homeApi: HomeApi,
    private val userDao: UserDao,
) : HomeRepository {

    override fun getInspirationQuote(): Flow<InspirationQuoteModel?> {
        return inspirationQuoteDao.getQuote()
    }

    override fun getUserName(): Flow<String?> {
        return userDao.getUserName()
    }

    override suspend fun updateInspirationQuotes() {
        try {
            val quote = homeApi.getTodayQuote().first()
            inspirationQuoteDao.insert(quote.toInspirationQuoteModel())
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.i("Log", "HomeRepository_updateQuotesError: ${e.message}")
        }
    }

    override fun userInfo(): Flow<UserModel?> {
        return userDao.getInfo()
    }
}