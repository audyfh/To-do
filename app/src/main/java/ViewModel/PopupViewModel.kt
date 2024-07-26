package ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodo.Database.Todo
import com.example.mytodo.MainAPP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopupViewModel : ViewModel() {
    var isDialogShow by mutableStateOf(false)
        private set

    fun onDismiss(){
        isDialogShow = false
    }

    fun openPopup(){
        isDialogShow = true
    }

    val todoDAO = MainAPP.DB.getTodoDAO()
    val todoList : LiveData<List<Todo>> = todoDAO.getTodo()

    fun addTodo(tanggal : String, task: String){
        viewModelScope.launch (Dispatchers.IO){
            todoDAO.addTodo(Todo(tanggal = tanggal, task = task ))
        }
        onDismiss()
    }

    fun delTodo(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.deleteTodo(id)
        }
    }
}