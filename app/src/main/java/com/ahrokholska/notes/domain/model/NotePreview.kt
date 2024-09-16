package com.ahrokholska.notes.domain.model

import com.ahrokholska.notes.domain.model.Note.Goals.Task

sealed class NotePreview {
    abstract val id: Int

    data class InterestingIdea(
        override val id: Int = 0,
        val title: String,
        val body: String,
    ) : NotePreview()

    data class BuyingSomething(
        override val id: Int = 0,
        val title: String,
        val items: List<Pair<Boolean, String>>
    ) : NotePreview()

    data class Goals(
        override val id: Int = 0,
        val title: String,
        val tasks: List<Pair<Task, List<Task>>>,
    ) : NotePreview()

    data class Guidance(
        override val id: Int = 0,
    ) : NotePreview()

    data class RoutineTasks(
        override val id: Int = 0,
    ) : NotePreview()
}