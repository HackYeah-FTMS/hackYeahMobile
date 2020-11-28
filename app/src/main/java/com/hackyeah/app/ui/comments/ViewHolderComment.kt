package com.hackyeah.app.ui.comments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hackyeah.app.R
import com.hackyeah.app.databinding.ViewCommentItemBinding
import kotlinx.android.extensions.LayoutContainer

class ViewHolderComment(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private var binding = ViewCommentItemBinding.bind(itemView)

    fun bind(
        commentContent: String,
        commentOwnerUserName: String,
        commentImageUrl: String?,
    ) {
        setCommentOwnerName(commentOwnerUserName)
        setCommentContent(commentContent)
        setImage(commentImageUrl)
    }

    private fun setCommentOwnerName(commentOwnerUserName: String) {
        binding.commentOwnerUserName.text = commentOwnerUserName
    }

    private fun setCommentContent(commentContent: String) {
        binding.commentContent.text = commentContent
    }

    private fun setImage(commentImageUrl: String?) {
        Glide
            .with(itemView.context)
            .load(commentImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(binding.commentImage)
    }

}