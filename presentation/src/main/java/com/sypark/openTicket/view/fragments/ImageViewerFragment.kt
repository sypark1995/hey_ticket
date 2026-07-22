package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentImageViewerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewerFragment :
    BaseFragment<FragmentImageViewerBinding>(R.layout.fragment_image_viewer) {

    private val args by navArgs<ImageViewerFragmentArgs>()

    override fun init(view: View) {
        Glide.with(this).load(args.imageUrl).into(binding.imgViewerPoster)

        binding.imgViewerClose.setOnClickListener {
            backPressed()
        }
    }

    override fun backPressed() {
        findNavController().popBackStack()
    }
}
