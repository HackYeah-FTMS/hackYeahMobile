package com.hackyeah.app.ui.projects.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hackyeah.app.R
import com.hackyeah.app.databinding.ViewProjectItemBinding
import kotlinx.android.extensions.LayoutContainer

class ViewHolderProject(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private var binding = ViewProjectItemBinding.bind(itemView)

    fun bind(
        projectTitle: String,
        projectDescription: String,
        projectImageUrl: String,
        projectCreationTimestamp: Long
    ) {
        setTitle(projectTitle)
        setDescription(projectDescription)
        setImage(projectImageUrl)
    }

    private fun setTitle(projectTitle: String) {
        binding.projectTitle.text = projectTitle
    }

    private fun setDescription(projectDescription: String) {
        binding.projectDescription.text = projectDescription
    }

    private fun setImage(projectImageUrl: String) {
        Glide
            .with(itemView.context)
            .load(projectImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(binding.projectItemImage)
    }

}