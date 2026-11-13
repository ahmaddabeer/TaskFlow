
package com.example.taskflow.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Search_FilterScreen(
    controller: NavController

) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("All") }
    var selectedPriority by remember { mutableStateOf("All") }
    var selectedSort by remember { mutableStateOf("Due Date") }

    val green = Color(0xFF238B67)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        TextButton(onClick ={}) {
            Text("← Back", color = green)
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Search & Filter",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "Find and organize your tasks easily",
            color = Color.Gray
        )

        Spacer(Modifier.height(24.dp))

        // Search

        Text(
            "Search Tasks",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search by task name...") },
            singleLine = true
        )

        Spacer(Modifier.height(28.dp))

        // Status Filter

        Text(
            "Task Status",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("All", "Pending", "In Progress").forEach {
                FilterChip(
                    selected = selectedStatus == it,
                    onClick = { selectedStatus = it },
                    label = { Text(it) }
                )
            }
        }

        FilterChip(
            selected = selectedStatus == "Completed",
            onClick = { selectedStatus = "Completed" },
            label = { Text("Completed") }
        )

        Spacer(Modifier.height(28.dp))

        // Priority Filter

        Text(
            "Priority",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("All", "Low", "Medium", "High").forEach {
                FilterChip(
                    selected = selectedPriority == it,
                    onClick = { selectedPriority = it },
                    label = { Text(it) }
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        // Sort By

        Text(
            "Sort Tasks By",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(12.dp))

        listOf(
            "Due Date",
            "Created Date",
            "Priority",
            "Task Name"
        ).forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(it)

                RadioButton(
                    selected = selectedSort == it,
                    onClick = { selectedSort = it }
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        // Buttons

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = green
            )
        ) {
            Text("Apply Filters")
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                searchQuery = ""
                selectedStatus = "All"
                selectedPriority = "All"
                selectedSort = "Due Date"
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Reset Filters")
        }

        Spacer(Modifier.height(20.dp))
    }
}
