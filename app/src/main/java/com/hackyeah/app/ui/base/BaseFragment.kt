package com.hackyeah.app.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {


    abstract fun inject()

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun showToastMessage(msg: String) {
        Toast
            .makeText(
                requireContext(),
                msg,
                Toast.LENGTH_LONG
            ).show()
    }
}