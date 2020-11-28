package com.hackyeah.app.ui.ideas.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hackyeah.app.R
import com.hackyeah.app.databinding.ViewIdeaItemBinding
import kotlinx.android.extensions.LayoutContainer

class ViewHolderIdea(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private var binding = ViewIdeaItemBinding.bind(itemView)

    fun bind(
        ideaOwnerUserName: String,
        ideaDescription: String,
        ideaImageUrl: String,
    ) {
        setOwnerName(ideaOwnerUserName)
        setDescription(ideaDescription)
        setImage(ideaImageUrl)
    }

    private fun setOwnerName(ideaOwnerUserName: String) {
        binding.userName.text = ideaOwnerUserName
    }

    private fun setDescription(projectDescription: String) {
        binding.ideaDescription.text = projectDescription
    }

    private fun setImage(projectImageUrl: String) {
        Glide
            .with(itemView.context)
            .load(projectImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(binding.ideaItemImage)
    }

}