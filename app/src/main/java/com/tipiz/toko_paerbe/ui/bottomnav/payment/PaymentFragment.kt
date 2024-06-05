package com.tipiz.toko_paerbe.ui.bottomnav.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.gson.Gson
import com.tipiz.core.data.network.data.payment.PaymentResponse
import com.tipiz.core.domain.model.payment.ItemPayment
import com.tipiz.core.utils.DataMapper.toUiDataPayment
import com.tipiz.toko_paerbe.databinding.FragmentPaymentBinding
import com.tipiz.toko_paerbe.ui.utils.Constant.FIREBASE_KEY
import com.tipiz.toko_paerbe.ui.utils.Constant.PAYMENT_METHOD
import com.tipiz.toko_paerbe.ui.utils.Constant.SELECTED_PAYMENT
import com.tipiz.toko_paerbe.ui.utils.Constant.SELECTED_PAYMENT_LOGO
import org.koin.android.ext.android.inject


class PaymentFragment : Fragment() {

    private lateinit var adapter: PaymentAdapter
    private lateinit var binding: FragmentPaymentBinding
    private val remoteConfig: FirebaseRemoteConfig by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        adapter = PaymentAdapter { data ->
            sendData(data)
        }
        binding.rvPaymentTva.layoutManager = LinearLayoutManager(context)
        binding.rvPaymentTva.adapter = adapter
        setDataRemoteConfig()

    }

    private fun setDataRemoteConfig() {
        val paymentMethod = remoteConfig.getString(FIREBASE_KEY)
        showPayments(paymentMethod)
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (configUpdate.updatedKeys.contains(FIREBASE_KEY)) {
                    remoteConfig.activate().addOnCompleteListener {
                        val paymentMethodNew = remoteConfig.getString(FIREBASE_KEY)
                        showPayments(paymentMethodNew)
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {

            }
        })

    }

    private fun showPayments(payments: String) {
        val myPayments = Gson().fromJson(payments, PaymentResponse::class.java)
        Log.d("PAYMENTDEBUG", "$myPayments")
        val uiDataPayments = myPayments.data.map { it.toUiDataPayment() }
        adapter.submitList(uiDataPayments)
    }

    private fun sendData(data: ItemPayment) {
        setFragmentResult(
            PAYMENT_METHOD, bundleOf(
                SELECTED_PAYMENT to data.label,
                SELECTED_PAYMENT_LOGO to data.image

            )
        )
        findNavController().popBackStack()

    }
}
