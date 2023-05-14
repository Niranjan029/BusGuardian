package com.example.bustracker.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.bustracker.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_report_pop_up.*

class ReportPopUpFragment : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_report_pop_up, container, false)

        val reportText = root.findViewById<EditText>(R.id.reportEditText)
        val reportBtn = root.findViewById<Button>(R.id.reportBtn)

       reportBtn.setOnClickListener{
           val text =    reportText.text.toString().trim()
            if(text.length<20)
            {
                Toast.makeText(activity,"please describe the report properly .",Toast.LENGTH_SHORT).show()
            }
            else
            {
                val id = FirebaseAuth.getInstance().currentUser?.uid
                if(id!=null)
                {
                    FirebaseDatabase.getInstance().getReference("Reports")
                        .child(id).child("reports").push().setValue(text)
                    Toast.makeText(activity,"Report added",Toast.LENGTH_SHORT).show()
                    reportText.setText("")
                }

            }
        }

   return root
    }


}