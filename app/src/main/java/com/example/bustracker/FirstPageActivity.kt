package com.example.bustracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_first_page.*

class FirstPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)
        supportActionBar?.hide()
        if(FirebaseAuth.getInstance().currentUser!=null)
        {
            val intent: Intent  = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        nextBtn.setOnClickListener(View.OnClickListener {
            val intent: Intent  = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)

        })
    }
}