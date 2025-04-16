package com.example.chatapplication.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapplication.R
import com.example.chatapplication.databinding.FragmentProfileBinding
import com.example.chatapplication.viewmodel.LoginViewModel
import com.example.chatapplication.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.example.chatapplication.utils.Result


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    private var authToken: String? = null
    private var id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authToken = arguments?.getString("authToken")
        id = arguments?.getString("id")

        Log.d("TAG","auth token from profile- $authToken")

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar2.visibility = View.GONE

        binding.createProfileButton.setOnClickListener {

            val firstname = binding.firstNameEditText.text.toString()
            val lastname = binding.lastNameEditText.text.toString()

            profileViewModel.updateUserProfile(authToken!!,firstname,lastname)
        }

        if (authToken!=null){


            profileViewModel.profileUpdateState.observe(viewLifecycleOwner) { result ->

                when (result) {
                    is Result.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar2.visibility = View.GONE
                        val message = result.message
                        if (!message.isNullOrBlank()) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show()
                        }

                        val bundle = Bundle().apply {
                            putString("authToken", authToken)
                            putString("id",id)
                        }
                        findNavController().navigate(R.id.action_profileFragment_to_homeFragment,bundle)
                    }
                    is Result.Error -> {
                        binding.progressBar2.visibility = View.GONE
                        Log.d("TAG", "Profile update failed: ${result.message}")
                        val message = result.message
                        if (!message.isNullOrBlank()) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Profile update failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


        }else
        {
            Toast.makeText(context, "Auth Token is null", Toast.LENGTH_SHORT).show()
            binding.progressBar2.visibility = View.GONE
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
