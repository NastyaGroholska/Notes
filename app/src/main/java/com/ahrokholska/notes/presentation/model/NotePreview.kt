package com.ahrokholska.notes.presentation.model

import androidx.compose.ui.graphics.Color

sealed class NotePreview {
    abstract val id: Int
    abstract val color: Color

    data class InterestingIdea(
        override val id: Int = 0,
        val title: String,
        val body: String,
        override val color: Color
    ) : NotePreview()

    data class BuyingSomething(
        override val id: Int = 0,
        val title: String,
        val items: List<Pair<Boolean, String>>,
        override val color: Color
    ) : NotePreview()

    data class Goals(
        override val id: Int = 0,
        override val color: Color
    ) : NotePreview()

    data class Guidance(
        override val id: Int = 0,
        override val color: Color
    ) : NotePreview()

    data class RoutineTasks(
        override val id: Int = 0,
        override val color: Color
    ) : NotePreview()
}