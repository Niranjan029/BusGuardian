package com.example.bustracker

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseTable(context:Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {


    companion object{
       private const val DB_NAME  = "StudentDB"
       private  const val DB_VERSION = 1
       private const val TABLE_NAME = "StudentTB"
       private const val Student_ID = "Student_Id"
       private const val Student_NAME = "Student_NAME"
       private const val Student_CLASS = "Student_CLASS"
       private const val Student_SEC = "Student_SEC"
       private const val Student_ADDRESS = "Student_ADDRESS"
       private const val Student_CONTACT = "Student_CONTACT"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME($Student_ID INTEGER PRIMARY KEY,$Student_NAME TEXT ," +
                            "$Student_CLASS TEXT , $Student_SEC TEXT, $Student_ADDRESS TEXT , $Student_CONTACT TEXT );"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    @SuppressLint("Range")
    fun getALlDetails():List<StudentListModel>{
        val studentList = ArrayList<StudentListModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst())
        {
            do{
                val stList = StudentListModel();
                stList.studentID = Integer.parseInt(cursor.getColumnIndex(Student_ID).toString())
                stList.studentNAME = cursor.getString(cursor.getColumnIndex(Student_NAME))
                stList.studentCLASS = cursor.getString(cursor.getColumnIndex(Student_CLASS))
                stList.studentSEC = cursor.getString(cursor.getColumnIndex(Student_SEC))
                stList.studentADDRESS = cursor.getString(cursor.getColumnIndex(Student_ADDRESS))
                stList.studentCONTACT = cursor.getString(cursor.getColumnIndex(Student_CONTACT))
                studentList.add(stList)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return studentList
    }

    //insert
    fun addDetails(stList:StudentListModel):Boolean
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Student_ID,stList.studentID)
        values.put(Student_NAME,stList.studentNAME)
        values.put(Student_CLASS,stList.studentCLASS)
        values.put(Student_SEC,stList.studentSEC)
        values.put(Student_ADDRESS,stList.studentADDRESS)
        values.put(Student_CONTACT,stList.studentCONTACT)

        val _success = db.insert(TABLE_NAME,null,values)
        db.close()
        return (Integer.parseInt("$_success")!=-1)
    }

    // single user data
    @SuppressLint("Range")
    fun getStDetail(_id:Int):StudentListModel{
        val stList = StudentListModel();
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $Student_ID = $_id"
        val cursor = db.rawQuery(selectQuery,null)
        cursor?.moveToFirst()
        stList.studentID = Integer.parseInt(cursor.getColumnIndex(Student_ID).toString())
        stList.studentNAME = cursor.getString(cursor.getColumnIndex(Student_NAME))
        stList.studentCLASS = cursor.getString(cursor.getColumnIndex(Student_CLASS))
        stList.studentSEC = cursor.getString(cursor.getColumnIndex(Student_SEC))
        stList.studentADDRESS = cursor.getString(cursor.getColumnIndex(Student_ADDRESS))
        stList.studentCONTACT = cursor.getString(cursor.getColumnIndex(Student_CONTACT))
        cursor.close()
        db.close()
        return stList
    }

}