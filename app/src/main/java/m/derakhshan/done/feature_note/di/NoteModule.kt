package m.derakhshan.done.feature_note.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.feature_note.domain.repository.NoteRepository
import m.derakhshan.done.feature_note.domain.use_case.DeleteNoteUseCase
import m.derakhshan.done.feature_note.domain.use_case.GetNotesUseCase
import m.derakhshan.done.feature_note.domain.use_case.NoteUseCases
import m.derakhshan.done.feature_note.domain.use_case.RestoreNoteUseCase
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

}