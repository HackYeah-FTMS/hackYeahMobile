package com.hackyeah.app.ui.projects

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.hackyeah.app.BaseApplication
import com.hackyeah.app.databinding.FragmentProjectsBinding
import com.hackyeah.app.di.viewmodels.ViewModelProviderFactory
import com.hackyeah.app.ui.base.BaseFragment
import javax.inject.Inject
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackyeah.app.ui.projects.list.AdapterProjects

class FragmentProjects : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentProjectsBinding.inflate(inflater, container, false)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNewProject.setOnClickListener(this)
        binding.projectRecyclerView.adapter = AdapterProjects()
        binding.projectRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapterProjects = binding.projectRecyclerView.adapter as AdapterProjects
        adapterProjects.onClickListener = {
            Navigation.findNavController(requireView()).navigate(
                FragmentProjectsDirections.actionFragmentProjectsToFragmentProjectDetails(it)
            )
        }

        viewModelProjects
            .getProjects()
            .observe(viewLifecycleOwner, {
                adapterProjects.projectList = it
            })
    }

    override fun onClick(v: View) {
        when(v.id){
            binding.buttonNewProject.id ->{
                Navigation.findNavController(requireView()).navigate(
                    FragmentProjectsDirections.actionFragmentProjectsToFragmentNewProject()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}