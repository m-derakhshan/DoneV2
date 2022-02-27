package m.derakhshan.done.feature_authentication.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_authentication.domain.model.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserModel)

    @Query("DELETE FROM UserModel")
    suspend fun delete()

    @Query("SELECT * FROM UserModel Limit 1")
    fun getInfo(): Flow<UserModel>

    @Query("SELECT name FROM UserModel Limit 1")
    suspend fun getUserName(): String

}