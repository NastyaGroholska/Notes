package com.ahrokholska.api.model

sealed class Note {
    abstract val id: Int
    abstract val isFinished: Boolean
    abstract val isPinned: Boolean

    data class InterestingIdea(
        override val id: Int = 0,
        val title: String,
        val body: String,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false
    ) : Note()

    data class BuyingSomething(
        override val id: Int = 0,
        val title: String,
        val items: List<Pair<Boolean, String>>,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false
    ) : Note()

    data class Goals(
        override val id: Int = 0,
        val title: String,
        val tasks: List<Pair<Task, List<Task>>>,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false
    ) : Note() {
        data class Task(val finished: Boolean, val text: String)
    }

    data class Guidance(
        override val id: Int = 0,
        val title: String,
        val body: String,
        val image: String,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false
    ) : Note()

    data class RoutineTasks(
        override val id: Int = 0,
        val active: List<SubNote>,
        val completed: List<SubNote>,
        override val isFinished: Boolean = false,
        override val isPinned: Boolean = false
    ) : Note() {
        data class SubNote(val id: Int = 0, val title: String, val text: String)
    }
}