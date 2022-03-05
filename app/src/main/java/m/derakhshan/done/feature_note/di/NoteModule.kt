package m.derakhshan.done.feature_note.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.core.data.data_source.DoneDatabase
import m.derakhshan.done.feature_note.data.data_source.NoteSyncDao
import m.derakhshan.done.feature_note.data.repository.NoteRepositoryImpl
import m.derakhshan.done.feature_note.domain.repository.NoteRepository
import m.derakhshan.done.feature_note.domain.use_case.*
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            deleteNote = DeleteNoteUseCase(repository = repository),
            getNotes = GetNotesUseCase(repository = repository),
            restoreNote = RestoreNoteUseCase(repository = repository),
            syncNotes = SyncNotesUseCase(repository = repository),
            getNotesToSync = GetNotesToSyncUseCase(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun provideAddEditNoteUseCase(repository: NoteRepository): AddEditNoteUseCases {
        return AddEditNoteUseCases(
            insertNoteUseCase = InsertNoteUseCase(repository = repository),
            getNoteById = GetNoteById(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: DoneDatabase, storage: FirebaseFirestore): NoteRepository {
        return NoteRepositoryImpl(
            noteDao = database.noteDao,
            noteSyncDao = database.noteSyncDao,
            userDao = database.userDao,
            storage = storage
        )
    }

}