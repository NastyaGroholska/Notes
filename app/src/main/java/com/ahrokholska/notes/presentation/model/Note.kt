package com.ahrokholska.notes.presentation.model

import androidx.compose.ui.graphics.Color
import com.ahrokholska.notes.domain.model.Note.Goals.Task

sealed class Note {
    abstract val id: Int
    abstract val isFinished: Boolean
    abstract val isPinned: Boolean
    abstract val color: Color

    data class InterestingIdea(
        override val id: Int = 0,
        val title: String,
        val body: String,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false,
        override val color: Color
    ) : Note()

    data class BuyingSomething(
        override val id: Int = 0,
        val title: String,
        val items: List<Pair<Boolean, String>>,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false,
        override val color: Color
    ) : Note()

    data class Goals(
        override val id: Int = 0,
        val title: String,
        val tasks: List<Pair<Task, List<Task>>>,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false,
        override val color: Color
    ) : Note()

    data class Guidance(
        override val id: Int = 0,
        val title: String,
        val body: String,
        val image: String,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false,
        override val color: Color
    ) : Note()

    data class RoutineTasks(
        override val id: Int = 0,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false,
        override val color: Color
    ) : Note()
}