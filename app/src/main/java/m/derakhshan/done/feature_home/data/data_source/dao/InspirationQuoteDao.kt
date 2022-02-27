package m.derakhshan.done.feature_home.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_home.domain.model.InspirationQuoteModel

@Dao
interface InspirationQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: List<InspirationQuoteModel>)

    @Query("DELETE FROM InspirationQuoteModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM InspirationQuoteModel WHERE id=:id")
    fun getQuote(id: Int): Flow<InspirationQuoteModel?>
}