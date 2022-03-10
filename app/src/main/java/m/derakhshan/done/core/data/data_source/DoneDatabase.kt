package m.derakhshan.done.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_home.data.data_source.dao.InspirationQuoteDao
import m.derakhshan.done.feature_home.domain.model.InspirationQuoteModel
import m.derakhshan.done.feature_note.data.data_source.NoteDao
import m.derakhshan.done.feature_note.data.data_source.NoteSyncDao
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.model.NoteSyncActionConverter
import m.derakhshan.done.feature_note.domain.model.NoteSyncModel
import m.derakhshan.done.feature_task.data.data_source.TaskDao
import m.derakhshan.done.feature_task.data.data_source.TaskSyncDao
import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.model.TaskStatusConverter
import m.derakhshan.done.feature_task.domain.model.TaskSyncActionConverter
import m.derakhshan.done.feature_task.domain.model.TaskSyncModel

@Database(
    entities = [
        UserModel::class, InspirationQuoteModel::class,
        NoteModel::class, NoteSyncModel::class, TaskModel::class, TaskSyncModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    NoteSyncActionConverter::class,
    TaskStatusConverter::class,
    TaskSyncActionConverter::class
)
abstract class DoneDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val inspirationQuoteDao: InspirationQuoteDao
    abstract val noteDao: NoteDao
    abstract val noteSyncDao: NoteSyncDao
    abstract val taskDao: TaskDao
    abstract val taskSyncDao: TaskSyncDao
}

