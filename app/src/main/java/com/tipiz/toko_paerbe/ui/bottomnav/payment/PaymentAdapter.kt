package com.tipiz.toko_paerbe.ui.bottomnav.payment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tipiz.core.domain.model.payment.DataPayment
import com.tipiz.core.domain.model.payment.ItemPayment
import com.tipiz.toko_paerbe.databinding.ItemPaymentMethodBinding

class PaymentAdapter(private val selectPayment: (ItemPayment) -> Unit) :
    ListAdapter<DataPayment, PaymentAdapter.PaymentViewHolder>(DIFF_FOLLBACK) {

    companion object {
        val DIFF_FOLLBACK = object : DiffUtil.ItemCallback<DataPayment>() {
            override fun areItemsTheSame(oldItem: DataPayment, newItem: DataPayment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataPayment, newItem: DataPayment): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class PaymentViewHolder(private val binding: ItemPaymentMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataPayment, context: Context) {
            with(binding) {
                tvItemPaymentMethodTitle.text = data.title
                val adapter = PaymentDetailAdapter(selectPayment = { data ->
                    selectPayment(data)
                })
                rvItemPaymentMethod.adapter = adapter
                rvItemPaymentMethod.layoutManager = LinearLayoutManager(context)
                adapter.submitList(data.item)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding =
            ItemPaymentMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, holder.itemView.context)
    }
}