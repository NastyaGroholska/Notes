package com.ahrokholska.note_details.presentation.noteTypes.buy

import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.NoteType
import com.ahrokholska.note_details.domain.useCase.ChangeBuySomethingItemCheckUseCase
import com.ahrokholska.note_details.domain.useCase.DeleteNoteUseCase
import com.ahrokholska.note_details.domain.useCase.FinishNoteUseCase
import com.ahrokholska.note_details.domain.useCase.PinNoteUseCase
import com.ahrokholska.note_details.domain.useCase.UnPinNoteUseCase
import com.ahrokholska.note_details.domain.useCase.getNoteDetails.GetBuySomethingNoteDetailsUseCase
import com.ahrokholska.note_details.presentation.NoteDetailsViewModel
import com.ahrokholska.presentation_domain_mapper.toPresentation
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = BuySomethingDetailsScreenViewModel.Factory::class)
internal class BuySomethingDetailsScreenViewModel @AssistedInject constructor(
    @Assisted id: Int,
    pinNoteUseCase: PinNoteUseCase,
    unPinNoteUseCase: UnPinNoteUseCase,
    getBuySomethingNoteDetailsUseCase: GetBuySomethingNoteDetailsUseCase,
    deleteNoteUseCase: DeleteNoteUseCase,
    finishNoteUseCase: FinishNoteUseCase,
    private val changeBuySomethingItemCheckUseCase: ChangeBuySomethingItemCheckUseCase
) : NoteDetailsViewModel(
    id, NoteType.BuyingSomething, pinNoteUseCase, unPinNoteUseCase, deleteNoteUseCase,
    finishNoteUseCase
) {
    val note = getBuySomethingNoteDetailsUseCase(id)
        .map { it?.toPresentation() }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun changeItemCheck(index: Int, checked: Boolean) {
        if (isDeleting) return
        viewModelScope.launch {
            changeBuySomethingItemCheckUseCase(id, index, checked)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): BuySomethingDetailsScreenViewModel
    }
}