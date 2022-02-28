package m.derakhshan.done.feature_profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.core.data.data_source.DoneDatabase
import m.derakhshan.done.feature_profile.data.repository.ProfileRepositoryImpl
import m.derakhshan.done.feature_profile.domain.repository.ProfileRepository
import m.derakhshan.done.feature_profile.domain.use_case.GetUserInfoUseCase
import m.derakhshan.done.feature_profile.domain.use_case.ProfileUseCases
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileRepository(database: DoneDatabase): ProfileRepository {
        return ProfileRepositoryImpl(userDao = database.userDao)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getUserInfo = GetUserInfoUseCase(repository = repository)
        )
    }

}