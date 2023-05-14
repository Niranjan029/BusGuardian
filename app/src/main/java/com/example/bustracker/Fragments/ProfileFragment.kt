package com.example.bustracker.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bustracker.FirstPageActivity
import com.example.bustracker.R
import com.example.bustracker.StudentDatabaseTable
import com.example.bustracker.StudentListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
lateinit var phoneNumber:String
lateinit var studentData:StudentListModel

 var dbHandler:StudentDatabaseTable?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_profile, container, false)

        dbHandler = context?.let { StudentDatabaseTable(it) }
        FirebaseAuth.getInstance().currentUser?.let {
            FirebaseDatabase.getInstance().getReference("Student").child(
                it.uid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    phoneNumber = snapshot.child("phoneNumber").getValue(String::class.java).toString()
                    studentData = dbHandler!!.getStDetail(phoneNumber)
                    stName.text = studentData.studentNAME
                    stAddress.text = studentData.studentADDRESS
                    stPhoneNo.text = studentData.studentCONTACT
                    stclass.text = studentData.studentCLASS + " - " + studentData.studentSEC
                }

                override fun onCancelled(error: DatabaseError) {

                }
                })
        }
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.signout,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context,FirstPageActivity::class.java))

        }
        return super.onOptionsItemSelected(item)
    }
}