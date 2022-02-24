package m.derakhshan.done.feature_authentication.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_authentication.domain.model.UserModel

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserModel)

    @Query("DELETE FROM UserModel")
    suspend fun delete()

    @Query("SELECT * FROM UserModel WHERE uid=:uid")
    fun getInfo(uid: String): Flow<UserModel>

}