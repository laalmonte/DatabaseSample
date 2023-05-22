package com.android.sample.databasesample.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                AGE_COL + " TEXT" + ")")

        db?.let {d ->
            d.execSQL(query)
            d.execSQL(("CREATE TABLE " + "roles" + " ("
                    + ID_COL + " INTEGER PRIMARY KEY, " +
                    "status"  + " TEXT," +
                    "isAdmin" + " TEXT" + ")"))
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val stringExec: String  = "DROP TABLE IF EXISTS $TABLE_NAME"
        val stringExec2: String = "DROP TABLE IF EXISTS roles"
        db!!.execSQL(stringExec)
        db!!.execSQL(stringExec2)
        onCreate(db)
    }

    fun addRole(status : String, isAdmin : String ){
        val values = ContentValues()
        values.put("status", status)
        values.put("isAdmin", isAdmin)
        val db = this.writableDatabase
        db.insert("roles", null, values)
        db.close()
    }

    fun getRoles(): Cursor? {
        val db = this.readableDatabase
        var queryString = "SELECT * FROM roles"
        return db.rawQuery(queryString, null)
    }

    fun addName(name : String, age : String ){
        val values = ContentValues()
        values.put(NAME_COl, name)
        values.put(AGE_COL, age)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteUser(idUser: Int){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_COL, idUser)
        db.delete(TABLE_NAME,"id="+ idUser,null)
        db.close()
    }

    fun getUsers(): Cursor? {
        val db = this.readableDatabase
        var queryString = "SELECT * FROM $TABLE_NAME "
        return db.rawQuery(queryString, null)
    }
    
    fun getSpecificUser(nameParam: String, ageParam: String): Cursor?{
        val db = this.readableDatabase
        var queryString = "SELECT * FROM $TABLE_NAME WHERE $NAME_COl = '$nameParam' AND $AGE_COL = '$ageParam'"
        return db.rawQuery(queryString, null)
    }

    fun updateUser(nameParam: String, ageParam: String, idParam: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_COL, idParam)
        contentValues.put(NAME_COl, nameParam + "1")
        contentValues.put(AGE_COL, ageParam + "1")

        val success = db.update(TABLE_NAME, contentValues,"id="+ idParam.toString(),null)
        db.close()
        return success
    }

    companion object{
        private val DATABASE_NAME    = "USER"
        private val DATABASE_VERSION = 2
        val TABLE_NAME               = "users"
        val ID_COL                   = "id"
        val NAME_COl                 = "name"
        val AGE_COL                  = "age"
    }

}
