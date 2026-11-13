package com.example.taskflow.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.taskflow.data.room.enums.CategoryEnum
import com.example.taskflow.data.room.enums.PriorityEnum
import com.example.taskflow.data.room.enums.StatusEnum
import com.example.taskflow.presentation.viewmodel.TaskViewModel
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.ArrowNarrowLeft
import dev.seyfarth.tablericons.outlined.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(controller: NavController) {



    var showDatePicker by remember {mutableStateOf(false) }
    val viewModel: TaskViewModel = hiltViewModel()

    val showDatePickerState = rememberDatePickerState()

    val date = System.currentTimeMillis()

    var dueDateFormatted = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(viewModel.dueDate ))



    val context = LocalContext.current








    Scaffold(

        modifier = Modifier
            .padding(horizontal = 16.dp)
            .imePadding(),
        topBar = {


            CenterAlignedTopAppBar(

                title = {

                    Text(text = "Create Task", style = MaterialTheme.typography.headlineSmall)
                },
                navigationIcon = {

                    Icon(
                        imageVector = TablerIcons.Outlined.ArrowNarrowLeft,
                        contentDescription = "ArrowBack",
                        modifier = Modifier.clickable(onClick = { controller.popBackStack() })
                    )
                }
            )


        },
        bottomBar = {

            Button(
                onClick = {

                    viewModel.onCreatedAt(date)
                    viewModel.onUpdatedAt(date)
                    viewModel.onStatusChange(StatusEnum.PENDING)

                    viewModel.insertTask()

                    viewModel.let {

                        it.onTitleChange("")
                        it.onDescriptionChange("")
                        it.onDueDateChange(0)
                        it.onPriorityChange(PriorityEnum.MEDIUM)
                        it.onCategoryChange(CategoryEnum.STUDY)
                        it.onCreatedAt(0)
                        it.onUpdatedAt(0)
                        it.onStatusChange(StatusEnum.PENDING)


                    }


                    Toast.makeText(context,"Saving Task...",Toast.LENGTH_SHORT).show()
                    controller.popBackStack()

                },
                enabled = viewModel.title.isNotEmpty() && dueDateFormatted.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()


                    .padding(bottom = 8.dp)
            ) {

                Text(text = "Save Task", style = MaterialTheme.typography.headlineSmall)
            }


        }


    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 10.dp)

        ) {
            item {


                Text(
                    "Add details about your new task",
                    color = Color.Gray
                )

                Spacer(Modifier.height(28.dp))

                Text("Task Title", style = MaterialTheme.typography.titleSmall)

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.onTitleChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter task title") },
                    singleLine = true
                )

                Spacer(Modifier.height(20.dp))

                Text("Description", style = MaterialTheme.typography.titleSmall)

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.description,
                    onValueChange = { viewModel.onDescriptionChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = { Text("Write task description...") }
                )

                Spacer(Modifier.height(20.dp))

                Text("Due Date", style = MaterialTheme.typography.titleSmall)

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = dueDateFormatted,
                    onValueChange = {viewModel.onDueDateChange(viewModel.dueDate)},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    placeholder = { Text("dd/MMM/yyyy") },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {

                            showDatePicker = true

                        }) {
                            Icon(
                                imageVector = TablerIcons.Outlined.Calendar,
                                contentDescription = "Calender"
                            )
                        }
                    }
                )

                if(showDatePicker){

                    DatePickerDialog(
                        onDismissRequest = {showDatePicker = false},
                        confirmButton = {

                            TextButton(onClick = {
                                showDatePickerState.selectedDateMillis?.let { milli->

                                    viewModel.onDueDateChange(milli)
                                    showDatePicker = false

                                }


                            }) {

                                Text("OK")
                            }


                        }
                    ) {
                        DatePicker(state =  showDatePickerState)


                    }
                }

                Spacer(Modifier.height(20.dp))

                Text("Priority", style = MaterialTheme.typography.titleSmall)

                Spacer(Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PriorityEnum.entries.forEach { currentPriority ->
                        FilterChip(
                            selected = viewModel.priority == currentPriority,
                            onClick = { viewModel.onPriorityChange(currentPriority) },
                            label = { Text(currentPriority.toString()) }
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Text("Category", style = MaterialTheme.typography.titleSmall)

                Spacer(Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryEnum.entries.forEach { category ->
                        FilterChip(
                            selected = viewModel.category == category,
                            onClick = { viewModel.onCategoryChange(category) },
                            label = { Text(category.toString()) }
                        )
                    }
                }


            }


        }
    }
}
