package com.ahrokholska.notes.data.mapper

import com.ahrokholska.notes.data.local.dao.GoalsNotesDao
import com.ahrokholska.notes.data.local.entities.BuySomethingNoteEntityWithItems
import com.ahrokholska.notes.data.local.entities.GoalsNoteEntity
import com.ahrokholska.notes.data.local.entities.GuidanceNoteEntity
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
import com.ahrokholska.notes.data.local.entities.RoutineTasksNoteEntityWithSubNotes
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.model.NotePreview

fun Note.toEntity() = when (this) {
    is Note.BuyingSomething -> TODO()
    is Note.Goals -> TODO()
    is Note.Guidance -> TODO()
    is Note.InterestingIdea -> toEntity()
    is Note.RoutineTasks -> TODO()
}

fun Note.InterestingIdea.toEntity() = InterestingIdeaNoteEntity(
    id = id,
    title = title,
    body = body
)

fun Note.Guidance.toEntity() = GuidanceNoteEntity(
    id = id,
    title = title,
    body = body,
    image = image
)

fun InterestingIdeaNoteEntity.toDomain(isFinished: Boolean, isPinned: Boolean) =
    Note.InterestingIdea(
        id = id,
        title = title,
        body = body,
        isFinished = isFinished,
        isPinned = isPinned
    )

fun InterestingIdeaNoteEntity.toDomainPreview() =
    NotePreview.InterestingIdea(
        id = id,
        title = title,
        body = body
    )

fun BuySomethingNoteEntityWithItems.toDomainPreview() = NotePreview.BuyingSomething(
    id = note.id,
    title = note.title,
    items = items.map { it.checked to it.text }
)

fun Map.Entry<GoalsNoteEntity, List<GoalsNotesDao.TaskAndSubtask>>.toDomainPreview(): NotePreview.Goals {
    val tasks = mutableListOf<Pair<Note.Goals.Task, List<Note.Goals.Task>>>()
    var currentTaskIndex = 0
    var currentTask = Note.Goals.Task(
        finished = value.first().taskChecked,
        text = value.first().taskText
    )
    var subtasks = mutableListOf<Note.Goals.Task>()
    value.forEach { pair ->
        if (pair.taskIndex != currentTaskIndex) {
            tasks.add(currentTask to subtasks)
            currentTaskIndex = pair.taskIndex
            currentTask = Note.Goals.Task(
                finished = pair.taskChecked,
                text = pair.taskText
            )
            subtasks = mutableListOf()
        }
        if (pair.subtaskChecked != null && pair.subtaskText != null)
            subtasks.add(
                Note.Goals.Task(
                    finished = pair.subtaskChecked,
                    text = pair.subtaskText
                )
            )
    }
    tasks.add(currentTask to subtasks)
    return NotePreview.Goals(
        id = key.id,
        title = key.title,
        tasks = tasks
    )
}

fun GuidanceNoteEntity.toDomainPreview() = NotePreview.Guidance(
    id = id,
    title = title,
    body = body,
    image = image
)

fun RoutineTasksNoteEntityWithSubNotes.toDomainPreview(): NotePreview.RoutineTasks {
    val active = mutableListOf<Note.RoutineTasks.SubNote>()
    val completed = mutableListOf<Note.RoutineTasks.SubNote>()
    subNotes.forEach {
        val item = Note.RoutineTasks.SubNote(
            title = it.title,
            text = it.text
        )
        if (it.completed) {
            completed.add(item)
        } else {
            active.add(item)
        }
    }
    return NotePreview.RoutineTasks(
        id = note.id,
        active = active,
        completed = completed
    )
}