package com.ishmit.aisleassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ishmit.aisleassignment.R
import com.ishmit.aisleassignment.databinding.NotesItemBinding

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.InviteViewHolder>() {

    // List of profiles to display
    private var profilesList: List<Map<String, Any>> = emptyList()

    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InviteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NotesItemBinding.inflate(inflater, parent, false)
        return InviteViewHolder(binding)
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: InviteViewHolder, position: Int) {
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
    class InviteViewHolder(private val binding: NotesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // Bind profile data to the view holder
        fun bindData(profile: Map<String, Any>) {
            val firstName = profile["general_information"]?.let { (it as Map<*, *>)["first_name"] as? String }
            val age = profile["general_information"]?.let { (it as Map<*, *>)["age"] as? Double }
            binding.textName.text = "$firstName, ${age?.toInt()}"

            val photos = profile["photos"] as? List<Map<String, Any>>?
            if (!photos.isNullOrEmpty()) {
                val avatarUrl = photos.firstOrNull {
                    it["status"] == "avatar" && it["selected"] == true
                }?.get("photo") as? String
                Glide.with(binding.imageNotes).load(avatarUrl).into(binding.imageNotes)
            } else {
                Glide.with(binding.imageNotes).load(R.drawable.sample).into(binding.imageNotes)
            }
        }
    }
}
