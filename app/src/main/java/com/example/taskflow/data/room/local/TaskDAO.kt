package com.example.taskflow.data.room.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDAO {


    @Insert
    suspend fun insertTask(insertTask: TaskEntity)


    @Update
    suspend fun updateTask(updateTask: TaskEntity)


    @Delete
    suspend fun deleteTask(deleteTask: TaskEntity)


    @Query("select * from task order by id desc")
    fun getAllTask(): Flow<List<TaskEntity>>


}

