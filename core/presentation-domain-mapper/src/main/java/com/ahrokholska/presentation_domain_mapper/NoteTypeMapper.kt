package com.ahrokholska.presentation_domain_mapper

import com.ahrokholska.api.model.NoteType

typealias NoteTypeUI = com.ahrokholska.note_presentation.model.NoteType

fun NoteType.toUI() = when (this) {
    NoteType.InterestingIdea -> NoteTypeUI.InterestingIdea
    NoteType.BuyingSomething -> NoteTypeUI.BuyingSomething
    NoteType.Goals -> NoteTypeUI.Goals
    NoteType.Guidance -> NoteTypeUI.Guidance
    NoteType.RoutineTasks -> NoteTypeUI.RoutineTasks
}