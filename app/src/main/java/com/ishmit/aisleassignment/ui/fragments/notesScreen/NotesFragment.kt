package com.ishmit.aisleassignment.ui.fragments.notesScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ishmit.aisleassignment.databinding.FragmentNotesBinding
import com.ishmit.aisleassignment.ui.adapters.NotesAdapter
import com.ishmit.aisleassignment.ui.adapters.ProfileAdapter
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import com.ishmit.aisleassignment.utils.gone
import com.ishmit.aisleassignment.utils.visible
import com.ishmit.aisleassignment.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private val notesViewModel: NotesViewModel by viewModel()
    private val invitesAdapter = NotesAdapter()
    private val likesAdapter = ProfileAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using data binding
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve arguments from navigation
        val args = NotesFragmentArgs.fromBundle(requireArguments())
        val authToken = args.token

        // Set up RecyclerViews and Adapters
        binding.notesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = invitesAdapter
        }

        binding.profileRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = likesAdapter
        }

        // Observe notesResponse LiveData
        notesViewModel.notesResponse.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ResponseRequest.Loading -> {
                    binding.notesProgressBar.visible()
                    binding.profileProgressBar.visible()
                }
                is ResponseRequest.Success -> {
                    binding.notesProgressBar.gone()
                    binding.profileProgressBar.gone()
                    val notesResponse = uiState.data
                    val invites = notesResponse?.invites
                    val likes = notesResponse?.likes
                    invites?.let {
                        val profiles = it["profiles"] as? List<Map<String, Any>>
                        profiles?.let { profilesList ->
                            invitesAdapter.submitList(profilesList)
                        }
                    }
                    likes?.let {
                        val profiles = it["profiles"] as? List<Map<String, Any>>
                        profiles?.let { profilesList ->
                            likesAdapter.submitList(profilesList)
                        }
                    }
                }
                is ResponseRequest.Failure -> {
                    showToast(uiState.error)
                    binding.notesProgressBar.gone()
                    binding.profileProgressBar.gone()
                }
            }
        }

        // Call the getNotes function with the auth token
        notesViewModel.getNotes(authToken)
    }
}
