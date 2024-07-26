package com.example.mytodo

import ViewModel.PopupViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mytodo.Database.Todo
import com.example.mytodo.ui.theme.CustomBlue
import com.example.mytodo.ui.theme.CustomDarkBlue
import com.example.mytodo.ui.theme.CustomWhite
import com.example.mytodo.ui.theme.satoshiFamily

@Composable
fun TodoPage( 
    popupViewModel :  PopupViewModel
) {
    val todoList by popupViewModel.todoList.observeAsState(emptyList())
    Column (modifier = Modifier
        .padding(8.dp)
        .fillMaxSize()) {
        Text(text = "Your Todo List"
            ,modifier = Modifier.padding(top = 30.dp, start = 20.dp),
            color = CustomDarkBlue,
            fontFamily = satoshiFamily,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.padding(10.dp))
        if (todoList.isNotEmpty()) {
            LazyColumn(content = {
                itemsIndexed(todoList){ index: Int, item: Todo ->
                    TodoItem(todo = item, onDelete = {
                        popupViewModel.delTodo(item.id)
                    })
                }
            }, modifier = Modifier.weight(1f))
        } else {
            Text(
                text = "Belum Ada Tugas",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(18.dp),
                textAlign = TextAlign.Start,
                fontFamily = satoshiFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal)
        }
        FloatingActionButton(onClick = { popupViewModel.openPopup() },
            modifier = Modifier
                .align(End)
                .padding(16.dp))
            {
            Icon(painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Task",
                tint = CustomDarkBlue)
        }
    }
    if (popupViewModel.isDialogShow){
        addTodo(onDismiss = { popupViewModel.onDismiss() },
            onConfirm = { tanggal, task ->
                popupViewModel.addTodo(tanggal, task)
            }
            )
    }
    
}

@Composable
fun TodoItem(todo : Todo, onDelete:() -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(color = CustomBlue)
        .padding(16.dp)) {
        Column(modifier= Modifier.weight(1f)) {
            Text(text = todo.tanggal,
                color = CustomDarkBlue,
                fontSize = 18.sp,
                fontFamily = satoshiFamily,
                fontWeight = FontWeight.Bold
            )
            Text(text = todo.task,
                color = CustomDarkBlue,
                fontSize = 20.sp,
                fontFamily = satoshiFamily,
                fontWeight = FontWeight.Bold
            )
        }
        IconButton(onClick = onDelete) {
            Icon(painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "Done",
                tint = Color.White)
        }
    }
}


@Composable
fun addTodo(
    onDismiss:() -> Unit,
    onConfirm:(String,String) -> Unit,
) {
    Dialog(onDismissRequest = {
        onDismiss()
    }, properties = DialogProperties(
        usePlatformDefaultWidth = false
    )) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add your to-do",
                    fontFamily = satoshiFamily,
                    color = CustomDarkBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Deadline Day",
                    fontFamily = satoshiFamily,
                    color = CustomDarkBlue,
                    fontSize = 18.sp
                )
                var inputTanggal by remember {
                    mutableStateOf("")
                }
                var inputTask by remember {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = inputTanggal,
                    onValueChange = {
                        inputTanggal = it
                    })
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Your Task",
                    fontFamily = satoshiFamily,
                    color = CustomDarkBlue,
                    fontSize = 18.sp
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = inputTask,
                    onValueChange = {
                        inputTask = it
                    })
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Button(
                        onClick =
                        onDismiss, colors = ButtonDefaults.buttonColors(
                            containerColor = CustomBlue,
                            contentColor = CustomWhite
                        ), modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f), shape = CircleShape
                    ) {
                        Text(
                            text = "Batal",
                            fontFamily = satoshiFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        onClick = {
                            onConfirm(inputTanggal,inputTask)
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = CustomBlue,
                            contentColor = CustomWhite
                        ), modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f), shape = CircleShape
                    ) {
                        Text(
                            text = "Tambah",
                            fontFamily = satoshiFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

