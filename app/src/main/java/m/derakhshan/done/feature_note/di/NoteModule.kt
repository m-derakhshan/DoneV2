package m.derakhshan.done.feature_note.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.core.data.data_source.DoneDatabase
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
            restoreNote = RestoreNoteUseCase(repository = repository)
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
    fun provideNoteRepository(database: DoneDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDao = database.noteDao)
    }

}