package com.ahrokholska.notes.data.mapper

import com.ahrokholska.notes.data.local.entities.GoalsNoteEntity
import com.ahrokholska.notes.data.local.entities.GuidanceNoteEntity
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
import com.ahrokholska.notes.data.local.entities.RoutineTasksNoteSubNoteEntity
import com.ahrokholska.notes.data.local.intermediate.BuySomethingNoteDetails
import com.ahrokholska.notes.data.local.intermediate.BuySomethingNoteEntityWithItems
import com.ahrokholska.notes.data.local.intermediate.GoalsNoteDetails
import com.ahrokholska.notes.data.local.intermediate.GuidanceNoteDetails
import com.ahrokholska.notes.data.local.intermediate.InterestingIdeaNoteDetails
import com.ahrokholska.notes.data.local.intermediate.RoutineTasksNoteDetails
import com.ahrokholska.notes.data.local.intermediate.RoutineTasksNoteEntityWithSubNotes
import com.ahrokholska.notes.data.local.intermediate.TaskAndSubtask
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

fun Map.Entry<GoalsNoteEntity, List<TaskAndSubtask>>.toDomainPreview(): NotePreview.Goals =
    NotePreview.Goals(
        id = key.id,
        title = key.title,
        tasks = value.toDomain()
    )

private fun List<TaskAndSubtask>.toDomain(): List<Pair<Note.Goals.Task, List<Note.Goals.Task>>> {
    if (isEmpty()) return listOf()
    val tasks = mutableListOf<Pair<Note.Goals.Task, List<Note.Goals.Task>>>()
    var currentTaskIndex = 0
    var currentTask = Note.Goals.Task(
        finished = first().taskChecked,
        text = first().taskText
    )
    var subtasks = mutableListOf<Note.Goals.Task>()
    forEach { pair ->
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
    return tasks
}

fun GuidanceNoteEntity.toDomainPreview() = NotePreview.Guidance(
    id = id,
    title = title,
    body = body,
    image = image
)

fun RoutineTasksNoteEntityWithSubNotes.toDomainPreview(): NotePreview.RoutineTasks {
    val pair = subNotes.toActiveAndCompleted()
    return NotePreview.RoutineTasks(
        id = note.id,
        active = pair.first,
        completed = pair.second
    )
}

private fun List<RoutineTasksNoteSubNoteEntity>.toActiveAndCompleted(): Pair<List<Note.RoutineTasks.SubNote>, List<Note.RoutineTasks.SubNote>> {
    val active = mutableListOf<Note.RoutineTasks.SubNote>()
    val completed = mutableListOf<Note.RoutineTasks.SubNote>()
    forEach {
        val item = Note.RoutineTasks.SubNote(
            id = it.itemIndex,
            title = it.title,
            text = it.text
        )
        if (it.completed) {
            completed.add(item)
        } else {
            active.add(item)
        }
    }
    return active to completed
}

fun InterestingIdeaNoteDetails.toNote() = Note.InterestingIdea(
    id = note.id,
    title = note.title,
    body = note.body,
    isFinished = isFinished,
    isPinned = isPinned
)

fun BuySomethingNoteDetails.toNote() = Note.BuyingSomething(
    id = noteWithItems.note.id,
    title = noteWithItems.note.title,
    items = noteWithItems.items.map { it.checked to it.text },
    isFinished = isFinished,
    isPinned = isPinned
)

fun GoalsNoteDetails.toNote(tasks: List<TaskAndSubtask>): Note.Goals =
    Note.Goals(
        id = note.id,
        title = note.title,
        tasks = tasks.toDomain(),
        isFinished = isFinished,
        isPinned = isPinned
    )

fun GuidanceNoteDetails.toNote(): Note.Guidance = Note.Guidance(
    id = note.id,
    title = note.title,
    body = note.body,
    image = note.image,
    isFinished = isFinished,
    isPinned = isPinned
)

fun RoutineTasksNoteDetails.toNote(): Note.RoutineTasks {
    val pair = note.subNotes.toActiveAndCompleted()
    return Note.RoutineTasks(
        id = note.note.id,
        active = pair.first,
        completed = pair.second,
        isFinished = isFinished,
        isPinned = isPinned
    )
}