package com.codennamdi.roomdemoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student-table")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nameId")
    val name: String = "",
    @ColumnInfo(name = "matricNumberId")
    val matricNumber: Int,
    @ColumnInfo(name = "email-id")
    val email: String = "",
    @ColumnInfo(name = "genderId")
    val gender: String = "",
    @ColumnInfo(name = "phoneNumberId")
    val phoneNumber: Int,
    @ColumnInfo(name = "ageId")
    val age: Int,
    @ColumnInfo(name = "schoolNameId")
    val schoolName: String = ""
)
