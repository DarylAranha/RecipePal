package com.mdev.recipepal

import MyDatabaseHelper
import SharedViewModel
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class UserInformationFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_user_information, container, false)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)

        // Hide the BottomNavigationView
        bottomNavigationView.visibility = View.VISIBLE

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    findNavController().navigate(R.id.homeFragment)
                    true
                }
                R.id.userInformation -> {
                    findNavController().navigate(R.id.userInformationFragment)
                    true
                }
                else -> false
            }
        }

        val sharedPref = requireActivity().getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        val emailAddress = sharedPref.getString("email", "")

        val email = view.findViewById<EditText>(R.id.email_address)
        email.setText(emailAddress)

        // get the database instance
        val dbHelper = MyDatabaseHelper(requireContext())
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM userInformation WHERE email = ?", arrayOf(emailAddress))

        val columnIndex = cursor.getColumnIndex("email")
        if (columnIndex >= 0) {
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))

                    val nameField = view.findViewById<EditText>(R.id.name)
                    nameField.setText(name)

                    // Do something with the data
                } while (cursor.moveToNext())
            }
        }

        cursor.close()


        val submitButton = view.findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener{
            val db = dbHelper.writableDatabase

            val password = view.findViewById<EditText>(R.id.password).text.toString()
            val rePassword = view.findViewById<EditText>(R.id.re_password).text.toString()

            if (password == rePassword) {
                val values = ContentValues().apply {
                    put("password", password)
                }

                val selection = "${MyDatabaseHelper.COLUMN_EMAIL} = ?"
                val selectionArgs = arrayOf(emailAddress)

                val count = db.update(
                    MyDatabaseHelper.USER_INFO,
                    values,
                    selection,
                    selectionArgs
                )

                if (count > 0) {
                    Toast.makeText(requireContext(), "Password updated", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to update password", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(), "Password do not match", Toast.LENGTH_LONG).show()
            }

        }


        return view
    }
}