package com.example.taskflow.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskflow.data.repository.TaskRepository
import com.example.taskflow.data.room.enums.CategoryEnum
import com.example.taskflow.data.room.enums.PriorityEnum
import com.example.taskflow.data.room.enums.StatusEnum
import com.example.taskflow.data.room.local.TaskEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {



    var id by mutableStateOf(0)
        private set
    var title by mutableStateOf("")
        private set


    var description by mutableStateOf("")
        private set

    var createdAt by mutableLongStateOf(0)
        private set

    var updatedAt by mutableLongStateOf(0)
        private set

    var dueDate by mutableLongStateOf(0)

    var priority by mutableStateOf(PriorityEnum.MEDIUM)
        private set

    var category by mutableStateOf(CategoryEnum.PERSONAL)
        private set

    var status by mutableStateOf(StatusEnum.PENDING)
        private set


    fun onId(d1:Int){

        id = d1
    }

    fun onTitleChange(t1: String) {

        title = t1
    }

    fun onDescriptionChange(d1: String) {

        description = d1
    }

    fun onCreatedAt(c1:Long) {

        createdAt = c1
    }

    fun onUpdatedAt(u1: Long) {

        updatedAt = u1
    }

    fun onDueDateChange(d1: Long) {
        dueDate = d1
    }

    fun onPriorityChange(p1: PriorityEnum) {

        priority = p1
    }

    fun onStatusChange(s1: StatusEnum) {

        status = s1
    }

    fun onCategoryChange(c1: CategoryEnum){

        category = c1
    }


    private var tasks : StateFlow<List<TaskEntity>> =

        repository.getAllTask().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue =emptyList()

        )


    fun getAllTask(): StateFlow<List<TaskEntity>> = tasks


    fun insertTask(){

        if(title.isNotEmpty() && description.isNotEmpty() ){

            val task = TaskEntity(

                id = 0,
                title = title,
                description = description,
                createdAt = createdAt,
                updatedAt = updatedAt,
                dueDate = dueDate,
                category = category,
                status = status,
                priority = priority

            )

            viewModelScope.launch { repository.insert(task) }
        }
    }

    fun updateTask(){


        if(title.isNotEmpty() && description.isNotEmpty() ){

            val task = TaskEntity(

                id,
                title,
                description,
                updatedAt,
                createdAt,
                dueDate,
                status,
                priority,
                category,


            )

            viewModelScope.launch { repository.update(task) }
        }
    }


    fun deleteTask(id: Int){

        if(title.isNotEmpty() && description.isNotEmpty()){

            val task = TaskEntity(

                id,
                title,
                description,
                createdAt,
                updatedAt,
                dueDate,
                status,
                priority,
                category
            )

            viewModelScope.launch { repository.delete(task) }


        }
    }


}