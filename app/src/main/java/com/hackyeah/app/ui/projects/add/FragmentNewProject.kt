package com.hackyeah.app.ui.projects.add

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hackyeah.app.BaseApplication
import com.hackyeah.app.R
import com.hackyeah.app.databinding.FragmentNewProjectBinding
import com.hackyeah.app.di.viewmodels.ViewModelProviderFactory
import com.hackyeah.app.ui.base.BaseFragment
import com.hackyeah.app.ui.projects.ViewModelProjects
import javax.inject.Inject

class FragmentNewProject : BaseFragment(), View.OnClickListener {

    private val REQUEST_PHOTO_SELECT_CODE = 7312

    private var _binding: FragmentNewProjectBinding? = null
    private val binding get() = _binding!!

    private var chosenPhotoContainerId: Int? = null

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
        _binding = FragmentNewProjectBinding.inflate(inflater, container, false)
//        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ideaImage.setOnClickListener(this)
        binding.solutionImage.setOnClickListener(this)
        binding.createButton.setOnClickListener(this)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.ideaImage.id -> {
                showDialog(binding.ideaImage.id)
            }
            binding.solutionImage.id -> {
                showDialog(binding.solutionImage.id)
            }
            binding.createButton.id -> {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_PHOTO_SELECT_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data?.data
                    loadPhotoToImageView(uri)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialog(imageId: Int) {
        MaterialDialog(requireContext())
            .title(text = "Dodaj zdjecie!")
            .message(text = "Wybierz zdjecie z galerii lub zrób zdjęcie!")
            .show {
                positiveButton(text = "Zdjecie") {

                }
                negativeButton(text = "Galeria") {
                    showFileChooser(imageId)
                }
            }
    }

    private fun showFileChooser(imageId: Int) {
        val intent = Intent(
            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        try {
            chosenPhotoContainerId = imageId
            startActivityForResult(
                Intent.createChooser(intent, "Wybierz zdjęcie"),
                REQUEST_PHOTO_SELECT_CODE
            )
        } catch (ex: ActivityNotFoundException) {
            showToastMessage(
                getString(R.string.error_something_went_wrong)
            )
        }
    }

    private fun loadPhotoToImageView(uri: Uri?) {
        if (uri == null || chosenPhotoContainerId == null) {
            return
        }

        val imageView = activity?.findViewById<ImageView>(chosenPhotoContainerId!!) ?: return

        Glide
            .with(requireContext())
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(imageView)

        chosenPhotoContainerId = null
    }

}