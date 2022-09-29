package com.codennamdi.roomdemoapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDAO {
    @Insert
    suspend fun insert(studentEntity: StudentEntity)

    @Update
    suspend fun update(studentEntity: StudentEntity)

    @Delete
    suspend fun delete(studentEntity: StudentEntity)

    @Query("SELECT * FROM  `student-table`")
    fun fetchAllStudent(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM  `student-table` where id=:id")
    fun fetchStudentById(id: Int): Flow<StudentEntity>
}