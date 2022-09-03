package com.codennamdi.roomdemoapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codennamdi.roomdemoapp.databinding.ActivityEditDialogBinding
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

        lifecycleScope.launch {
            studentDao.fetchAllStudent().collect {
                val list = ArrayList(it)
                setUpListToTheRecyclerView(list, studentDao)
            }
        }
    }

    private fun addDetails(studentDAO: StudentDAO) {
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

    private fun setUpListToTheRecyclerView(studentList: ArrayList<StudentEntity>, studentDAO: StudentDAO) {
        if (studentList.isNotEmpty()) {
            val itemDetailAdapter = ItemDetailsAdapter(
                studentList,
                { updateId ->
                    updateDetailsDialog(updateId, studentDAO)
                },
                { deleteId, deleteName, deleteMatricNumber, deleteEmail, deleteGender, deletePhoneNumber, deleteAge, deleteSchoolName ->
                    deleteDetailsDialog(
                        deleteId,
                        deleteName,
                        deleteMatricNumber,
                        deleteEmail,
                        deleteGender,
                        deletePhoneNumber,
                        deleteAge,
                        deleteSchoolName,
                        studentDAO
                    )
                }
            )
            binding.recyclerViewWidget.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewWidget.adapter = itemDetailAdapter
            binding.recyclerViewWidget.visibility = View.VISIBLE
            binding.textViewNoRecordAvailable.visibility = View.GONE
        } else {
            binding.recyclerViewWidget.visibility = View.GONE
            binding.textViewNoRecordAvailable.visibility = View.VISIBLE
        }
    }

    private fun updateDetailsDialog(id: Int, studentDAO: StudentDAO) {
        val updateDialog = Dialog(this, androidx.constraintlayout.widget.R.style.Theme_AppCompat_Dialog)
        updateDialog.setCancelable(false)
        val binding = ActivityEditDialogBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch {
            studentDAO.fetchStudentById(id).collect {
               if (it!=null){
                   binding.textFieldUpdatedName.setText(it.name)
                   binding.textFieldUpdatedMatricNumb.setText(it.matricNumber.toString())
                   binding.textFieldUpdatedEmail.setText(it.email)
                   binding.textFieldUpdatedGender.setText(it.gender)
                   binding.textFieldUpdatedPhoneNumb.setText(it.phoneNumber.toString())
                   binding.textFieldUpdatedAge.setText(it.age.toString())
                   binding.textFieldUpdatedSchoolName.setText(it.schoolName)
               }
            }
        }

       binding.textViewUpdate.setOnClickListener {
           val updatedName = binding.textFieldUpdatedName.text.toString()
           val updateMatricNumber = binding.textFieldUpdatedMatricNumb.text.toString()
           val updatedEmail = binding.textFieldUpdatedEmail.text.toString()
           val updatedGender = binding.textFieldUpdatedGender.text.toString()
           val updatedPhoneNumber = binding.textFieldUpdatedPhoneNumb.text.toString()
           val updatedAge = binding.textFieldUpdatedAge.text.toString()
           val updatedSchoolName = binding.textFieldUpdatedSchoolName.text.toString()

           if (updatedName.isNotEmpty() && updateMatricNumber.isNotEmpty() && updatedEmail.isNotEmpty() &&
               updatedGender.isNotEmpty() && updatedPhoneNumber.isNotEmpty() && updatedAge.isNotEmpty() && updatedSchoolName.isNotEmpty()
           ) {
               lifecycleScope.launch {
                   studentDAO.update(
                       StudentEntity(
                           id,
                           name = updatedName,
                           matricNumber = updateMatricNumber.toLong(),
                           email = updatedEmail,
                           gender = updatedGender,
                           phoneNumber = updatedPhoneNumber.toLong(),
                           age = updatedAge.toInt(),
                           schoolName = updatedSchoolName
                       )
                   )
               }
               Toast.makeText(this@MainActivity, "Details Updated", Toast.LENGTH_LONG).show()
               updateDialog.dismiss()
           } else {
               Toast.makeText(
                   this@MainActivity,
                   "name, matric number, email, gender, phone number, age, school name can't be blank.",
                   Toast.LENGTH_LONG
               ).show()
           }
       }

        binding.textViewCancel.setOnClickListener {
            updateDialog.dismiss()
        }
        updateDialog.show()
    }

    private fun deleteDetailsDialog(
        id: Int,
        name: String,
        matricNum: Long,
        email: String,
        gender: String,
        phoneNumber: Long,
        age: Int,
        schoolNam: String,
        studentDAO: StudentDAO
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Details")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setMessage("Are you sure you want to delete $name")

        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            lifecycleScope.launch {
                studentDAO.delete(
                    StudentEntity(
                        id,
                        name,
                        matricNum,
                        email,
                        gender, phoneNumber, age, schoolNam
                    )
                )
                Toast.makeText(
                    applicationContext,
                    "Details deleted successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
            dialogInterface.dismiss()
        }

        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }
}