package com.example.mytodo

import android.app.Application
import androidx.room.Room
import com.example.mytodo.Database.TodoDB

class MainAPP : Application() {

    companion object {
        lateinit var DB : TodoDB
    }

    override fun onCreate() {
        super.onCreate()
        DB = Room.databaseBuilder(
            applicationContext,
            TodoDB::class.java,
            TodoDB.NAME
        ).build()
    }
}