package uz.texnopos.mytodoapp.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uz.texnopos.mytodoapp.ui.screens.task.TaskScreen
import uz.texnopos.mytodoapp.ui.viewmodels.SharedViewModel
import uz.texnopos.mytodoapp.util.Action
import uz.texnopos.mytodoapp.util.Constants.TASK_ARGUMENT_KEY
import uz.texnopos.mytodoapp.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit,
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId = taskId)

        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        TaskScreen(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen)
    }
}