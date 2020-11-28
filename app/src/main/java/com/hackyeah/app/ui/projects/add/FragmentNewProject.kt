package com.hackyeah.app.ui.projects.add

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.TransitionDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hackyeah.app.BaseApplication
import com.hackyeah.app.R
import com.hackyeah.app.data.Status
import com.hackyeah.app.databinding.FragmentNewProjectBinding
import com.hackyeah.app.di.viewmodels.ViewModelProviderFactory
import com.hackyeah.app.ui.base.BaseFragment
import com.hackyeah.app.ui.main.MainActivity
import com.hackyeah.app.ui.projects.ViewModelProjects
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
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

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ideaImage.setOnClickListener(this)
        binding.solutionImage.setOnClickListener(this)
        binding.createButton.setOnClickListener(this)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        observeNetworkState()
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
                viewModelProjects.setLoading()
                val ideaFile = bitmapToFile(
                    (binding.ideaImage.drawable as TransitionDrawable).toBitmap(),
                    binding.ideaImage.drawable.toBitmap().generationId.toString()
                )
                val solutionFile = bitmapToFile(
                    (binding.solutionImage.drawable as TransitionDrawable).toBitmap(),
                    binding.solutionImage.drawable.toBitmap().generationId.toString()
                )

                if (ideaFile == null || solutionFile == null) {
                    return
                }

                viewModelProjects
                    .addProject(
                        binding.projectTitle.text.toString(),
                        binding.projectDescription.text.toString(),
                        ideaFile,
                        solutionFile
                    )
            }
        }
    }

    private fun observeNetworkState() {
        viewModelProjects.resetNetworkState()
        viewModelProjects
            .networkState
            .observe(viewLifecycleOwner, { networkState ->

                val mainActivity = requireActivity() as MainActivity
                when (networkState.status) {
                    Status.LOADING -> {
                        mainActivity.showHUD()
                    }
                    Status.SUCCESS -> {
                        mainActivity.hideHUD()
                        findNavController().navigateUp()
                    }
                    Status.FAILED -> {
                        mainActivity.hideHUD()
                        findNavController().navigateUp()
                    }
                }
            })
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
            .asDrawable()
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(imageView)

        chosenPhotoContainerId = null
    }

    private fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? {
        var file: File? = null
        return try {
            file = File(
                requireContext().getExternalFilesDir(null)?.absolutePath + File.separator + fileNameToSave
            )
            file.createNewFile()

            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata = bos.toByteArray()

            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file
        }
    }
}