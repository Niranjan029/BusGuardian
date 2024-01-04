package com.example.bustracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    var dbHandler:StudentDatabaseTable?=null
    var userList:List<StudentListModel> = ArrayList<StudentListModel>()
    lateinit var userId: EditText
    lateinit var userNo :EditText
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()

        dbHandler = StudentDatabaseTable(this)
        secretcode()
        fetchData()
        userId = findViewById(R.id.registrationNoEt)
        userNo = findViewById(R.id.editTextNumberSigned)
        val ccp:CountryCodePicker = findViewById(R.id.countyCodePicker)
        nextbtn.setOnClickListener {

            val text = userId.text.trim().toString()
            val mobileNo = userNo.text.trim()
                if((text != "") && (mobileNo.length == 10)) {
                    for (it in userList) {
                            Log.i("idid",it.studentID.toString() + " " + it.studentNAME)
                        if(mobileNo.toString() == it.studentCONTACT && text == it.studentNAME)
                        {
                            ccp.registerCarrierNumberEditText(userNo)
                            val intent:Intent = Intent(this, OtpActivity::class.java)
                            intent.putExtra("phoneNoCcp",ccp.fullNumberWithPlus.trim())
                            intent.putExtra("phoneNo",mobileNo.toString())
                            startActivity(intent)
                            break
                        }
                    }
                }
            else
                {
                    userId.error = "Please enter registered name"
                    userNo.error = "please enter valid 10 digit mobile no"
                }
        }

    }

    private fun fetchData()
    {
        userList = dbHandler!!.getALlDetails()
        for (i in userList) {
            Log.i("data", i.studentNAME + " " + i.studentCONTACT);
        }
    }

    private fun secretcode()
    {
        var success:Boolean=false;
        val stData:StudentListModel = StudentListModel()
        stData.studentID = 20321
        stData.studentNAME = "Yash Pamecha"
        stData.studentCLASS="12"
        stData.studentSEC = "A"
        stData.studentCONTACT = "7869265789"
        stData.studentADDRESS = "jaipur,Rajasthan"
        success = dbHandler?.addDetails(stData) as Boolean
        var _success:Boolean=false;
        val _stData:StudentListModel = StudentListModel()
        _stData.studentID = 20342
        _stData.studentNAME = "Sahil Sharma"
        _stData.studentCLASS="12"
        _stData.studentSEC = "A"
        _stData.studentCONTACT = "7878002440"
        _stData.studentADDRESS = "jaipur ,Rajasthan"
        _success = dbHandler?.addDetails(_stData) as Boolean
        var __success:Boolean=false;
        val __stData:StudentListModel = StudentListModel()
        __stData.studentID = 20331
        __stData.studentNAME = "Niranjan Patidar"
        __stData.studentCLASS="12"
       __stData.studentSEC = "A"
        __stData.studentCONTACT = "8349122392"
        __stData.studentADDRESS = "jaipur ,Rajasthan"
        __success = dbHandler?.addDetails(__stData) as Boolean


        var success1:Boolean=false;
        val stData1:StudentListModel = StudentListModel()
        stData1.studentID = 20339
        stData1.studentNAME = "Akshat Mewara"
        stData1.studentCLASS="12"
        stData1.studentSEC = "A"
        stData1.studentCONTACT = "6378522323"
        stData1.studentADDRESS = "jaipur,Rajasthan"
        success1 = dbHandler?.addDetails(stData1) as Boolean
    }
}