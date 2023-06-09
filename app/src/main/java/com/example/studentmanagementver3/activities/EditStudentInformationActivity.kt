package com.example.studentmanagementver3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.studentmanagementver3.databases.StudentDatabase
import com.example.studentmanagementver3.models.Student
import com.example.studentmanagementver3.models.StudentList
import studentmanagementver3.R

class EditStudentInformationActivity : AppCompatActivity() {
    private var editStudentNameET: EditText? = null
    private var editStudentBirthdayET: EditText? = null
    private var editStudentClassSpinner: Spinner? = null
    private var radioGroup: RadioGroup? = null
    private var radioButton: RadioButton? = null
    private var saveBtn: Button? = null
    private var deleteBtn: Button? = null
    private var registrationNumber: EditText? = null
    private var attendancePercentage: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student_information)

        var classroom: String? = null

        editStudentNameET = findViewById(R.id.editNameET)
        editStudentBirthdayET = findViewById(R.id.editDateET)
        editStudentClassSpinner = findViewById(R.id.editClassSpinner)
        radioGroup = findViewById(R.id.radioGroup_2)
        registrationNumber = findViewById(R.id.editTextRegistrationNumber)
        attendancePercentage = findViewById(R.id.editTextAttendancePercentage)
        saveBtn = findViewById(R.id.saveAddStudentBtn_2)
        deleteBtn = findViewById(R.id.deleteStudentBtn)


        val classroomList = listOf("A1", "A2", "A3")
        val db = StudentDatabase.getInstance(this)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classroomList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editStudentClassSpinner!!.adapter = adapter
        editStudentClassSpinner!!.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    classroom = classroomList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        val intent = intent
        val position = intent.getIntExtra("position", 0)
        Log.i("hdlog1", position.toString())
        this.setValueForContentView(StudentList.getStudentAtPosition(position))

        saveBtn!!.setOnClickListener {
            // check empty name
            if (TextUtils.isEmpty(editStudentNameET!!.text.toString())) { // check if input is empty
                editStudentNameET!!.setError("This field can't be empty")
                return@setOnClickListener
            }
            // check empty birthday
            if (TextUtils.isEmpty(editStudentBirthdayET!!.text.toString())) { // check if input is empty
                editStudentBirthdayET!!.setError("This field can't be empty")
                return@setOnClickListener
            }

            // get student gender
            var radioID: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(radioID)

            var newStudent = Student(
                editStudentNameET!!.text.toString(),
                editStudentBirthdayET!!.text.toString(),
                classroom.toString(),
                radioButton!!.text.toString(),
                registrationNumber.toString(),
                attendancePercentage.toString()
            )

            StudentList.editStudentInfo(position, newStudent)
            newStudent.id = position
            db!!.studentDao().updateStudent(newStudent)
            Toast.makeText(this, "Update successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
        deleteBtn!!.setOnClickListener {
            StudentList.deleteStudent(position)
            db!!.studentDao().deleteStudent(position)
            db!!.studentDao().updateID(position)
            Toast.makeText(this, "Delete successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun setValueForContentView(student: Student) {
        editStudentNameET!!.setText(student.name)
        editStudentBirthdayET!!.setText(student.birthday)
        var pos: Int = student.classroom.last().digitToInt() - 1
        editStudentClassSpinner!!.setSelection(pos)
        if (student.gender == "Male")
            radioGroup!!.check(R.id.maleRB_2)
        else if (student.gender == "Female")
            radioGroup!!.check(R.id.femaleRB_2)
        else
            radioGroup!!.check(R.id.otherGenderRB_2)
        registrationNumber!!.setText(student.registrationNumber)
        attendancePercentage!!.setText(student.attendancePercentage)

    }
}