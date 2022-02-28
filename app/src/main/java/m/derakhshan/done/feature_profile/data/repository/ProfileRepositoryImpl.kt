package m.derakhshan.done.feature_profile.data.repository

import kotlinx.coroutines.flow.Flow

import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val userDao: UserDao) : ProfileRepository {
    override suspend fun getUserInfo(): Flow<UserModel> {
        return userDao.getInfo()
    }
}