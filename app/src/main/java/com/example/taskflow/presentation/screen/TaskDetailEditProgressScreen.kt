package com.example.taskflow.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.taskflow.data.room.enums.StatusEnum
import com.example.taskflow.presentation.navigation.Route
import com.example.taskflow.presentation.viewmodel.TaskViewModel
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.ChevronLeft
import dev.seyfarth.tablericons.outlined.Edit
import dev.seyfarth.tablericons.outlined.Trash
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailEditScreen(
    controller: NavController,
    id: Int

) {


    var isEditActive = remember { mutableStateOf(false) }


    val viewModel: TaskViewModel = hiltViewModel()

    val task by viewModel.getAllTask().collectAsStateWithLifecycle()

    val selectedTask = task.find { it.id == id }

    val coroutine = rememberCoroutineScope()

    var dialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(selectedTask?.id) {

        selectedTask?.let {

            viewModel.onId(it.id)
            viewModel.onStatusChange(it.status)
            viewModel.onTitleChange(it.title)
            viewModel.onDescriptionChange(it.description)
            viewModel.onCreatedAt(it.createdAt)
            viewModel.onUpdatedAt(it.updatedAt)
            viewModel.onCategoryChange(it.category)
            viewModel.onDueDateChange(it.dueDate)
            viewModel.onPriorityChange(it.priority)

        }
    }


    Scaffold(

        topBar = {

            TopAppBar(

                title = { Text("Task Detail", style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {

                    IconButton(onClick = { controller.popBackStack() }) {

                        Icon(
                            imageVector = TablerIcons.Outlined.ChevronLeft,
                            contentDescription = "Back To home"
                        )
                    }
                },
                actions = {

                    IconButton(onClick = {


                        if (dialog) dialog = false else dialog = true


                    }) {

                        Icon(
                            imageVector = TablerIcons.Outlined.Trash,
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }

            )
        }


    ) { innerPadding ->

        if (isEditActive.value) {
            EditScreen(viewModel)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {

                item {


                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF0F7F3)
                        )
                    ) {
                        Column(Modifier.padding(18.dp)) {

                            Text(
                                "${selectedTask?.title}",
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(Modifier.height(12.dp))

                            Text(
                                "${selectedTask?.description}",
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(Modifier.height(28.dp))

                    Text(
                        "Task Information",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(16.dp))

                    DetailRow(
                        "Due Date", SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                            Date(viewModel.dueDate)
                        )
                    )

                    HorizontalDivider(Modifier.padding(vertical = 12.dp))

                    DetailRow("Due Time", SimpleDateFormat("hh:mm a", Locale.getDefault()).format(
                        Date(viewModel.dueDate)
                    ))

                    HorizontalDivider(Modifier.padding(vertical = 12.dp))

                    DetailRow("Priority", "${selectedTask?.priority}")

                    HorizontalDivider(Modifier.padding(vertical = 12.dp))

                    DetailRow("Category", "${selectedTask?.category}")

                    Spacer(Modifier.height(28.dp))

                    Text(
                        "Task Status",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(10.dp))

                    StatusEnum.entries.forEach { status ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                status.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = if (status == viewModel.status) {
                                    if (status == StatusEnum.PENDING) Color.Red
                                    else if (status == StatusEnum.COMPLETE) Color.Green
                                    else Color.Yellow
                                } else Color.LightGray
                            )

                            RadioButton(
                                selected = viewModel.status == status,
                                onClick = { viewModel.onStatusChange(status) }
                            )
                        }
                    }



                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Button(
                            onClick = {


                                viewModel.updateTask()
                                Toast.makeText(context, "Progress Saved", Toast.LENGTH_SHORT).show()
                                controller.navigate(Route.HomeScreen) {

                                    popUpTo<Route.HomeScreen> {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }


                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF238B67)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Save Progress")
                        }

                        Spacer(modifier = Modifier.width(5.dp))
                        IconButton(
                            onClick = {

                                isEditActive.value = true


                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Yellow.copy(0.6f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Icon(
                                imageVector = TablerIcons.Outlined.Edit,
                                contentDescription = "Edit"
                            )
                        }
                    }


                }
            }

        }









        if (dialog) {

            ShowDialog(
                { dialog = false },
                { dialog = false },
                { viewModel.deleteTask(id) },
                controller
            )
        }


    }


}

@Composable
private fun DetailRow(
    label: String,
    value: String,


    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.bodyMedium)


    }
}


@Composable
private fun ShowDialog(


    onDismiss: () -> Unit,
    onDismissButton: () -> Unit,
    onConfirmButton: () -> Unit,
    controller: NavController,


    ) {

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {

            Button(
                onClick = {

                    onConfirmButton()
                    Toast.makeText(context, "Deleting..", Toast.LENGTH_SHORT).show()
                    controller.popBackStack()


                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(0.6f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {

                Text("Oky")
            }
        },
        dismissButton = {

            Button(
                onClick = {

                    onDismissButton()


                },
                colors = ButtonDefaults.buttonColors(

                    containerColor = Color.Green.copy(0.6f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {

                Text("Cancel")
            }
        },

        title = {

            Text("Delete", color = Color.Red)
        },
        text = {

            Text("Are you sure want to delete this task")

        }


    )
}
