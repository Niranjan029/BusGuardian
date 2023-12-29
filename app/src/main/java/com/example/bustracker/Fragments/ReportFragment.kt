package com.example.bustracker.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bustracker.PostItem
import com.example.bustracker.R
import com.example.bustracker.ReportAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_report.*

class ReportFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
lateinit var myReportSet:ArrayList<PostItem>
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_report, container, false)
      val  reportTextView = root.findViewById<TextView>(R.id.reportTv)
        reportTextView?.setOnClickListener{
            val showPopUp = ReportPopUpFragment()
            showPopUp.show((activity as AppCompatActivity).supportFragmentManager,"showPopUp")
        }
        myReportSet = ArrayList()
        val reportRv = root.findViewById<RecyclerView>(R.id.reportRv)
        val context = root.context
        val myAdapter = ReportAdapter(myReportSet, context)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.stackFromEnd = true
       reportRv.adapter = myAdapter
        reportRv.layoutManager = linearLayoutManager

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        FirebaseDatabase.getInstance().getReference("Reports").child(userId)
            .addValueEventListener(object:ValueEventListener
            {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.hasChild("reports"))
                    {
                        myReportSet.clear()
                        for (itemSnapshot in snapshot.child("reports").children) {
                            val text = itemSnapshot.getValue(String::class.java).toString()
                            if (text != null) {
                                val item = PostItem(text)
                                myReportSet.add(item)
                            }
                        }

                        myAdapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        return root
    }


}