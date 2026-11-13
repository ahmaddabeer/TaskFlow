package com.example.taskflow.data.room.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskflow.data.room.enums.CategoryEnum
import com.example.taskflow.data.room.enums.PriorityEnum
import com.example.taskflow.data.room.enums.StatusEnum


@Entity(tableName = "task")
data class TaskEntity(


    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long,
    val dueDate: Long,
    val status: StatusEnum,
    val priority: PriorityEnum,
    val category: CategoryEnum,

    )