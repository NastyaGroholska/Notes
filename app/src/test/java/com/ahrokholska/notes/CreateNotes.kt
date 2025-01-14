package com.ahrokholska.notes

import com.ahrokholska.notes.domain.model.NotePreview

fun getEmptyInterestingIdeaNote() = NotePreview.InterestingIdea(title = "", body = "")
fun getEmptyBuyingSomethingNote() = NotePreview.BuyingSomething(title = "", items = listOf())
fun getEmptyGoalsNote() = NotePreview.Goals(title = "", tasks = listOf())
fun getEmptyGuidanceNote() = NotePreview.Guidance(title = "", body = "", image = "")
fun getEmptyRoutineTasksNote() = NotePreview.RoutineTasks(active = listOf(), completed = listOf())