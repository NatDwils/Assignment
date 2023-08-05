package com.ishmit.aisleassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ishmit.aisleassignment.databinding.LikeItemLayoutBinding

class LikesAdapter : RecyclerView.Adapter<LikesAdapter.LikeViewHolder>() {

    private var profilesList: List<Map<String, Any>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LikeItemLayoutBinding.inflate(inflater, parent, false)
        return LikeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        val profile = profilesList[position]
        holder.bindData(profile)
    }

    override fun getItemCount(): Int = profilesList.size

    fun submitList(profilesList: List<*>) {
        this.profilesList = profilesList.filterIsInstance<Map<String, Any>>()
        notifyDataSetChanged()
    }

    class LikeViewHolder(private val binding: LikeItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(profile: Map<String, Any>) {

            val firstName = profile["first_name"] as? String
            val avatarUrl = profile["avatar"] as? String

             Glide.with(binding.imageLike).load(avatarUrl).into(binding.imageLike)

            binding.textLikeName.text = firstName
        }
    }
}
