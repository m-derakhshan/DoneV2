package m.derakhshan.done.feature_task.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.core.data.data_source.DoneDatabase
import m.derakhshan.done.feature_task.data.repository.TaskRepositoryImpl
import m.derakhshan.done.feature_task.domain.repository.TaskRepository
import m.derakhshan.done.feature_task.domain.use_case.GetTasksUseCase
import m.derakhshan.done.feature_task.domain.use_case.InsertNewTask
import m.derakhshan.done.feature_task.domain.use_case.TaskUseCases
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    @Singleton
    fun provideTaskRepository(database: DoneDatabase): TaskRepository {
        return TaskRepositoryImpl(
            taskDao = database.taskDao
        )
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getTasksUseCase = GetTasksUseCase(repository = repository),
            insertNewTask = InsertNewTask(repository = repository)
        )
    }

}