package com.example.taskflow.presentation.navigation

import com.example.taskflow.data.room.enums.StatusEnum
import kotlinx.serialization.Serializable




@Serializable
sealed class  Route{



    @Serializable
    object HomeScreen:Route()


    @Serializable
    object AddTaskScreen: Route()



    @Serializable
    object Search_FilterScreen: Route()


    @Serializable
    data class TaskDetailEditProgressScreen(
        val taskId:Int
    )



    @Serializable
    data class AllListScreen(
        val status: String
    )





}