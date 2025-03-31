package com.ahrokholska.notes.presentation.mapper

import com.ahrokholska.note_presentation.model.NotePreview
import com.ahrokholska.note_presentation.theme.noteColors
import com.ahrokholska.notes.getEmptyBuyingSomethingNote
import com.ahrokholska.notes.getEmptyGoalsNote
import com.ahrokholska.notes.getEmptyGuidanceNote
import com.ahrokholska.notes.getEmptyInterestingIdeaNote
import com.ahrokholska.notes.getEmptyRoutineTasksNote
import com.ahrokholska.presentation_domain_mapper.toUI
import org.junit.Assert.assertEquals
import org.junit.Test

class NotePreviewMapperTest {   //TODO move
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

    @Test
    fun `list of BuyingSomething domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { getEmptyBuyingSomethingNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 2) % noteColors.size], notePreview.color)
        }
    }

    @Test
    fun `list of Goals domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { getEmptyGoalsNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 3) % noteColors.size], notePreview.color)
        }
    }

    @Test
    fun `list of Guidance domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { getEmptyGuidanceNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 4) % noteColors.size], notePreview.color)
        }
    }

    @Test
    fun `list of RoutineTasks domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { getEmptyRoutineTasksNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 5) % noteColors.size], notePreview.color)
        }
    }
}