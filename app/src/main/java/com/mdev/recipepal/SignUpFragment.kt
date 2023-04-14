package com.mdev.recipepal

import MyDatabaseHelper
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class SignUpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)

        // Hide the BottomNavigationView
        bottomNavigationView.visibility = View.GONE

        // get the database instance
        val dbHelper = MyDatabaseHelper(requireContext())
        val db = dbHelper.writableDatabase

        // get user inputs
        // check if the user input is present (use email address for uniqueness)
        // if present show toast message
        // if not present create entry and redirect to next page
        val signUpButton = view.findViewById<Button>(R.id.btn_signup)
        signUpButton.setOnClickListener {
            val name = view.findViewById<EditText>(R.id.name).text.toString()
            val emailAddress = view.findViewById<EditText>(R.id.email).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()
            val confirmPassword = view.findViewById<EditText>(R.id.confirm_password).text.toString()

            Toast.makeText(requireContext(), "Password do not match", Toast.LENGTH_LONG).show()

            if (password != confirmPassword) {
                // show toast message
                Toast.makeText(requireContext(), "Password do not match", Toast.LENGTH_LONG).show()
            } else {
                val values = ContentValues().apply {
                    put("name", name)
                    put("email", emailAddress)
                    put("password", password)
                }

                db.insert("userInformation", null, values)
                db.close()

                // navigate to user information page
//                val action = SignUpFragmentDirections.actionSignUpFragmentToUserInformationFragment(emailAddress)
//                view.findNavController().navigate(action)

                val sharedPref = requireActivity().getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()

                editor.putString("email", emailAddress)
                editor.apply()

                view.findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
            }
        }

        return view
    }
}