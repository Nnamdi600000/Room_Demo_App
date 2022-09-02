package com.codennamdi.roomdemoapp

import android.app.Application

class StudentApp : Application() {
    val db by lazy {
        StudentDatabase.getInstance(this)
    }
}