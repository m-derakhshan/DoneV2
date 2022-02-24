package m.derakhshan.done.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_authentication.domain.model.UserModel

@Database(
    entities = [UserModel::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}