package com.example.taskflow.data.repository

import com.example.taskflow.data.room.local.TaskDAO
import com.example.taskflow.data.room.local.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TaskRepository @Inject constructor(private val dao: TaskDAO) {



    fun getAllTask(): Flow<List<TaskEntity>> {

        return dao.getAllTask()
    }



    suspend fun insert(task: TaskEntity) = dao.insertTask(task)

    suspend fun update(task: TaskEntity) = dao.updateTask(task)

    suspend fun delete(task: TaskEntity) = dao.deleteTask(task)




}