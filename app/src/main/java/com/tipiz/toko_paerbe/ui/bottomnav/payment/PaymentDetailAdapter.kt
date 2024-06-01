package com.tipiz.toko_paerbe.ui.bottomnav.payment

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.color.MaterialColors
import com.tipiz.core.domain.model.payment.ItemPayment
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemPaymentMethodDetailBinding

class PaymentDetailAdapter(private val selectPayment: (ItemPayment) -> Unit) :
    ListAdapter<ItemPayment, PaymentDetailAdapter.PaymentDetailViewHolder>(DIFF_FOLLBACK) {

    companion object {
        val DIFF_FOLLBACK = object : DiffUtil.ItemCallback<ItemPayment>() {
            override fun areItemsTheSame(oldItem: ItemPayment, newItem: ItemPayment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemPayment, newItem: ItemPayment): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class PaymentDetailViewHolder(private val binding: ItemPaymentMethodDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemPayment, context: Context) {
            with(binding) {
                Glide.with(context)
                    .load(data.image)
                    .error(R.drawable.thumbnail_load_product)
                    .into(ivItemPaymentDetailPartyLogo)
                itemView.alpha = if (data.status != false) 1f else 0.5f
                val color = MaterialColors.getColor(
                    context,
                    com.google.android.material.R.attr.colorSurfaceContainerHighest, Color.GRAY
                )
                if (data.status == false) {
                    itemView.setBackgroundColor(color)
                }
                itemView.isEnabled = data.status!!
                vgItemPaymentMethodDetail.isEnabled = data.status!!
                itemView.setOnClickListener {
                    selectPayment(data)
                }
                tvItemPaymentDetailPartyName.text = data.label
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentDetailViewHolder {
        val binding = ItemPaymentMethodDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentDetailViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, holder.itemView.context)

    }
}