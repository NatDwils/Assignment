package com.ishmit.aisleassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ishmit.aisleassignment.R
import com.ishmit.aisleassignment.databinding.InviteItemLayoutBinding

class InvitesAdapter : RecyclerView.Adapter<InvitesAdapter.InviteViewHolder>() {

    private var profilesList: List<Map<String, Any>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InviteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InviteItemLayoutBinding.inflate(inflater, parent, false)
        return InviteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InviteViewHolder, position: Int) {
        val profile = profilesList[position]
        holder.bindData(profile)
    }

    override fun getItemCount(): Int = profilesList.size

    fun submitList(profilesList: List<*>) {
        this.profilesList = profilesList.filterIsInstance<Map<String, Any>>()
        notifyDataSetChanged()
    }

    class InviteViewHolder(private val binding: InviteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(profile: Map<String, Any>) {
            val firstName =profile["general_information"]?.let { (it as Map<*, *>)["first_name"] as? String }
            val age = profile["general_information"]?.let { (it as Map<*, *>)["age"] as? Double }
            binding.textFirstNameAndAge.text = "$firstName, ${age?.toInt()}"

            val photos = profile["photos"] as? List<Map<String, Any>>?
            if (!photos.isNullOrEmpty()) {
                val avatarUrl = photos.firstOrNull {
                    it["status"] == "avatar" && it["selected"] == true
                }?.get("photo") as? String
                Glide.with(binding.imageInvite).load(avatarUrl).into(binding.imageInvite)
            } else {
                Glide.with(binding.imageInvite).load(R.drawable.sample).into(binding.imageInvite)
            }
        }
    }
}
