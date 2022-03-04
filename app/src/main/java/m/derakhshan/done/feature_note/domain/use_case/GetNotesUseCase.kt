package m.derakhshan.done.feature_note.domain.use_case

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType
import m.derakhshan.done.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val repository: NoteRepository) {

    operator fun invoke(
        orderType: NoteOrderType,
        sortType: NoteOrderSortType
    ): Flow<List<NoteModel>> {
        return repository.getNotes(orderType = orderType, sortType = sortType)
    }
}