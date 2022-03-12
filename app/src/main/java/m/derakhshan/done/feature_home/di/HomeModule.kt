package m.derakhshan.done.feature_home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.core.data.data_source.DoneDatabase
import m.derakhshan.done.core.utils.AppConstants
import m.derakhshan.done.feature_home.data.data_source.HomeApi
import m.derakhshan.done.feature_home.data.repository.HomeRepositoryImpl
import m.derakhshan.done.feature_home.domain.repository.HomeRepository
import m.derakhshan.done.feature_home.domain.use_case.*
import m.derakhshan.done.feature_task.domain.repository.TaskRepository
import m.derakhshan.done.feature_task.domain.use_case.DeleteTaskUseCase
import m.derakhshan.done.feature_task.domain.use_case.InsertNewTask
import m.derakhshan.done.feature_task.domain.use_case.UpdateTaskStatus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeApi(): HomeApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.QUOTE_BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeApi::class.java)
    }


    @Provides
    @Singleton
    fun provideHomeRepository(
        database: DoneDatabase,
        homeApi: HomeApi
    ): HomeRepository {
        return HomeRepositoryImpl(
            inspirationQuoteDao = database.inspirationQuoteDao,
            homeApi = homeApi,
            userDao = database.userDao,
            taskDao = database.taskDao
        )
    }


    @Provides
    @Singleton
    fun provideHomeUseCases(
        repository: HomeRepository,
        taskRepository: TaskRepository
    ): HomeUseCases {
        return HomeUseCases(
            getInsertInspirationQuoteUseCase = GetInspirationQuoteUseCase(repository = repository),
            updateInspirationQuotesUseCase = UpdateInspirationQuotesUseCase(repository = repository),
            greetingsUseCase = GreetingsUseCase(repository = repository),
            getTodayTask = GetTodayTaskUseCase(repository = repository),
            updateTaskStatus = UpdateTaskStatus(repository = taskRepository),
            deleteTask = DeleteTaskUseCase(repository = taskRepository),
            insertNewTask = InsertNewTask(repository = taskRepository)
        )
    }


}