package uz.texnopos.mytodoapp.navigation.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uz.texnopos.mytodoapp.ui.screens.list.ListScreen
import uz.texnopos.mytodoapp.ui.viewmodels.SharedViewModel
import uz.texnopos.mytodoapp.util.Constants.LIST_ARGUMENT_KEY
import uz.texnopos.mytodoapp.util.Constants.LIST_SCREEN

@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen:(taskId:Int)->Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY){
            type= NavType.StringType
        })
    ){
        ListScreen(navigateToTaskScreen = navigateToTaskScreen,
        sharedViewModel = sharedViewModel)
    }
}