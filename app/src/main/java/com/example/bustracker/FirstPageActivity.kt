package com.example.bustracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_first_page.*

class FirstPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)
        supportActionBar?.hide()

        nextBtn.setOnClickListener(View.OnClickListener {
            val intent: Intent  = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)

        })
    }
}