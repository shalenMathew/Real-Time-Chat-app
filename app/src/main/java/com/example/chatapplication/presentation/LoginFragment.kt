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
import com.example.chatapplication.databinding.FragmentLoginBinding
import com.example.chatapplication.utils.Result
import com.example.chatapplication.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach



@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.GONE

        loginViewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if (result.data!=null){

                        val token = result.data.resources.data.authToken
                        val id = result.data.resources.data._id

                        Log.d("TAG","auth token - $token")

                        val bundle = Bundle().apply {
                            putString("authToken", token)
                            putString("id",id)
                        }

                        findNavController().navigate(R.id.action_loginFragment_to_profileFragment,bundle)
                    }else{
                        Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                        Log.d("TAG","Error: ${result.message}")
                    }

                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                    Log.d("TAG","Error: ${result.message}")
                }
            }
        }


        binding.loginLoginButton.setOnClickListener {

            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.VISIBLE

            val mobileNumber = binding.loginMobileNo.text.toString().toLongOrNull()
            if (mobileNumber != null) {
                loginViewModel.loginUser(mobileNumber)
            } else {
                Toast.makeText(context, "Invalid mobile number", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}