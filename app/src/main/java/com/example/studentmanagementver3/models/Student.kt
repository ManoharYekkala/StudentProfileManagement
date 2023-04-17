package com.example.studentmanagementver3.models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "birthday")
    var birthday: String,
    @ColumnInfo(name = "classroom")
    var classroom: String,
    @ColumnInfo(name = "gender")
    var gender: String,
    @ColumnInfo(name = "registration_number")
    var registrationNumber: String,
    @ColumnInfo(name = "attendance_percentage")
    var attendancePercentage: String
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
