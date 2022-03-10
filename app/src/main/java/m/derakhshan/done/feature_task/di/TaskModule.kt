package m.derakhshan.done.feature_task.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.core.data.data_source.DoneDatabase
import m.derakhshan.done.feature_task.data.repository.TaskRepositoryImpl
import m.derakhshan.done.feature_task.domain.repository.TaskRepository
import m.derakhshan.done.feature_task.domain.use_case.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    @Singleton
    fun provideTaskRepository(database: DoneDatabase, storage: FirebaseFirestore): TaskRepository {
        return TaskRepositoryImpl(
            taskDao = database.taskDao,
            taskSyncDao = database.taskSyncDao,
            userDao = database.userDao,
            storage = storage
        )
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getTasks = GetTasksUseCase(repository = repository),
            insertNewTask = InsertNewTask(repository = repository),
            updateTaskStatus = UpdateTaskStatus(repository = repository),
            deleteTask = DeleteTaskUseCase(repository = repository),
            syncTasks = SyncTasksUseCase(repository = repository),
            getTasksToSync = GetTasksToSyncUseCase(repository = repository)
        )
    }

}