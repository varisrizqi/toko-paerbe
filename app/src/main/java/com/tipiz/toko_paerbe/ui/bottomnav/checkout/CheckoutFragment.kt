package com.tipiz.toko_paerbe.ui.bottomnav.checkout

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tipiz.core.data.network.data.fullfillmentbody.ItemsItemFillFull
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.core.domain.model.fillfullment.DataFulFillMent
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onLoading
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentCheckoutBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import com.tipiz.toko_paerbe.ui.utils.Constant.PAYMENT_METHOD
import com.tipiz.toko_paerbe.ui.utils.Constant.SELECTED_PAYMENT
import com.tipiz.toko_paerbe.ui.utils.Constant.SELECTED_PAYMENT_LOGO
import com.tipiz.toko_paerbe.ui.utils.currency
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutFragment :
    BaseFragmentBottomNav<FragmentCheckoutBinding, CheckoutViewModel>(FragmentCheckoutBinding::inflate) {
    override val viewModel: CheckoutViewModel by viewModel()

    private val adapter by lazy {
        CheckoutAdapter()
    }

    override fun initView() {
        val items = CheckoutFragmentArgs.fromBundle(arguments as Bundle).checkoutItems
        if (items != null) {
            viewModel.checkoutProduct.postValue(items.toList())
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.clCheckoutPayment.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutFragment_to_paymentFragment)
        }

        initListener()


    }


    override fun initViewModel() {

        binding.btnBuy.setOnClickListener {
            viewModel.fetchFulfillment()
            viewModel.responseFullFillMent.launchAndCollectIn(viewLifecycleOwner){state->
                state.onLoading {
                    binding.pbBar.visibility = View.VISIBLE

                }.onSuccess { data->
                    binding.pbBar.visibility = View.INVISIBLE
                    moveToStatus(data)
                    viewModel.deleteAll()


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

        viewModel.fBody.observe(viewLifecycleOwner){
            binding.btnBuy.isEnabled = it.payment != null
        }

        viewModel.paymentMethod.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.tvChoosePaymentMethod.text = it[SELECTED_PAYMENT]
                Glide.with(requireContext())
                    .load(it[SELECTED_PAYMENT_LOGO])
                    .error(R.drawable.ic_add_card)
                    .placeholder(R.drawable.ic_add_card)
                    .into(binding.imgPayment)
            }

        }




    }

    private fun initListener() {
        // Payment method listener
        setFragmentResultListener(PAYMENT_METHOD) { _, bundle ->
            viewModel.addPaymentMethod(bundle.getString(SELECTED_PAYMENT))
            viewModel.paymentMethod.postValue(
                hashMapOf(
                    SELECTED_PAYMENT to bundle.getString(SELECTED_PAYMENT),
                    SELECTED_PAYMENT_LOGO to bundle.getString(SELECTED_PAYMENT_LOGO)
                )
            )
        }
    }

    private fun showRv(data:List<DataCart>){
        binding.rvCheckout.itemAnimator = null
        binding.rvCheckout.adapter = adapter
        binding.rvCheckout.layoutManager = LinearLayoutManager(context)
        adapter.submitList(data)
    }

    private fun moveToStatus(fulfillment: DataFulFillMent){
       val p = fulfillment.invoiceId
        Log.d("PAYMENTDEBUG", "invoice $p")



    }
}
