package com.hackyeah.app.ui.projects.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hackyeah.app.R
import com.hackyeah.app.data.model.Project

class AdapterProjects : RecyclerView.Adapter<ViewHolderProject>() {

    var projectList: List<Project> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: (Int) -> (Unit) = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolderProject(
        LayoutInflater.from(parent.context).inflate(R.layout.view_project_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolderProject, position: Int) {
        val project = projectList[position]

        holder.bind(
            projectTitle = project.title,
            projectImageUrl = project.ideaImage,
            projectDescription = project.description,
            projectCreationTimestamp = project.creationTimestamp,
        )
        holder.itemView.setOnClickListener {
            onClickListener(project.id)
        }
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

}