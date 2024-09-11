package com.ahrokholska.notes.domain.model

sealed class NotePreview {
    abstract val id: Int

    data class InterestingIdea(
        override val id: Int = 0,
        val title: String,
        val body: String,
    ) : NotePreview()

    data class BuyingSomething(
        override val id: Int = 0,
    ) : NotePreview()

    data class Goals(
        override val id: Int = 0,
    ) : NotePreview()

    data class Guidance(
        override val id: Int = 0,
    ) : NotePreview()

    data class RoutineTasks(
        override val id: Int = 0,
    ) : NotePreview()
}