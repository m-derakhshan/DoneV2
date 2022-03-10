package m.derakhshan.done.feature_task.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_task.domain.model.TaskModel

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tasks: List<TaskModel>)

    @Delete
    suspend fun delete(task: TaskModel)

    @Query("SELECT * FROM TaskModel order by status DESC,date DESC, time DESC")
    fun getTasks(): Flow<List<TaskModel>?>

    @Query("DELETE FROM TaskModel")
    suspend fun deleteAll()

}