package com.example.taskflow.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.taskflow.data.room.enums.StatusEnum
import com.example.taskflow.presentation.navigation.Route
import com.example.taskflow.presentation.screen.component.TaskItemCard
import com.example.taskflow.presentation.viewmodel.TaskViewModel
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.ArrowBack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllListScreen(controller: NavController, status:String) {


    val viewmodel: TaskViewModel = hiltViewModel()

    val alltaskList by viewmodel.getAllTask().collectAsStateWithLifecycle()



    val filterdTask = alltaskList.filter { it.status.toString() == status }



    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(status.toString(), style = MaterialTheme.typography.headlineMedium)
                },
                navigationIcon = {

                    IconButton(onClick = {controller.popBackStack()}) {

                        Icon(imageVector = TablerIcons.Outlined.ArrowBack, contentDescription = "Bakc")
                    }
                }
            )

        }
    ) { innerPadding ->


        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)

            .padding(8.dp),

            verticalArrangement = Arrangement.spacedBy(8.dp)) {


            items(items = if(status.equals("Total")) alltaskList else filterdTask){ item ->

                TaskItemCard(

                    item.title,
                    item.description.take(20),
                    item.status.toString(),
                    {controller.navigate(Route.TaskDetailEditProgressScreen(item.id))}


                )
            }
        }


    }


}