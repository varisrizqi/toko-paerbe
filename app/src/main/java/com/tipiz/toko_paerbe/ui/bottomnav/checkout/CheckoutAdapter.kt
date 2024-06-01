package com.tipiz.toko_paerbe.ui.bottomnav.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemCheckoutBinding
import com.tipiz.toko_paerbe.ui.utils.currency

class CheckoutAdapter : ListAdapter<DataCart, CheckoutAdapter.CheckoutLayout>(DIFF_FOLLBACK) {

    companion object {
        val DIFF_FOLLBACK = object : DiffUtil.ItemCallback<DataCart>() {
            override fun areItemsTheSame(oldItem: DataCart, newItem: DataCart): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: DataCart, newItem: DataCart): Boolean {
                return oldItem == newItem
            }
        }
    }

    class CheckoutLayout(private val binding: ItemCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataCart, context: Context) {
            with(binding) {
                Glide.with(context)
                    .load(data.image)
                    .placeholder(R.drawable.thumbnail_load_product)
                    .error(R.drawable.thumbnail_load_product)
                    .into(ivItemChartImgProduct)

                tvItemChartVariant.text = data.variantName
                tvItemChartProductName.text = data.productName
                tvItemChartPrice.text =
                    currency((data.productPrice + data.variantPrice) * data.amount)
                tvItemChartStock.text = if (data.stock < 10) {
                    tvItemChartStock.setTextColor(context.getColor(R.color.red_splash))
                    context.getString(R.string._5_left)
                        .replace("%5%", data.stock.toString())
                } else {
                    context.getString(R.string.stock)
                        .replace("%5%", data.stock.toString())
                }

                tvStock.text = context.getString(R.string._1x).replace("%1%", data.amount.toString())
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutLayout {
        val binding =
            ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckoutLayout(binding)
    }

    override fun onBindViewHolder(holder: CheckoutLayout, position: Int) {

        val data = getItem(position)
        holder.bind(data, holder.itemView.context)
    }

}