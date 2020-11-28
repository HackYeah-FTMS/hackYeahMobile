package com.hackyeah.app.ui.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hackyeah.app.R
import com.hackyeah.app.data.model.Comment
import com.hackyeah.app.data.model.Project

class AdapterComments : RecyclerView.Adapter<ViewHolderComment>() {

    var commentList: List<Comment> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: (Int) -> (Unit) = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolderComment(
        LayoutInflater.from(parent.context).inflate(R.layout.view_comment_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolderComment, position: Int) {
        val comment = commentList[position]

        holder.bind(
            commentOwnerUserName = comment.owner.username,
            commentContent = comment.content,
            commentImageUrl = comment.image
        )
        holder.itemView.setOnClickListener {
            onClickListener(comment.id)
        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

}