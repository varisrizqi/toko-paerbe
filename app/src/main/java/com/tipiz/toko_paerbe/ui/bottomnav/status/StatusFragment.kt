package com.tipiz.toko_paerbe.ui.bottomnav.status

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.tipiz.core.domain.model.fillfullment.DataFulFillMent
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onLoading
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentStatusBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import com.tipiz.toko_paerbe.ui.utils.currency
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatusFragment :
    BaseFragmentBottomNav<FragmentStatusBinding, StatusViewModel>(FragmentStatusBinding::inflate) {
    override val viewModel: StatusViewModel by viewModel()

    override fun initView() {
        val resultData = StatusFragmentArgs.fromBundle(arguments as Bundle).data

        binding.tvTransactionIdValue.text = resultData.invoiceId
        binding.tvStatusIdValue.text = if (resultData.status) {
            getString(R.string.succeed)
        } else {
            getString(R.string.failed)
        }
        binding.tvDateIdValue.text = resultData.date
        binding.tvTimeIdValue.text = resultData.time
        binding.tvTotalPaymentIdValue.text = currency(resultData.total)
        binding.tvPaymentMethodIdValue.text = resultData.payment



        //handle back button
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                        val nav = StatusFragmentDirections.actionStatusFragmentToDashBoardFragment()
                        findNavController().navigate(nav)
                }
            })
    }

    override fun initViewModel() {
        val resultData = StatusFragmentArgs.fromBundle(arguments as Bundle).data

        with(viewModel) {

            binding.btnDone.setOnClickListener {

                responseRating.launchAndCollectIn(viewLifecycleOwner) { state ->
                    state.onSuccess { data ->
                        Log.d("StatusFragment", "-> $ratingBody")
                        val nav = StatusFragmentDirections.actionStatusFragmentToDashBoardFragment()
                        findNavController().navigate(nav)
                    }.onLoading {
                        binding.pgBar.visibility = View.VISIBLE
                        binding.btnDone.visibility = View.INVISIBLE
                    }
                }

                // Handle done button click
                handleDoneButtonClick(resultData)

            }

        }
    }

    private fun handleDoneButtonClick(resultData: DataFulFillMent) {

        with(viewModel) {
            ratingBody.invoiceId = resultData.invoiceId
            ratingBody.review = binding.edText.text.toString()
            ratingBody.rating = binding.rtbStatus.rating.toInt()
            fetchStatus()
        }
    }
}