package com.hackyeah.app.ui.ideas.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hackyeah.app.BaseApplication
import com.hackyeah.app.R
import com.hackyeah.app.data.model.Comment
import com.hackyeah.app.databinding.FragmentIdeaDetailsBinding
import com.hackyeah.app.di.viewmodels.ViewModelProviderFactory
import com.hackyeah.app.ui.base.BaseFragment
import com.hackyeah.app.ui.comments.AdapterComments
import com.hackyeah.app.ui.ideas.ViewModelIdeas
import com.hackyeah.app.ui.main.MainActivity
import javax.inject.Inject

class FragmentIdeaDetails : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentIdeaDetailsBinding? = null
    private val binding get() = _binding!!

    private val arguments: FragmentIdeaDetailsArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private val viewModelIdeas: ViewModelIdeas by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(ViewModelIdeas::class.java)
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
        _binding = FragmentIdeaDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val ideaId = arguments.ideaId

        if (ideaId == 0) {
            return
        }

        viewModelIdeas
            .getIdeaById(ideaId)
            .observe(viewLifecycleOwner, { ideaDetail ->
                setDescription(ideaDetail.description)
                setImage(ideaDetail.image)

                setCommentSection(ideaDetail.comments)
            })
    }

    private fun setCommentSection(commentsList: List<Comment>) {
        if (commentsList.isNullOrEmpty()) {
            binding.commentSection.visibility = View.GONE
            return
        }

        binding.commentList.adapter = AdapterComments()
        binding.commentList.layoutManager = LinearLayoutManager(requireContext())
        (binding.commentList.adapter as AdapterComments).commentList = commentsList
    }

    private fun setDescription(description: String) {
        binding.ideaDescription.text = description
    }

    private fun setImage(imageUrl: String) {
        Glide
            .with(requireContext())
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.image_placeholder)
            .into(binding.ideaImage)
    }

    override fun onClick(v: View) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}