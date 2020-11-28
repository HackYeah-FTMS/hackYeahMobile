package com.hackyeah.app.ui.ideas

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
import com.hackyeah.app.databinding.FragmentIdeasBinding
import com.hackyeah.app.ui.ideas.list.AdapterIdeas
import com.hackyeah.app.ui.main.MainActivity
import com.hackyeah.app.ui.projects.FragmentProjectsDirections
import com.hackyeah.app.ui.projects.ViewModelProjects
import com.hackyeah.app.ui.projects.list.AdapterProjects

class FragmentIdeas : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentIdeasBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentIdeasBinding.inflate(inflater, container, false)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).showBottomNavigation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ideasRecyclerView.adapter = AdapterIdeas()
        binding.ideasRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapterProjects = binding.ideasRecyclerView.adapter as AdapterIdeas
        adapterProjects.onClickListener = {
//            Navigation.findNavController(requireView()).navigate(
//                FragmentProjectsDirections.actionFragmentProjectsToFragmentProjectDetails(it)
//            )
        }

        viewModelIdeas
            .getIdeas()
            .observe(viewLifecycleOwner, {
                adapterProjects.ideasList = it
            })
    }


    override fun onClick(v: View) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}