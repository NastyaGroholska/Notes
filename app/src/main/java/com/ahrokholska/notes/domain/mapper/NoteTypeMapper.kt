package com.ahrokholska.notes.domain.mapper

import com.ahrokholska.api.model.NoteType

typealias NoteTypeUI = com.ahrokholska.notes.presentation.model.NoteType

fun NoteType.toUI() = when (this) {
    NoteType.InterestingIdea -> NoteTypeUI.InterestingIdea
    NoteType.BuyingSomething -> NoteTypeUI.BuyingSomething
    NoteType.Goals -> NoteTypeUI.Goals
    NoteType.Guidance -> NoteTypeUI.Guidance
    NoteType.RoutineTasks -> NoteTypeUI.RoutineTasks
}