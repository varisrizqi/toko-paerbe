package com.tipiz.toko_paerbe.ui.bottomnav.checkout

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.tipiz.core.data.network.data.fullfillmentbody.ItemsItemFillFull
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.core.domain.model.fillfullment.DataFulFillMent
import com.tipiz.core.domain.model.firebase.Notification
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onError
import com.tipiz.core.utils.state.onLoading
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentCheckoutBinding
import com.tipiz.toko_paerbe.ui.MainActivity
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import com.tipiz.toko_paerbe.ui.utils.Constant
import com.tipiz.toko_paerbe.ui.utils.Constant.PAYMENT_METHOD
import com.tipiz.toko_paerbe.ui.utils.Constant.SELECTED_PAYMENT
import com.tipiz.toko_paerbe.ui.utils.Constant.SELECTED_PAYMENT_LOGO
import com.tipiz.toko_paerbe.ui.utils.currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
        with(viewModel) {
            binding.btnBuy.setOnClickListener {

                viewModel.fetchFulfillment()
                viewModel.responseFullFillMent.launchAndCollectIn(viewLifecycleOwner) { state ->
                    state.onLoading {
                        binding.pbBar.visibility = View.VISIBLE
                        binding.btnBuy.visibility = View.INVISIBLE

                    }.onSuccess { data ->
                        binding.pbBar.visibility = View.INVISIBLE
                        moveToStatus(fulfillment = data)
                        viewModel.deleteAll()

                        val total = currency(data.total)
                        notif.type = "Success"
                        notif.title = "Pesanan Berhasil!"
                        notif.body =
                            "Pembayaran berhasil dengan invoice ${data.invoiceId}. melalui ${data.payment} dengan jumlah $total."
                        notif.date = data.date
                        notif.time = data.time

                        CoroutineScope(Dispatchers.IO).launch {
                            insertNotify()
                            delay(1000)
                            val lastData = getAllNotification().first()
                            sendNotification(lastData[lastData.lastIndex])
                        }


                    }.onError {
                        Snackbar.make(binding.root, it.message!!, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }


            }


            viewModel.checkoutProduct.observe(viewLifecycleOwner) { chart ->
                var totalPay = 0
                val listItem = mutableListOf<ItemsItemFillFull>()
                chart?.forEach {
                    showRv(chart)
                    totalPay += ((it.productPrice + it.variantPrice) * it.amount)
                    binding.tvCheckoutTotalPay.text = currency(totalPay)
                    listItem.add(
                        ItemsItemFillFull(
                            quantity = it.amount,
                            productId = it.productId,
                            variantName = it.variantName
                        )
                    )
                    notif.image = it.image
                }
                viewModel.addItemToBuy(listItem)
            }

            viewModel.fBody.observe(viewLifecycleOwner) {
                binding.btnBuy.isEnabled = it.payment != null
            }

            viewModel.paymentMethod.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.tvChoosePaymentMethod.text = it[SELECTED_PAYMENT]
                    Glide.with(requireContext())
                        .load(it[SELECTED_PAYMENT_LOGO])
                        .error(R.drawable.ic_add_card)
                        .placeholder(R.drawable.ic_add_card)
                        .into(binding.imgPayment)
                }

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

    private fun showRv(data: List<DataCart>) {
        binding.rvCheckout.itemAnimator = null
        binding.rvCheckout.adapter = adapter
        binding.rvCheckout.layoutManager = LinearLayoutManager(context)
        adapter.submitList(data)
    }

    private fun moveToStatus(fulfillment: DataFulFillMent) {
        val args = CheckoutFragmentDirections.actionCheckoutFragmentToStatusFragment(
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
        findNavController().navigate(args)
    }

    private fun sendNotification(data: Notification) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(requireContext(), Constant.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(data.title)
            .setContentText(data.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(createContentIntent())
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constant.CHANNEL_ID,
                Constant.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notificationID = data.id
        notificationManager.notify(notificationID, notificationBuilder.build())

    }

    private fun createContentIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("destination", R.id.notificationFragment)
        }
        return PendingIntent.getActivity(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

}
