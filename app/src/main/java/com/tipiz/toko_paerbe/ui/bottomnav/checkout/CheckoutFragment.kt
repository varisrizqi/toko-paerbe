package com.tipiz.toko_paerbe.ui.bottomnav.checkout

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.core.data.network.data.fullfillmentbody.ItemsItemFillFull
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onLoading
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.databinding.FragmentCheckoutBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import com.tipiz.toko_paerbe.ui.utils.currency
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutFragment :
    BaseFragmentBottomNav<FragmentCheckoutBinding, CheckoutViewModel>(FragmentCheckoutBinding::inflate) {
    override val viewModel: CheckoutViewModel by viewModel()

    private val adapter by lazy {
        CheckoutAdapter()
    }

    override fun initView() {
        Log.d("checkout", "fragment create")
        val items = CheckoutFragmentArgs.fromBundle(arguments as Bundle).checkoutItems
        if (items != null) {
            viewModel.checkoutProduct.postValue(items.toList())
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }



    }


    override fun initViewModel() {

        binding.btnBuy.setOnClickListener {
            viewModel.fetchFulfillment()
            viewModel.responseFullFillMent.launchAndCollectIn(viewLifecycleOwner){state->
                state.onLoading {
                    binding.pbBar.visibility = View.VISIBLE
                    binding.scrollView.visibility = View.INVISIBLE

                }.onSuccess { data->
                    binding.scrollView.visibility = View.VISIBLE
                    binding.pbBar.visibility = View.INVISIBLE



                }
            }
        }


        viewModel.checkoutProduct.observe(viewLifecycleOwner) { chart ->
            var totalPay = 0
            val listItem = mutableListOf<ItemsItemFillFull>()
            chart?.forEach {
                totalPay += ((it.productPrice + it.variantPrice) * it.amount)
                binding.tvCheckoutTotalPay.text = currency(totalPay)
                showRv(chart)
                listItem.add(
                    ItemsItemFillFull(
                        quantity = it.amount,
                        productId = it.productId,
                        variantName = it.variantName
                    )
                )

            }
            viewModel.addItemToBuy(listItem)


        }


    }

    private fun showRv(data:List<DataCart>){
        binding.rvCheckout.itemAnimator = null
        binding.rvCheckout.adapter = adapter
        binding.rvCheckout.layoutManager = LinearLayoutManager(context)
        adapter.submitList(data)

    }
}
