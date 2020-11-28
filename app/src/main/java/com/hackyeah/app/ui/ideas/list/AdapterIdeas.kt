package com.hackyeah.app.ui.ideas.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hackyeah.app.R
import com.hackyeah.app.data.model.Idea
import android.util.Log

class AdapterIdeas : RecyclerView.Adapter<ViewHolderIdea>() {

    var ideasList: List<Idea> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: (Int) -> (Unit) = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolderIdea(
        LayoutInflater.from(parent.context).inflate(R.layout.view_idea_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolderIdea, position: Int) {
        val idea = ideasList[position]

        holder.bind(
            ideaOwnerUserName = idea.owner.username,
            ideaImageUrl = idea.image,
            ideaDescription = idea.description
        )

        holder.itemView.setOnClickListener {
            onClickListener(idea.id)
        }

    }

    override fun getItemCount(): Int {
        return ideasList.size
    }

}