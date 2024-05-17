package com.tipiz.toko_paerbe.ui.bottomnav.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemCartBinding
import com.tipiz.toko_paerbe.ui.utils.currency

class CartAdapter(
    private val deleteItem: (DataCart) -> Unit,
    private val increaseCount: (DataCart) -> Unit,
    private val decreaseCount: (DataCart) -> Unit,
    private val checkItem: (DataCart) -> Unit
) : ListAdapter<DataCart, CartAdapter.CartLinearAdapter>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataCart>() {
            override fun areItemsTheSame(
                oldItem: DataCart,
                newItem: DataCart
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataCart,
                newItem: DataCart
            ): Boolean {
                return oldItem.productId == newItem.productId
            }
        }
    }

    inner class CartLinearAdapter(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataCart, context: Context) {
            with(binding) {
                cbItemChartCheck.isChecked = data.isChecked
                Glide.with(context)
                    .load(data.image)
                    .placeholder(R.drawable.thumbnail_load_product)
                    .error(R.drawable.thumbnail_load_product)
                    .into(ivItemChartImgProduct)
                tvItemChartVariant.text = data.variantName
                tvItemChartProductName.text = data.productName
                Log.e("CartAdapter", "Adapter productname ${data.productName}")
                tvItemChartStock.text = if (data.stock < 10) {
                    tvItemChartStock.setTextColor(context.getColor(R.color.red_splash))
                    context.getString(R.string._5_left)
                        .replace("%5%", data.stock.toString())
                } else {
                    context.getString(R.string.stock)
                        .replace("%5%", data.stock.toString())
                }

                tvItemChartPrice.text = currency((data.productPrice + data.variantPrice) * data.amount)
                tvItemChartTotalItems.text = data.amount.toString()

                cbItemChartCheck.setOnClickListener {
                    checkItem(data)
                }
                ivItemChartDelete.setOnClickListener{
                    deleteItem(data)
                }
                btnItemChartDelete.setOnClickListener {
                    decreaseCount(data)
                }
                btnItemChartAdd.setOnClickListener {
                    increaseCount(data)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartLinearAdapter {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartLinearAdapter(binding)
    }

    override fun onBindViewHolder(holder: CartLinearAdapter, position: Int) {
        val data = getItem(position)
        holder as CartLinearAdapter
        holder.bind(data,holder.itemView.context)

    }
}