package com.ahrokholska.room.mapper

import com.ahrokholska.api.model.Note
import com.ahrokholska.api.model.NotePreview
import com.ahrokholska.room.entities.GoalsNoteEntity
import com.ahrokholska.room.entities.GuidanceNoteEntity
import com.ahrokholska.room.entities.InterestingIdeaNoteEntity
import com.ahrokholska.room.entities.RoutineTasksNoteSubNoteEntity
import com.ahrokholska.room.intermediate.BuySomethingNoteDetails
import com.ahrokholska.room.intermediate.BuySomethingNoteEntityWithItems
import com.ahrokholska.room.intermediate.GoalsNoteDetails
import com.ahrokholska.room.intermediate.GuidanceNoteDetails
import com.ahrokholska.room.intermediate.InterestingIdeaNoteDetails
import com.ahrokholska.room.intermediate.RoutineTasksNoteDetails
import com.ahrokholska.room.intermediate.RoutineTasksNoteEntityWithSubNotes
import com.ahrokholska.room.intermediate.TaskAndSubtask

internal fun Note.InterestingIdea.toEntity() = InterestingIdeaNoteEntity(
    id = id,
    title = title,
    body = body
)

internal fun Note.Guidance.toEntity() = GuidanceNoteEntity(
    id = id,
    title = title,
    body = body,
    image = image
)

internal fun InterestingIdeaNoteEntity.toDomain(isFinished: Boolean, isPinned: Boolean) =
    Note.InterestingIdea(
        id = id,
        title = title,
        body = body,
        isFinished = isFinished,
        isPinned = isPinned
    )

internal fun InterestingIdeaNoteEntity.toDomainPreview() =
    NotePreview.InterestingIdea(
        id = id,
        title = title,
        body = body
    )

internal fun BuySomethingNoteEntityWithItems.toDomainPreview() = NotePreview.BuyingSomething(
    id = note.id,
    title = note.title,
    items = items.map { it.checked to it.text }
)

internal fun Map.Entry<GoalsNoteEntity, List<TaskAndSubtask>>.toDomainPreview(): NotePreview.Goals =
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

internal fun GuidanceNoteEntity.toDomainPreview() = NotePreview.Guidance(
    id = id,
    title = title,
    body = body,
    image = image
)

internal fun RoutineTasksNoteEntityWithSubNotes.toDomainPreview(): NotePreview.RoutineTasks {
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

internal fun InterestingIdeaNoteDetails.toNote() = Note.InterestingIdea(
    id = note.id,
    title = note.title,
    body = note.body,
    isFinished = isFinished,
    isPinned = isPinned
)

internal fun BuySomethingNoteDetails.toNote() = Note.BuyingSomething(
    id = noteWithItems.note.id,
    title = noteWithItems.note.title,
    items = noteWithItems.items.map { it.checked to it.text },
    isFinished = isFinished,
    isPinned = isPinned
)

internal fun GoalsNoteDetails.toNote(tasks: List<TaskAndSubtask>): Note.Goals =
    Note.Goals(
        id = note.id,
        title = note.title,
        tasks = tasks.toDomain(),
        isFinished = isFinished,
        isPinned = isPinned
    )

internal fun GuidanceNoteDetails.toNote(): Note.Guidance = Note.Guidance(
    id = note.id,
    title = note.title,
    body = note.body,
    image = note.image,
    isFinished = isFinished,
    isPinned = isPinned
)

internal fun RoutineTasksNoteDetails.toNote(): Note.RoutineTasks {
    val pair = note.subNotes.toActiveAndCompleted()
    return Note.RoutineTasks(
        id = note.note.id,
        active = pair.first,
        completed = pair.second,
        isFinished = isFinished,
        isPinned = isPinned
    )
}