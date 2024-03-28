package com.tipiz.toko_paerbe.ui.bottomnav.store.review

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentReviewBinding
import com.tipiz.toko_paerbe.ui.bottomnav.store.StoreViewModel
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import com.tipiz.toko_paerbe.ui.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReviewFragment : BaseFragment<FragmentReviewBinding,StoreViewModel>(FragmentReviewBinding::inflate){
    override val viewModel: StoreViewModel by viewModel() //ktx
    override fun initView() {
        val review = arguments?.getString(Constant.extra_detail)
        viewModel.showReviewProducts(review ?: "")
    }

    override fun initViewModel() {
        viewModel.responseReview.launchAndCollectIn(viewLifecycleOwner) { products ->
            products.onSuccess { data ->
                setUp(data)
            }

        }
    }
    private fun setUp(review: List<DataReview>) {

        with(binding) {
            toolbar.title = getString(R.string.buyer_reviews)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            val adapter = ReviewAdapter()
            rvReview.adapter = adapter
            adapter.submitList(review)
            val layoutManager = LinearLayoutManager(context)
            rvReview.layoutManager = layoutManager
            rvReview.setHasFixedSize(true)
        }


    }
}