package com.ishmit.aisleassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ishmit.aisleassignment.databinding.ProfileItemBinding

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.LikeViewHolder>() {

    private var profilesList: List<Map<String, Any>> = emptyList()

    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProfileItemBinding.inflate(inflater, parent, false)
        return LikeViewHolder(binding)
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        val profile = profilesList[position]
        holder.bindData(profile)
    }

    // Get item count
    override fun getItemCount(): Int = profilesList.size

    // Submit new list of profiles
    fun submitList(profilesList: List<*>) {
        this.profilesList = profilesList.filterIsInstance<Map<String, Any>>()
        notifyDataSetChanged()
    }

    // ViewHolder class
    class LikeViewHolder(private val binding: ProfileItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // Bind profile data to the view holder
        fun bindData(profile: Map<String, Any>) {
            val firstName = profile["first_name"] as? String
            val avatarUrl = profile["avatar"] as? String

            // Load profile avatar using Glide
            Glide.with(binding.imageProfile).load(avatarUrl).into(binding.imageProfile)

            // Set the profile name
            binding.textName.text = firstName
        }
    }
}
