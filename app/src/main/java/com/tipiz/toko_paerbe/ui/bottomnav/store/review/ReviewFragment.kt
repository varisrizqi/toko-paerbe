package com.tipiz.toko_paerbe.ui.bottomnav.store.review

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onLoading
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentReviewBinding
import com.tipiz.toko_paerbe.ui.bottomnav.store.StoreViewModel
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReviewFragment : BaseFragmentBottomNav<FragmentReviewBinding, StoreViewModel>(FragmentReviewBinding::inflate){
    override val viewModel: StoreViewModel by viewModel() //ktx
    override fun initView() {
        val review = ReviewFragmentArgs.fromBundle(requireArguments()).productId
        viewModel.showReviewProducts(review)

        binding.toolbar.title = getString(R.string.buyer_reviews)
        binding. toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initViewModel() {
        viewModel.responseReview.launchAndCollectIn(viewLifecycleOwner) { products ->
            products.onSuccess { data ->
                binding.pbBar.visibility = View.INVISIBLE
                binding.rvReview.visibility = View.VISIBLE
                setUp(data)
            }.onLoading {
                binding.pbBar.visibility = View.VISIBLE
                binding.rvReview.visibility = View.INVISIBLE
            }

        }
    }
    private fun setUp(review: List<DataReview>) {

        with(binding) {

            val adapter = ReviewAdapter()
            rvReview.adapter = adapter
            adapter.submitList(review)
            val layoutManager = LinearLayoutManager(context)
            rvReview.layoutManager = layoutManager
            rvReview.setHasFixedSize(true)
        }

    }
}