package com.tipiz.toko_paerbe.ui.bottomnav.transaction

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.tipiz.core.domain.model.fillfullment.DataFulFillMent
import com.tipiz.core.domain.model.transaction.DataTransaction
import com.tipiz.core.utils.DataMapper.toDataFulFillMent
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onError
import com.tipiz.core.utils.state.onLoading
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentTransactionBinding
import com.tipiz.toko_paerbe.ui.bottomnav.dashboard.DashBoardFragmentDirections
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionFragment : BaseFragmentBottomNav<FragmentTransactionBinding, TransactionViewModel>(
    FragmentTransactionBinding::inflate
) {
    override val viewModel: TransactionViewModel by viewModel()
    private val analytics: FirebaseAnalytics by inject()

    override fun initView() {
        binding.btnTransactionRefresh.setOnClickListener {
            viewModel.fetchTransaction()
        }
    }

    override fun initViewModel() {
        with(viewModel) {

            responseTransaction.launchAndCollectIn(viewLifecycleOwner) { uiState ->
                uiState.onLoading {
                    binding.pgTransaction.visibility = View.VISIBLE
                    showError(false)
                }.onSuccess { data ->
                    println("varis transac data: ${data.size}")
                    binding.pgTransaction.visibility = View.GONE
                    showError(false)
                    setUpTransaction(data)
                    if (data.isEmpty()) {
                        println("varis transac 0 data: ${data.size}")
                        showError(true)
                        binding.tvTransactionErrorCode.text =
                            resources.getString(R.string.http_exception_404_code)
                        binding.tvTransactionErrorMessage.text =
                            resources.getString(R.string.http_exception_404_message)
                    }
                }.onError {
                    showError(true)
                    binding.pgTransaction.visibility = View.GONE
                    binding.tvTransactionErrorCode.text =
                        resources.getString(R.string.http_exception_404_code)
                    binding.tvTransactionErrorMessage.text =
                        resources.getString(R.string.http_exception_404_message)
                }
            }
        }
    }

    private fun setUpTransaction(data: List<DataTransaction>) {
        val adapter = TransactionAdapter(moveToStatus = {
            moveToStatus(it.toDataFulFillMent())
        })

        binding.rvTransaction.adapter = adapter
        binding.rvTransaction.layoutManager = LinearLayoutManager(context)
        adapter.submitList(data.reversed())


    }

    private fun moveToStatus(fulfillment: DataFulFillMent) {
        Log.d("NavController", "Current destination: ${findNavController().currentDestination?.id}")
        val args = DashBoardFragmentDirections.actionDashBoardFragmentToStatusFragment(
            fulfillment.invoiceId,
            fulfillment.status,
            fulfillment.date,
            fulfillment.total,
            fulfillment.payment,
            fulfillment.time,
            fulfillment,
            fulfillment.review ?: "",
            fulfillment.rating ?: 0
        )
        // Akses NavController melalui activity
        activity?.supportFragmentManager?.findFragmentById(R.id.container_main_nav_host)
            ?.findNavController()?.navigate(args)
    }

    private fun showError(state: Boolean) {
        binding.ivTransactionError.isVisible = state
        binding.tvTransactionErrorCode.isVisible = state
        binding.tvTransactionErrorMessage.isVisible = state
        binding.btnTransactionRefresh.isVisible = state
    }
}