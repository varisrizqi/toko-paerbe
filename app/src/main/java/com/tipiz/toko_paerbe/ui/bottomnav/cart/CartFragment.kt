package com.tipiz.toko_paerbe.ui.bottomnav.cart

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.toko_paerbe.databinding.FragmentCartBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import com.tipiz.toko_paerbe.ui.utils.currency
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment :
    BaseFragmentBottomNav<FragmentCartBinding, CartViewModel>(FragmentCartBinding::inflate) {
    override val viewModel: CartViewModel by viewModel()
    private lateinit var adapter: CartAdapter

    override fun initView() {
        with(binding) {
            adapter = CartAdapter(
                increaseCount = { increaseChart(it) },
                deleteItem = { deleteItemChart(it.productId) },
                decreaseCount = { decreaseChart(it) },
                checkItem = { checkItem(it) })
            rvChart.itemAnimator = null
            rvChart.layoutManager = LinearLayoutManager(context)
            rvChart.adapter = adapter

            btnChartBuy.setOnClickListener {

            }

            btnChartEraseAll.setOnClickListener {
                viewModel.deleteAllChecked()
            }

            cbChartCheckAll.setOnClickListener {
                viewModel.allCheck(cbChartCheckAll.isChecked)
            }

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

    override fun initViewModel() {
        with(viewModel) {
            getAllChart.observe(viewLifecycleOwner) { data ->
                showError(data.isEmpty())
                showCart(data)
                val isCheckTotal = data.filter { it.isChecked }.size
                if (isCheckTotal > 0){
                    binding.btnChartEraseAll.visibility = View.VISIBLE
                    binding.btnChartBuy.isEnabled = true
                }else{
                    binding.btnChartEraseAll.visibility = View.GONE
                    binding.btnChartBuy.isEnabled = false
                }
                binding.cbChartCheckAll.isChecked = isCheckTotal == data.size
                data.forEach {
                    Log.e("Database", "chart ${it.productId}")
                    Log.e("Database", "chart $it")

                }
            }


        }
    }

    // Functions for adapter
    private fun increaseChart(data: DataCart) {
        viewModel.updateCart(data, true)
    }

    private fun decreaseChart(data: DataCart) {
        viewModel.updateCart(data, false)
    }

    private fun deleteItemChart(id: String) {
        viewModel.deleteItemChart(id)
    }

    private fun checkItem(data: DataCart) {
        data.isChecked = !data.isChecked
        viewModel.updateIsChecked(data.productId, data.isChecked)
    }

    private fun showCart(data: List<DataCart>){
        adapter.submitList(data)
        var totalPay = 0
        viewModel.checkoutItems.clear()
        data.forEach {
            if (it.isChecked){
                totalPay += (it.productPrice+it.variantPrice) * it.amount
                viewModel.checkoutItems.add(it)

            }
            binding.tvChartTotalPay.text = currency(totalPay)
        }



    }

    private fun showError(state: Boolean) {
        // Visible view
        binding.ivChartError.isVisible = state
        binding.tvChartErrorCode.isVisible = state
        binding.tvChartErrorMessage.isVisible = state
        // Invisible view
        binding.rvChart.isVisible = !state
        binding.dvdrChart1.isVisible = !state
        binding.cbChartCheckAll.isVisible = !state
        binding.tvChooseAll.isVisible = !state
        binding.btnChartEraseAll.isVisible = !state
        binding.dvdrChart2.isVisible = !state
        binding.tvChartTotalPay.isVisible = !state
        binding.tvChartTotalTitle.isVisible = !state
        binding.btnChartBuy.isVisible = !state
    }
}