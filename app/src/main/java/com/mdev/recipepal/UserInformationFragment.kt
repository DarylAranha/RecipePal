package com.mdev.recipepal

import SharedViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return view
    }
}