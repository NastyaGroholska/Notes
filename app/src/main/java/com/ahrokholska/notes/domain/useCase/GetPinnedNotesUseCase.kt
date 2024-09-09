package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.notes.domain.model.Note
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetPinnedNotesUseCase @Inject constructor() {
    operator fun invoke() = flowOf(
        listOf(Note.Goals(), Note.Goals())
    )
}