package com.ahrokholska.notes.presentation.mapper

import com.ahrokholska.notes.getEmptyBuyingSomethingNote
import com.ahrokholska.notes.getEmptyGoalsNote
import com.ahrokholska.notes.getEmptyGuidanceNote
import com.ahrokholska.notes.getEmptyInterestingIdeaNote
import com.ahrokholska.notes.getEmptyRoutineTasksNote
import com.ahrokholska.notes.presentation.model.NotePreview
import com.ahrokholska.notes.presentation.theme.noteColors
import org.junit.Assert.assertEquals
import org.junit.Test

class NotePreviewMapperTest {
    @Test
    fun `InterestingIdea domain is mapped to InterestingIdea presentation`() {
        val note = getEmptyInterestingIdeaNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.InterestingIdea::class, mappedNote::class)
    }

    @Test
    fun `BuyingSomething domain is mapped to BuyingSomething presentation`() {
        val note = getEmptyBuyingSomethingNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.BuyingSomething::class, mappedNote::class)
    }

    @Test
    fun `Goals domain is mapped to Goals presentation`() {
        val note = getEmptyGoalsNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.Goals::class, mappedNote::class)
    }

    @Test
    fun `Guidance domain is mapped to Guidance presentation`() {
        val note = getEmptyGuidanceNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.Guidance::class, mappedNote::class)
    }

    @Test
    fun `RoutineTasks domain is mapped to RoutineTasks presentation`() {
        val note = getEmptyRoutineTasksNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.RoutineTasks::class, mappedNote::class)
    }

    @Test
    fun `list of NotePreview domain after mapping to presentation has correct colors`() {
        val notes = listOf(
            getEmptyRoutineTasksNote(),
            getEmptyGuidanceNote(),
            getEmptyGoalsNote(),
            getEmptyBuyingSomethingNote(),
            getEmptyInterestingIdeaNote()
        )
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[index % noteColors.size], notePreview.color)
        }
    }

    @Test
    fun `list of InterestingIdea domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { getEmptyInterestingIdeaNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 1) % noteColors.size], notePreview.color)
        }
    }
}