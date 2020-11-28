package com.hackyeah.app.ui.projects.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hackyeah.app.BaseApplication
import com.hackyeah.app.di.viewmodels.ViewModelProviderFactory
import com.hackyeah.app.ui.base.BaseFragment
import javax.inject.Inject
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hackyeah.app.R
import com.hackyeah.app.databinding.FragmentProjectDetailsBinding
import com.hackyeah.app.ui.projects.ViewModelProjects

class FragmentProjectDetails : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentProjectDetailsBinding? = null
    private val binding get() = _binding!!

    private val arguments: FragmentProjectDetailsArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private val viewModelProjects: ViewModelProjects by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(ViewModelProjects::class.java)
    }

    override fun inject() {
        (context?.applicationContext as BaseApplication)
            .appComponent
            .inject(this)
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProjectDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val projectById = viewModelProjects.getProjectById(arguments.projectId)
        if(projectById == null){
            // TODO handle error
            return
        }

        setTitle(projectById.title)
        setDescription(projectById.description)
        setSolutionImage(projectById.solutionImage)
        setIdeaImage(projectById.ideaImage)
    }

    private fun setTitle(title: String) {
        binding.projectTitle.text = title
    }

    private fun setDescription(description: String) {
        binding.projectDescription.text = description
    }

    private fun setSolutionImage(imageUrl: String) {
        Glide
            .with(requireContext())
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(binding.projectSolutionImage)
    }

    private fun setIdeaImage(imageUrl: String) {
        Glide
            .with(requireContext())
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(binding.projectIdeaImage)
    }

    override fun onClick(v: View) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}