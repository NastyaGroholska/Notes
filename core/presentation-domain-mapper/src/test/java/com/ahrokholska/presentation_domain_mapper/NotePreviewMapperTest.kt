package com.ahrokholska.presentation_domain_mapper

import com.ahrokholska.note_presentation.model.NotePreview
import com.ahrokholska.note_presentation.theme.noteColors
import org.junit.Assert.assertEquals
import org.junit.Test

internal class NotePreviewMapperTest {
    @Test
    fun `InterestingIdea domain is mapped to InterestingIdea presentation`() {
        val note = CreateNotes.getEmptyInterestingIdeaNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.InterestingIdea::class, mappedNote::class)
    }

    @Test
    fun `BuyingSomething domain is mapped to BuyingSomething presentation`() {
        val note = CreateNotes.getEmptyBuyingSomethingNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.BuyingSomething::class, mappedNote::class)
    }

    @Test
    fun `Goals domain is mapped to Goals presentation`() {
        val note = CreateNotes.getEmptyGoalsNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.Goals::class, mappedNote::class)
    }

    @Test
    fun `Guidance domain is mapped to Guidance presentation`() {
        val note = CreateNotes.getEmptyGuidanceNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.Guidance::class, mappedNote::class)
    }

    @Test
    fun `RoutineTasks domain is mapped to RoutineTasks presentation`() {
        val note = CreateNotes.getEmptyRoutineTasksNote()
        val mappedNote = note.toUI(0)
        assertEquals(NotePreview.RoutineTasks::class, mappedNote::class)
    }

    @Test
    fun `list of NotePreview domain after mapping to presentation has correct colors`() {
        val notes = with(CreateNotes) {
            listOf(
                getEmptyRoutineTasksNote(),
                getEmptyGuidanceNote(),
                getEmptyGoalsNote(),
                getEmptyBuyingSomethingNote(),
                getEmptyInterestingIdeaNote()
            )
        }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[index % noteColors.size], notePreview.color)
        }
    }

    @Test
    fun `list of InterestingIdea domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { CreateNotes.getEmptyInterestingIdeaNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 1) % noteColors.size], notePreview.color)
        }
    }

    @Test
    fun `list of BuyingSomething domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { CreateNotes.getEmptyBuyingSomethingNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 2) % noteColors.size], notePreview.color)
        }
    }

    @Test
    fun `list of Goals domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { CreateNotes.getEmptyGoalsNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 3) % noteColors.size], notePreview.color)
        }
    }

    @Test
    fun `list of Guidance domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { CreateNotes.getEmptyGuidanceNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 4) % noteColors.size], notePreview.color)
        }
    }

    @Test
    fun `list of RoutineTasks domain after mapping to presentation has correct colors`() {
        val notes = List(noteColors.size + 1) { CreateNotes.getEmptyRoutineTasksNote() }
        val mappedNotes = notes.mapIndexed { index, item -> item.toUI(index) }
        mappedNotes.forEachIndexed { index, notePreview ->
            assertEquals(noteColors[(index + 5) % noteColors.size], notePreview.color)
        }
    }
}