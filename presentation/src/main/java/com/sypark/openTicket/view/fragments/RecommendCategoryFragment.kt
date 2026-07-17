package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRecommendCategoryBinding
import com.sypark.openTicket.model.RecommendCategoryViewModel
import com.sypark.openTicket.view.adapter.RecommendAreaAdapter
import com.sypark.openTicket.view.adapter.RecommendGenreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecommendCategoryFragment : BaseFragment<FragmentRecommendCategoryBinding>(R.layout.fragment_recommend_category) {

    private val viewModel: RecommendCategoryViewModel by viewModels()
    private lateinit var areaAdapter: RecommendAreaAdapter
    private lateinit var genreAdapter: RecommendGenreAdapter

    override fun init(view: View) {
        areaAdapter = RecommendAreaAdapter()
        binding.recyclerviewArea.apply {
            layoutManager = GridLayoutManager(view.context, 3)
            adapter = areaAdapter
        }
        areaAdapter.submitList(Common.areaList)

        genreAdapter = RecommendGenreAdapter()
        binding.recyclerviewGenre.apply {
            layoutManager = GridLayoutManager(view.context, 3)
            adapter = genreAdapter
        }
        genreAdapter.submitList(Common.genreList.filterNot { it.code == "ALL" })

        binding.btnRecommendComplete.setOnClickListener {
            lifecycleScope.launch {
                viewModel.saveSelections(genreAdapter.selectedCodes(), areaAdapter.selectedCodes())
                findNavController().popBackStack()
            }
        }

        viewModel.savedGenres.observe(viewLifecycleOwner) {
            genreAdapter.setSelectedCodes(it)
        }

        viewModel.savedAreas.observe(viewLifecycleOwner) {
            areaAdapter.setSelectedCodes(it)
        }

        lifecycleScope.launch {
            viewModel.loadSavedSelections()
        }
    }

    override fun backPressed() {
        findNavController().popBackStack()
    }
}
