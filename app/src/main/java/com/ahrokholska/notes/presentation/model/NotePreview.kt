package com.ahrokholska.notes.presentation.model

import androidx.compose.ui.graphics.Color
import com.ahrokholska.notes.domain.model.Note.Goals.Task
import com.ahrokholska.notes.domain.model.Note.RoutineTasks.SubNote

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
        val title: String,
        val tasks: List<Pair<Task, List<Task>>>,
        override val color: Color
    ) : NotePreview()

    data class Guidance(
        override val id: Int = 0,
        val title: String,
        val body: String,
        val image: String,
        override val color: Color
    ) : NotePreview()

    data class RoutineTasks(
        override val id: Int = 0,
        val active: List<SubNote>,
        val completed: List<SubNote>,
        override val color: Color
    ) : NotePreview()
}