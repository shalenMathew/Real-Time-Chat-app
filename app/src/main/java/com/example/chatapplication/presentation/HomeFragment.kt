package com.example.chatapplication.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.R
import com.example.chatapplication.databinding.FragmentHomeBinding
import com.example.chatapplication.presentation.adapter.GroupChatAdapter
import com.example.chatapplication.utils.Result
import com.example.chatapplication.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var authToken: String? = null
    private var id: String? = null

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var groupChatAdapter: GroupChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authToken = arguments?.getString("authToken")
        id = arguments?.getString("id")

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupChatAdapter =  GroupChatAdapter { selectedGroup ->

            val bundle = Bundle().apply {
                putString("groupId", selectedGroup.group._id)
                putString("groupName", selectedGroup.group.name)
                putString("authToken", authToken)
                putString("id",id)
            }

            Log.d("TAG","id token from home- $id")
            Log.d("TAG","group id from home- ${selectedGroup.group._id}")

            findNavController().navigate(R.id.action_homeFragment_to_chatFragment, bundle)

        }

        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupChatAdapter
        }

        // Observe the LiveData from ViewModel
        chatViewModel.groupList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
//                    binding.progressBar.visibility = View.GONE
                    groupChatAdapter.submitList(result.data)
                }
                is Result.Error -> {
//                    binding.progressBar.visibility = View.GONE

                }
            }
        }

//        val token = arguments?.getString("authToken")
        Log.d("TAG","auth token from home- $authToken")
        chatViewModel.fetchGroups(authToken!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
