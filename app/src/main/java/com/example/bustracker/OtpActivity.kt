package com.example.bustracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_otp.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {
    private lateinit var userNo:TextView
    val auth = FirebaseAuth.getInstance()
     private lateinit var options: PhoneAuthOptions
     private lateinit var phoneNumber:String
    private lateinit var phoneNumberwithoutccp:String
     private lateinit var storedVerificationId:String
     private lateinit var signin:Button
    private lateinit var database: DatabaseReference
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        supportActionBar?.hide()
         phoneNumber = intent.getStringExtra("phoneNoCcp").toString()
        phoneNumberwithoutccp = intent.getStringExtra("phoneNo").toString()
        initializeApp()
        userNo = findViewById(R.id.userNo)
        userNo.text = phoneNumber
        signin = findViewById(R.id.signin)
        database = FirebaseDatabase.getInstance().getReference("Student")
        signin.setOnClickListener {
                if(otp.text.toString().isEmpty())
                {
                    Toast.makeText(applicationContext, "Blank Field can not processed ",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            else if(otp.text.toString().length!=6)
                {
                    Toast.makeText(applicationContext, "Not valid otp ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            else
                {
                    val credential = PhoneAuthProvider.getCredential(storedVerificationId,otp.text.toString())
                    signInWithPhoneAuthCredential(credential)
                }
        }
    }

    private fun initializeApp()
    {
         options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {

                    if (e is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(applicationContext,"Invalid Code",Toast.LENGTH_SHORT).show()
                    } else if (e is FirebaseTooManyRequestsException) {
                         Toast.makeText(applicationContext,"Server error try after some time",Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    storedVerificationId = verificationId
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = task.result?.user

                    if (user != null) {
                        database.child(user.uid).child("phoneNumber").setValue(phoneNumberwithoutccp)
                    }
                    startActivity(Intent(this,MainActivity::class.java))


                } else {
                    Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()
                }
            }
    }

}
