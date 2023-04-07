package com.mdev.recipepal

import MyDatabaseHelper
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
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)

        // Hide the BottomNavigationView
        bottomNavigationView.visibility = View.GONE

        val dbHelper = MyDatabaseHelper(requireContext())
        val db = dbHelper.readableDatabase

        val loginButton = view.findViewById<Button>(R.id.btn_login)
        loginButton.setOnClickListener {
            val emailAddress = view.findViewById<EditText>(R.id.email_address).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()

            val columns = arrayOf(MyDatabaseHelper.COLUMN_PASSWORD)
            val selection = "${MyDatabaseHelper.COLUMN_EMAIL} = ?"
            val selectionArgs = arrayOf(emailAddress)
            val cursor = db.query(
                MyDatabaseHelper.USER_INFO,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            if (cursor.moveToFirst()) {
                val passwordIndex = cursor.getColumnIndex(MyDatabaseHelper.COLUMN_PASSWORD)
                if (passwordIndex >= 0) {
                    val passwordFromDatabase = cursor.getString(passwordIndex)

                    if (passwordFromDatabase == password) {
                        // navigate to user information screen
                        // view.findNavController().navigate(R.id.action_loginFragment_to_userInformationFragment)

//                        val action = LoginFragmentDirections.actionLoginFragmentToUserInformationFragment(emailAddress)
//                        view.findNavController().navigate(action)

                        view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), "Invalid User", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "User do not exist", Toast.LENGTH_LONG).show()
            }
            cursor.close()

            db.close()
        }

        val signUpButton = view.findViewById<Button>(R.id.btn_register)
        signUpButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return view
    }
}