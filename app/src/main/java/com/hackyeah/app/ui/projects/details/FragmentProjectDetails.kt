package com.hackyeah.app.ui.projects.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hackyeah.app.BaseApplication
import com.hackyeah.app.R
import com.hackyeah.app.databinding.FragmentProjectDetailsBinding
import com.hackyeah.app.di.viewmodels.ViewModelProviderFactory
import com.hackyeah.app.ui.base.BaseFragment
import com.hackyeah.app.ui.main.MainActivity
import com.hackyeah.app.ui.projects.ViewModelProjects
import javax.inject.Inject

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

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).hideBottomNavigation()

        DrawableCompat.setTint(
            DrawableCompat.wrap(binding.pointsImage.drawable),
            ContextCompat.getColor(requireContext(), R.color.color_grey_text)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.pointsWrapper.setOnClickListener(this)

        val projectById = viewModelProjects.getProjectById(arguments.projectId)
        if (projectById == null) {
            // TODO handle error
            return
        }

        setTitle(projectById.title)
        setDescription(projectById.description)
        setSolutionImage(projectById.solutionImage)
        setIdeaImage(projectById.ideaImage)
        setPoints(projectById.points)
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

    private fun setPoints(points: Int) {
        binding.pointsCounter.text = points.toString()
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.pointsWrapper.id -> {
                binding.pointsCounter.setTextColor(resources.getColor(R.color.color_blue_dark))
                binding.pointsCounter.text =
                    (Integer.parseInt(binding.pointsCounter.text.toString()) + 1).toString()

                DrawableCompat.setTint(
                    DrawableCompat.wrap(binding.pointsImage.drawable),
                    ContextCompat.getColor(requireContext(), R.color.color_blue_dark)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}