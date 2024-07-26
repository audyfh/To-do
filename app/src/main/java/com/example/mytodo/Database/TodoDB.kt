package com.example.mytodo.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodoDB : RoomDatabase() {

    companion object {
        const val NAME = "Todo_DB"
    }

    abstract fun getTodoDAO() : TodoDAO
}