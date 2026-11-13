package com.example.taskflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.taskflow.presentation.screen.AddTaskScreen
import com.example.taskflow.presentation.screen.AllListScreen
import com.example.taskflow.presentation.screen.HomeScreen
import com.example.taskflow.presentation.screen.Search_FilterScreen
import com.example.taskflow.presentation.screen.TaskDetailEditScreen

@Composable
fun NavController(){

    val controller = rememberNavController()




    NavHost(navController = controller, startDestination = Route.HomeScreen){


        composable<Route.HomeScreen>{



            HomeScreen(controller)
        }

        composable<Route.AddTaskScreen>{

            AddTaskScreen(controller)


        }

        composable<Route.Search_FilterScreen>{

            Search_FilterScreen(controller)
        }

        composable<Route.TaskDetailEditProgressScreen>{backStack->

            val route = backStack.toRoute<Route.TaskDetailEditProgressScreen>()

            TaskDetailEditScreen(controller,route.taskId)
        }


        composable<Route.AllListScreen>{backStack->

            val route = backStack.toRoute<Route.AllListScreen>()

            AllListScreen(controller,route.status)
        }
    }



}