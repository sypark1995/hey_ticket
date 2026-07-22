package com.sypark.openTicket.view.fragments

import android.Manifest
import android.os.Build
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun init(view: View) {
        areaAdapter = RecommendAreaAdapter()
        binding.recyclerviewArea.apply {
            layoutManager = GridLayoutManager(view.context, 4)
            adapter = areaAdapter
        }
        areaAdapter.submitList(Common.areaList)
        areaAdapter.onSelectionChanged = { updateSelectionSummary() }

        genreAdapter = RecommendGenreAdapter()
        binding.recyclerviewGenre.apply {
            layoutManager = GridLayoutManager(view.context, 4)
            adapter = genreAdapter
        }
        genreAdapter.submitList(Common.genreList.filterNot { it.code == "ALL" })
        genreAdapter.onSelectionChanged = { updateSelectionSummary() }
        updateSelectionSummary()

        binding.btnRecommendComplete.setOnClickListener {
            lifecycleScope.launch {
                viewModel.saveSelections(genreAdapter.selectedCodes(), areaAdapter.selectedCodes())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
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

    private fun updateSelectionSummary() {
        binding.textSelectionSummary.text = getString(
            R.string.recommend_selector_summary,
            areaAdapter.selectedCodes().size,
            genreAdapter.selectedCodes().size
        )
    }
}
