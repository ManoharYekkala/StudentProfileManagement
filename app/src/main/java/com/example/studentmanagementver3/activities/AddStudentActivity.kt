package com.example.studentmanagementver3.activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementver3.databases.StudentDatabase
import com.example.studentmanagementver3.models.Student
import com.example.studentmanagementver3.models.StudentList
import studentmanagementver3.R

class AddStudentActivity : AppCompatActivity() {

    private var studentClassSpinner: Spinner? = null
    private var studentNameET: EditText? = null
    private var studentBirthday: EditText? = null
    private var studentGenderRadioBtn: RadioButton? = null
    private var studentGenderRadioGroup: RadioGroup? = null
    private var registrationNumberET: EditText? = null
    private var attendancePercentageET: EditText? = null
    private var registrationNumber: EditText? = null
    private var attendancePercentage: EditText? = null

    private var saveStudentBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        var studentClassroom: String? = null

        studentNameET = findViewById(R.id.inputNameET)
        studentBirthday = findViewById(R.id.inputDateET)
        studentGenderRadioGroup = findViewById(R.id.radioGroup)
        studentClassSpinner = findViewById(R.id.inputClassSpinner)
        saveStudentBtn = findViewById(R.id.saveAddStudentBtn_1)
        registrationNumberET = findViewById(R.id.inputRegistrationNumberET)
        attendancePercentageET = findViewById(R.id.inputAttendancePercentageET)


        val classroomList = listOf("19KTPM1", "19KTPM2", "19KTPM3")
        val db = StudentDatabase.getInstance(this)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classroomList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        studentClassSpinner!!.adapter = adapter
        studentClassSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                studentClassroom = classroomList[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        saveStudentBtn!!.setOnClickListener {
            // check empty name
            if (TextUtils.isEmpty(studentNameET!!.text.toString())) { // check if input is empty
                studentNameET!!.setError("This field can't be empty")
                return@setOnClickListener
            }
            // check empty birthday
            if (TextUtils.isEmpty(studentBirthday!!.text.toString())) { // check if input is empty
                studentBirthday!!.setError("This field can't be empty")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(registrationNumberET!!.text.toString())) {
                registrationNumberET!!.setError("This field can't be empty")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(attendancePercentageET!!.text.toString())) {
                attendancePercentageET!!.setError("This field can't be empty")
                return@setOnClickListener
            }

            // get student gender
            var radioID: Int = studentGenderRadioGroup!!.checkedRadioButtonId
            studentGenderRadioBtn = findViewById(radioID)
            // create student object
            var student = Student(
                studentNameET!!.text.toString(),
                studentBirthday!!.text.toString(),
                studentClassroom.toString(),
                studentGenderRadioBtn!!.text.toString(),
                registrationNumberET!!.text.toString(),
                attendancePercentageET!!.text.toString()

            )
            // add student to list
            StudentList.addStudent(student)
            Log.i("total student", StudentList.getItemCount().toString())
            student.id = StudentList.getItemCount() - 1
            db!!.studentDao().insertStudent(StudentList.getItemCount() - 1, student.name, student.birthday, student.classroom, student.gender,student.registrationNumber,student.attendancePercentage)
            Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show()
            // clear user input
            this.clearInput()
        }
    }

    fun clearInput() {
        studentNameET!!.setText("")
        studentBirthday!!.setText("")
        studentGenderRadioGroup!!.check(R.id.maleRB)
        studentClassSpinner!!.setSelection(0)
        registrationNumberET!!.setText("")
        attendancePercentageET!!.setText("")
    }
}