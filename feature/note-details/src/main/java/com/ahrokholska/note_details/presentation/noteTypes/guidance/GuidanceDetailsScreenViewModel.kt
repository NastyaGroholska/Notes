package com.ahrokholska.note_details.presentation.noteTypes.guidance

import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.NoteType
import com.ahrokholska.note_details.domain.useCase.DeleteNoteUseCase
import com.ahrokholska.note_details.domain.useCase.FinishNoteUseCase
import com.ahrokholska.note_details.domain.useCase.PinNoteUseCase
import com.ahrokholska.note_details.domain.useCase.UnPinNoteUseCase
import com.ahrokholska.note_details.domain.useCase.getNoteDetails.GetGuidanceNoteDetailsUseCase
import com.ahrokholska.note_details.presentation.NoteDetailsViewModel
import com.ahrokholska.presentation_domain_mapper.toPresentation
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = GuidanceDetailsScreenViewModel.Factory::class)
internal class GuidanceDetailsScreenViewModel @AssistedInject constructor(
    @Assisted id: Int,
    pinNoteUseCase: PinNoteUseCase,
    unPinNoteUseCase: UnPinNoteUseCase,
    getGuidanceNoteDetailsUseCase: GetGuidanceNoteDetailsUseCase,
    deleteNoteUseCase: DeleteNoteUseCase,
    finishNoteUseCase: FinishNoteUseCase,
) : NoteDetailsViewModel(
    id, NoteType.Guidance, pinNoteUseCase, unPinNoteUseCase, deleteNoteUseCase, finishNoteUseCase
) {
    val note = getGuidanceNoteDetailsUseCase(id)
        .map { it?.toPresentation() }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    @AssistedFactory
    interface Factory {
        fun create(id: Int): GuidanceDetailsScreenViewModel
    }
}