package com.ahrokholska.api.model

import com.ahrokholska.api.model.Note.Goals.Task
import com.ahrokholska.api.model.Note.RoutineTasks.SubNote

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
        val title: String,
        val body: String,
        val image: String,
    ) : NotePreview()

    data class RoutineTasks(
        override val id: Int = 0,
        val active: List<SubNote>,
        val completed: List<SubNote>,
    ) : NotePreview()
}