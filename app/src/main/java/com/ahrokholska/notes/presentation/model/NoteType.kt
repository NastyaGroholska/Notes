package com.ahrokholska.notes.presentation.model

import androidx.annotation.StringRes
import com.ahrokholska.notes.R

enum class NoteType(@StringRes val title: Int, @StringRes val description: Int) {
    InterestingIdea(R.string.interesting_idea, R.string.interesting_idea_descr),
    BuyingSomething(R.string.buying_something, R.string.buying_something_descr),
    Goals(R.string.goals, R.string.goals_descr),
    Guidance(R.string.guidance, R.string.guidance_descr),
    RoutineTasks(R.string.routine_tasks, R.string.routine_tasks_descr)
}