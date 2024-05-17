package com.tipiz.toko_paerbe.ui.bottomnav.wishlist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipiz.core.domain.model.favorite.DataFavorite
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemWishlistGridBinding
import com.tipiz.toko_paerbe.databinding.ItemWishlistLinearBinding
import com.tipiz.toko_paerbe.ui.utils.currency

class WishlistListAdapter(
    private val context: Context,
    val deleteItem: (DataFavorite) -> Unit,
    val addChart: (DataFavorite) -> Unit
) : ListAdapter<DataFavorite, RecyclerView.ViewHolder>(ProductDiffCallBack()) {

    private var isGridLayout: Boolean = false
    fun setLayoutType(isGrid: Boolean) {
        isGridLayout = isGrid
    }

    private class ProductDiffCallBack : DiffUtil.ItemCallback<DataFavorite>() {
        override fun areItemsTheSame(oldItem: DataFavorite, newItem: DataFavorite): Boolean =
            oldItem.productId == newItem.productId

        override fun areContentsTheSame(oldItem: DataFavorite, newItem: DataFavorite): Boolean =
            oldItem == newItem
    }

    inner class ViewHolderGrid(private val binding: ItemWishlistGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataFavorite) {
            with(binding) {
                cvWishlistGrid.startAnimation(AnimationUtils.loadAnimation(itemView.context, R.anim.anim_one))
                tvItemWishlistGridProductName.text = data.productName
                tvItemWishlistGridPrice.text = currency(data.productPrice + data.variantPrice)
                tvItemWishlistGridStoreName.text = data.store
                tvItemWishlistGridRatingSold.text =
                    context.getString(R.string.sold)
                        .replace("%5.0%", data.productRating.toString())
                        .replace("%10%", data.sale.toString())
                Glide.with(context)
                    .load(data.image)
                    .placeholder(R.drawable.thumbnail_load_product)
                    .error(R.drawable.thumbnail_load_product)
                    .into(ivItemWishlistGridImg)
                btnItemWishlistGridDelete.setOnClickListener {
                    deleteItem(data)
                }
                btnItemWishlistGridAddChart.setOnClickListener {
                    addChart(data)
                }
            }
        }
    }

    inner class ViewHolderLinear(private val binding: ItemWishlistLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataFavorite) {
          Log.e("varis", "chip ${data.setChip}")
            with(binding) {
                cvWishlistLinear.startAnimation(AnimationUtils.loadAnimation(itemView.context, R.anim.anim_one))
                tvItemWishlistLinearProductName.text = data.productName
                tvItemWishlistLinearPrice.text = currency(data.productPrice + data.variantPrice)
                tvItemWishlistLinearStoreName.text = data.store
                tvItemWishlistLinearRatingSold.text =
                    context.getString(R.string.sold)
                        .replace("%5.0%", data.productRating.toString())
                        .replace("%10%", data.sale.toString())
                Glide.with(context)
                    .load(data.image)
                    .placeholder(R.drawable.thumbnail_load_product)
                    .error(R.drawable.thumbnail_load_product)
                    .into(ivItemWishlistLinearImg)
                btnItemWishlistLinearDelete.setOnClickListener {
                    deleteItem(data)
                }
                btnItemWishlistLinearAddChart.setOnClickListener {
                    addChart(data)
                }
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (isGridLayout) {
            false -> {
                val binding = ItemWishlistLinearBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewHolderLinear(binding)
            }

            true -> {
                val binding = ItemWishlistGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewHolderGrid(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        when (isGridLayout) {
            false -> {
                val viewHolderLinear = holder as ViewHolderLinear
                viewHolderLinear.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(
                        data
                    )
                }
                viewHolderLinear.bind(data)
            }

            true -> {
                val viewHolderGrid = holder as ViewHolderGrid
                viewHolderGrid.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
                viewHolderGrid.bind(data)
            }
        }
    }

}