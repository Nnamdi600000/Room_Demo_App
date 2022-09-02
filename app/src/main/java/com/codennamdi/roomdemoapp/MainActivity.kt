package com.codennamdi.roomdemoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.codennamdi.roomdemoapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val studentDao = (application as StudentApp).db.studentDao()

        binding.buttonAddDetails.setOnClickListener {
            addDetails(studentDao)
        }
    }

    fun addDetails(studentDAO: StudentDAO) {
        val name = binding.textFieldName.text.toString()
        val matricNumber = binding.textFieldMatricNumb.text.toString()
        val emailAddress = binding.textFieldEmail.text.toString()
        val gender = binding.textFieldGender.text.toString()
        val phoneNumber = binding.textFieldPhoneNumb.text.toString()
        val age = binding.textFieldAge.text.toString()
        val schoolName = binding.textFieldSchoolName.text.toString()

        if (name.isNotEmpty() && matricNumber.isNotEmpty() && emailAddress.isNotEmpty() &&
            gender.isNotEmpty() && phoneNumber.isNotEmpty() && age.isNotEmpty() && schoolName.isNotEmpty()
        ) {
            lifecycleScope.launch {
                studentDAO.insert(
                    StudentEntity(
                        name = name,
                        matricNumber = matricNumber.toLong(),
                        email = emailAddress,
                        gender = gender,
                        phoneNumber = phoneNumber.toLong(),
                        age = age.toInt(),
                        schoolName = schoolName
                    )
                )
                Toast.makeText(applicationContext, "Details saved!", Toast.LENGTH_LONG).show()
                binding.textFieldName.text?.clear()
                binding.textFieldMatricNumb.text?.clear()
                binding.textFieldEmail.text?.clear()
                binding.textFieldGender.text?.clear()
                binding.textFieldPhoneNumb.text?.clear()
                binding.textFieldAge.text?.clear()
                binding.textFieldSchoolName.text?.clear()
            }
        } else {
            Toast.makeText(
                this@MainActivity,
                "name, matric number, email, gender, phone number, age, school name cannot be empty",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun setUpListToTheRecyclerView(studentList: ArrayList<StudentEntity>, studentDAO: StudentDAO) {

    }
}