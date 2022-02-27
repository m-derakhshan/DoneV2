package m.derakhshan.done.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_home.data.data_source.dao.InspirationQuoteDao
import m.derakhshan.done.feature_home.domain.model.InspirationQuoteModel

@Database(
    entities = [UserModel::class, InspirationQuoteModel::class],
    version = 1,
    exportSchema = false
)
abstract class DoneDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val inspirationQuoteDao: InspirationQuoteDao
}