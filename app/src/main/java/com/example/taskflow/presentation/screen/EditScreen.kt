package com.example.taskflow.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.taskflow.data.room.enums.CategoryEnum
import com.example.taskflow.data.room.enums.PriorityEnum
import com.example.taskflow.presentation.viewmodel.TaskViewModel
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun EditScreen(viewModel: TaskViewModel) {

    var text by remember { mutableStateOf("") }


    val updateAt = System.currentTimeMillis()

    var showDatePicker by remember { mutableStateOf(false) }

    val showDatePickerState = rememberDatePickerState()






    LazyColumn( 
        modifier = Modifier.fillMaxSize(),

        )
    {

        item {

            Column(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier.padding(horizontal = 16.dp)) {

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    value = viewModel.title,
                    singleLine = true,
                    onValueChange = { viewModel.onTitleChange(it) },
                    label = { Text("Enter Your Title") })



                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        ,
                    value = viewModel.description,

                    onValueChange = { viewModel.onDescriptionChange(it) },
                    label = { Text("Enter Your description") })


                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    value = SimpleDateFormat(
                        "dd MMM yyyy hh:MM a",
                        Locale.getDefault()
                    ).format(Date(viewModel.dueDate)),
                    singleLine = true,
                    readOnly = true,
                    onValueChange = { viewModel.onDueDateChange(it.toLong()) },
                    label = { Text("Date") },
                    trailingIcon = {
                        IconButton(onClick = {

                            showDatePicker = true
                        })
                        {

                            Icon(
                                imageVector = TablerIcons.Outlined.Calendar,
                                contentDescription = "Calender"
                            )
                        }


                    }
                )

                Text("Priority")

                Spacer(modifier = Modifier.height(10.dp))


                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    PriorityEnum.entries.forEach { item ->

                        FilterChip(
                            selected = viewModel.priority == item,
                            onClick = { viewModel.onPriorityChange(item) },
                            label = { Text(item.toString()) }

                        )
                    }
                }

                Text("Category")
                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    CategoryEnum.entries.forEach { currentCategory ->

                        FilterChip(

                            selected = viewModel.category == currentCategory,
                            onClick = { viewModel.onCategoryChange(currentCategory) },
                            label = { Text(currentCategory.toString()) }
                        )
                    }
                }


                TextButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp)


                    ) {

                    Text("Save")
                }


            }

        }
    }

    if (showDatePicker) {

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {

                TextButton(onClick = {

                    showDatePickerState.selectedDateMillis?.let { milli ->

                        viewModel.onDueDateChange(milli)
                    }

                    showDatePicker = false


                }) { Text("Ok") }


            }) { DatePicker(state = showDatePickerState) }
    }
}



