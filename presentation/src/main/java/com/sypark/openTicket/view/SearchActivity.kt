package com.sypark.openTicket.view

import android.os.Bundle
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseActivity
import com.sypark.openTicket.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this


    }
}