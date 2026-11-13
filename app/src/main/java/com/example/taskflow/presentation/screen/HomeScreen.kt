package com.example.taskflow.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.taskflow.data.room.enums.StatusEnum
import com.example.taskflow.presentation.navigation.Route
import com.example.taskflow.presentation.screen.component.TaskItemCard
import com.example.taskflow.presentation.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Green = Color(0xFF238B67)
private val Dark = Color(0xFF202A24)
private val Grey = Color(0xFF858585)

@Composable
fun HomeScreen(
    controller: NavController,

    ) {


    val viewModel: TaskViewModel = hiltViewModel()

    val taskList by viewModel.getAllTask().collectAsStateWithLifecycle()

    val currentDate = System.currentTimeMillis()


    val total = taskList.count()
    val pending = taskList.filter { it.status == StatusEnum.PENDING }.count()
    val done = taskList.filter { it.status == StatusEnum.COMPLETE }.count()

    val upcomingTaskList =
        taskList.filter { dateFormatter(it.dueDate) == "${dateFormatter(currentDate).toInt() + 1}" }
            .filter { it.status == StatusEnum.PENDING }.take(4)

    val todayTaskList = taskList.filter { dateFormatter(it.dueDate) == dateFormatter(currentDate) }
        .filter { it.status == StatusEnum.PENDING || it.status == StatusEnum.IN_PROGRESS }.take(3)

    val overDue = taskList.filter { dateFormatter(it.dueDate) < dateFormatter(currentDate) }
        .filter { it.status == StatusEnum.PENDING }







    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { controller.navigate(Route.AddTaskScreen) },
                containerColor = Green,
                contentColor = Color.White
            ) {
                Text("+", fontSize = 28.sp)
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)

                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            item {

                Text(
                    text = "TaskFlow", style = MaterialTheme.typography.titleLarge, color = Green
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Good Morning 👋",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Dark
                )

                Text(
                    text = "Let's organize your day!", color = Grey
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    "Overview", style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {


                    SummaryCard(
                        "Total", total.toString(), Modifier
                            .weight(1f)

                            .clickable(onClick = {
                                controller.navigate(
                                    Route.AllListScreen("Total")
                                )
                            })
                    )

                    SummaryCard(
                        "Pending", pending.toString(), Modifier
                            .weight(1f)

                            .clickable(onClick = {
                                controller.navigate(
                                    Route.AllListScreen(
                                        StatusEnum.PENDING.toString()
                                    )
                                )
                            })
                    )

                    SummaryCard(
                        "Done", done.toString(), Modifier
                            .weight(1f)
                            .clickable(onClick = {
                                controller.navigate(
                                    Route.AllListScreen(
                                        StatusEnum.COMPLETE.toString()
                                    )
                                )
                            })
                    )


                }

                Spacer(Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Today's Tasks", style = MaterialTheme.typography.titleMedium
                    )
                    TextButton(onClick = { controller.navigate(Route.AllListScreen("Total")) }) {
                        Text("See All", color = Green)
                    }
                }

                Spacer(Modifier.height(8.dp))
            }

            // all today task list of 3 card


            if (todayTaskList.isNotEmpty()) {
                items(
                    items = todayTaskList, key = { it.id }) { item ->


                    TaskItemCard(
                        title = item.title, subtitle = SimpleDateFormat(
                            "dd MMM", Locale.getDefault()
                        ).format(Date(item.dueDate)), status = item.status.toString()
                    ) {

                        controller.navigate(Route.TaskDetailEditProgressScreen(item.id))

                    }

                    Spacer(Modifier.height(12.dp))


                }
            } else {
                item {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            "No Task Today",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Green.copy(0.5f),

                            )
                    }
                }


            }


            // Upcomming Tasks

            item {

                Spacer(Modifier.height(24.dp))

                Text(
                    "Upcoming Tasks", style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))
            }



            if (upcomingTaskList.isNotEmpty()) {
                items(
                    items = upcomingTaskList, key = { it.id }) { item ->


                    TaskItemCard(
                        title = item.title.take(20), subtitle = SimpleDateFormat(
                            "dd MMM", Locale.getDefault()
                        ).format(Date(item.dueDate)), status = item.status.toString()
                    ) {
                        controller.navigate(Route.TaskDetailEditProgressScreen(item.id))

                    }


                }
            } else {

                item {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            "No Upcoming Task",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Green.copy(0.5f),

                            )
                    }
                }
            }


            // due over

            item {

                Spacer(Modifier.height(24.dp))

                Text(
                    "OverDue", style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))
            }



            if (overDue.isNotEmpty()) {
                items(
                    items = overDue, key = { it.id }) { item ->


                    TaskItemCard(
                        title = item.title.take(20), subtitle = SimpleDateFormat(
                            "dd MMM", Locale.getDefault()
                        ).format(Date(item.dueDate)), status = item.status.toString()
                    ) {
                        controller.navigate(Route.TaskDetailEditProgressScreen(item.id))

                    }


                }
            } else {

                item {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            "No OverDue Task",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Green.copy(0.5f),

                            )
                    }
                }
            }


        }
    }
}

@Composable
private fun SummaryCard(
    title: String, count: String, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0F7F3)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(title, color = Grey, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                count,
                style = MaterialTheme.typography.headlineSmall,
                color = if (title.equals("Pending") && count.isNotEmpty()) Color.Red else Green
            )
        }


    }
}


private fun dateFormatter(date: Long): String {

    return SimpleDateFormat("dd", Locale.getDefault()).format(Date(date))
}
