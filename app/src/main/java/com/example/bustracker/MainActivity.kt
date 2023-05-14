package com.example.bustracker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bustracker.Fragments.HomeFragment
import com.example.bustracker.Fragments.NotificationFragment
import com.example.bustracker.Fragments.ProfileFragment
import com.example.bustracker.Fragments.ReportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment()
        val notifFragment = NotificationFragment()
        val reportFragment = ReportFragment()
        val profileFragment = ProfileFragment()
        makeCurrentFragment(homeFragment)
        bottom_nav.selectedItemId = R.id.home_item
        supportActionBar!!.title = "Home"
        bottom_nav.setOnItemSelectedListener{
            when (it.itemId)
            {
                R.id.home_item->{
                    makeCurrentFragment(homeFragment)
                    supportActionBar!!.title = "Home"
                }
                R.id.profile_item-> {
                    makeCurrentFragment(profileFragment)
                    supportActionBar!!.title = "Profile"
                }
                R.id.report_item-> {
                    makeCurrentFragment(reportFragment)
                    supportActionBar!!.title = "Report"
                }
                R.id.notif_item-> {
                    makeCurrentFragment(notifFragment)
                    supportActionBar!!.title = "Notification"
                }
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }
    }


}


