package com.sypark.openTicket

import android.os.Bundle
import com.sypark.openTicket.base.BaseActivity
import com.sypark.openTicket.databinding.ActivityMain2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMain2Binding>(R.layout.activity_main2) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

    }

}