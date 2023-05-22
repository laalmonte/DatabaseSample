package com.android.sample.databasesample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.sample.databasesample.databinding.ActivityDisplayBinding
import com.android.sample.databasesample.db.DBHelper


class DisplayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisplayBinding
    private val dbSql  = DBHelper(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachActions()

    }

    private fun attachActions(){
        binding.btnAdd.setOnClickListener { addUser() }
        binding.btnDisplay.setOnClickListener { display() }
        binding.btnDelete.setOnClickListener { deleteUser() }
        binding.btnUpdate.setOnClickListener { updateUser() }
    }

    fun addRole(){
        dbSql.addRole(binding.etValue.text.toString(), binding.etValue2.text.toString())
        binding.etValue.text!!.clear()
        binding.etValue2.text!!.clear()
    }

    @SuppressLint("Range")
    fun displayRoles(){
        val cursorResult = dbSql.getRoles()

        cursorResult?.let { cr ->
            if (cr.moveToFirst()) {
                do {
                    val status   = cr.getString(cr.getColumnIndex("name"))
                    val isAdmin  = cr.getString(cr.getColumnIndex("age"))
                    val idUser = cr.getString(cr.getColumnIndex("id"))
                    Log.e("DB","name $status age $isAdmin $idUser")
                } while (cr.moveToNext())
            }
        }
    }

    fun addUser(){
        dbSql.addName(binding.etValue.text.toString(), binding.etValue2.text.toString())
        binding.etValue.text!!.clear()
        binding.etValue2.text!!.clear()
    }

    @SuppressLint("Range")
    fun display(){
        val cursorResult = dbSql.getUsers()
        cursorResult?.let { cr ->
            if (cr.moveToFirst()) {
                do {
                    val name   = cr.getString(cr.getColumnIndex("name"))
                    val age    = cr.getString(cr.getColumnIndex("age"))
                    val idUser = cr.getString(cr.getColumnIndex("id"))
                    Log.e("DB","$name $age $idUser")
                } while (cr.moveToNext())
            }
        }
    }

    @SuppressLint("Range")
    fun deleteUser(){
        val cursorResult = dbSql.getSpecificUser(binding.etValue.text.toString(),
                binding.etValue2.text.toString())

        var idUser = 0
        cursorResult?.let { cr ->
            if (cr.moveToFirst()) {
                do {
                    idUser     = cr.getInt(cr.getColumnIndex("id"))
                    Log.e("DB","id from specific $idUser")
                    dbSql.deleteUser(idUser)
                } while (cr.moveToNext())
            }
        }
        binding.etValue.text!!.clear()
        binding.etValue2.text!!.clear()
    }

    @SuppressLint("Range")
    fun updateUser(){
        val cursorResult = dbSql.getSpecificUser(binding.etValue.text.toString(),
            binding.etValue2.text.toString())

        var idUser = 0
        cursorResult?.let { cr ->
            if (cr.moveToFirst()) {
                do {
                    val name   = cr.getString(cr.getColumnIndex("name"))
                    val age    = cr.getString(cr.getColumnIndex("age"))
                    idUser     = cr.getInt(cr.getColumnIndex("id"))
                    Log.e("DB","id from specific $idUser")
                    val success = dbSql.updateUser(name, age, idUser)
                    Log.e("DB","success $success")
                } while (cr.moveToNext())
            }
        }
        binding.etValue.text!!.clear()
        binding.etValue2.text!!.clear()
    }
}