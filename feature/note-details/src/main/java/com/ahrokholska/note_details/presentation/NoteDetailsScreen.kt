package com.ahrokholska.note_details.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.note_details.presentation.noteTypes.buy.BuySomethingDetailsScreenViewModel
import com.ahrokholska.note_details.presentation.noteTypes.buy.BuyingSomethingDetailsScreen
import com.ahrokholska.note_details.presentation.noteTypes.goal.GoalsDetailsScreen
import com.ahrokholska.note_details.presentation.noteTypes.goal.GoalsDetailsScreenViewModel
import com.ahrokholska.note_details.presentation.noteTypes.guidance.GuidanceDetailsScreen
import com.ahrokholska.note_details.presentation.noteTypes.guidance.GuidanceDetailsScreenViewModel
import com.ahrokholska.note_details.presentation.noteTypes.idea.InterestingIdeaDetailsScreen
import com.ahrokholska.note_details.presentation.noteTypes.idea.InterestingIdeaDetailsScreenViewModel
import com.ahrokholska.note_details.presentation.noteTypes.routine.RoutineTasksDetailsScreen
import com.ahrokholska.note_details.presentation.noteTypes.routine.RoutineTasksDetailsScreenViewModel
import com.ahrokholska.note_presentation.model.NoteType

@Composable
internal fun NoteDetailsScreen(id: Int, type: NoteType, onBackClick: () -> Unit = {}) {
    when (type) {
        NoteType.InterestingIdea -> InterestingIdeaDetailsScreen(
            viewModel = hiltViewModel<InterestingIdeaDetailsScreenViewModel, InterestingIdeaDetailsScreenViewModel.Factory>(
                creationCallback = { factory -> factory.create(id = id) }
            ),
            onBackClick = onBackClick
        )

        NoteType.BuyingSomething -> BuyingSomethingDetailsScreen(
            viewModel = hiltViewModel<BuySomethingDetailsScreenViewModel, BuySomethingDetailsScreenViewModel.Factory>(
                creationCallback = { factory -> factory.create(id = id) }
            ),
            onBackClick = onBackClick
        )

        NoteType.Goals -> GoalsDetailsScreen(
            viewModel = hiltViewModel<GoalsDetailsScreenViewModel, GoalsDetailsScreenViewModel.Factory>(
                creationCallback = { factory -> factory.create(id = id) }
            ),
            onBackClick = onBackClick
        )

        NoteType.Guidance -> GuidanceDetailsScreen(
            viewModel = hiltViewModel<GuidanceDetailsScreenViewModel, GuidanceDetailsScreenViewModel.Factory>(
                creationCallback = { factory -> factory.create(id = id) }
            ),
            onBackClick = onBackClick
        )

        NoteType.RoutineTasks -> RoutineTasksDetailsScreen(
            viewModel = hiltViewModel<RoutineTasksDetailsScreenViewModel, RoutineTasksDetailsScreenViewModel.Factory>(
                creationCallback = { factory -> factory.create(id = id) }
            ),
            onBackClick = onBackClick
        )
    }
}