package com.ahrokholska.room.intermediate

import androidx.room.ColumnInfo

internal data class TaskAndSubtask(
    @ColumnInfo(name = "task_index") val taskIndex: Int,
    @ColumnInfo(name = "task_checked") val taskChecked: Boolean,
    @ColumnInfo(name = "task_text") val taskText: String,
    @ColumnInfo(name = "subtask_checked") val subtaskChecked: Boolean?,
    @ColumnInfo(name = "subtask_text") val subtaskText: String?,
)