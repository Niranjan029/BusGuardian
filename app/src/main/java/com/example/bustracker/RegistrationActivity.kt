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

    private fun fetchData(){
        userList = dbHandler!!.getALlDetails()
    }

    private fun secretcode()
    {
//        var success:Boolean=false;
//        val stData:StudentListModel = StudentListModel()
//        stData.studentID = 1270
//        stData.studentNAME = "pappu Bisleri"
//        stData.studentCLASS="5"
//        stData.studentSEC = "A"
//        stData.studentCONTACT = "8908908900"
//        stData.studentADDRESS = "vassii ,mumbai"
//        success = dbHandler?.addDetails(stData) as Boolean
        var _success:Boolean=false;
        val _stData:StudentListModel = StudentListModel()
        _stData.studentID = 1400
        _stData.studentNAME = "Sushant singh"
        _stData.studentCLASS="12"
        _stData.studentSEC = "A"
        _stData.studentCONTACT = "8349122392"
        _stData.studentADDRESS = "ranchi ,jharkhand"
        _success = dbHandler?.addDetails(_stData) as Boolean
    }
}