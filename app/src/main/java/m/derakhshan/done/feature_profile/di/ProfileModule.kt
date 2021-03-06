package m.derakhshan.done.feature_profile.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.core.data.data_source.DoneDatabase
import m.derakhshan.done.core.data.data_source.Setting
import m.derakhshan.done.feature_profile.data.repository.ProfileRepositoryImpl
import m.derakhshan.done.feature_profile.domain.repository.ProfileRepository
import m.derakhshan.done.feature_profile.domain.use_case.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileRepository(
        database: DoneDatabase,
        setting: Setting,
        authentication: FirebaseAuth,
        fireStore: FirebaseFirestore
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            userDao = database.userDao,
            noteDao = database.noteDao,
            taskDao = database.taskDao,
            noteSyncDao = database.noteSyncDao,
            taskSyncDao = database.taskSyncDao,
            setting = setting,
            authentication = authentication,
            fireStore = fireStore
        )
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getUserInfo = GetUserInfoUseCase(repository = repository),
            logOutUser = LogoutUseCase(repository = repository),
            updateUserInfo = UpdateUserInfoUseCase(repository = repository),
            resetPassword = ResetPasswordUseCase(repository = repository)
        )
    }

}