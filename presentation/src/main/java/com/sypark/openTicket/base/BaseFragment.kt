package com.sypark.openTicket.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sypark.openTicket.R
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutResId: Int) : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        view.findViewById<BottomNavigationView>(R.id.navigationBottom)?.run {

//            if (null != initNavButtonId()) {
//                this.selectedItemId = initNavButtonId()!!
//            }

            setOnItemSelectedListener {
                val fragmentId = when (it.itemId) {
                    R.id.menu_home -> {
                        R.id.mainFragment
                    }
                    R.id.menu_category -> {
                        R.id.categoryFragment
                    }
                    R.id.menu_my -> {
                        R.id.mainFragment
                    }
                    else -> {
                        return@setOnItemSelectedListener false
                    }
                }

                findNavController().navigate(fragmentId)
                return@setOnItemSelectedListener true
            }


        }
        init(view)
    }

    protected abstract fun init(view: View)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected open fun initNavButtonId(): Int? = null
}