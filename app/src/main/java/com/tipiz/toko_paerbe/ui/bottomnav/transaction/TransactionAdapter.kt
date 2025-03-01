package com.tipiz.toko_paerbe.ui.bottomnav.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipiz.core.domain.model.transaction.DataTransaction
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemTransactionBinding
import com.tipiz.toko_paerbe.ui.utils.currency

class TransactionAdapter(val moveToStatus: (DataTransaction) -> Unit) :
    ListAdapter<DataTransaction, TransactionAdapter.TransactionHolder>(TransactionDiffCallBack()) {

    private class TransactionDiffCallBack : DiffUtil.ItemCallback<DataTransaction>() {
        override fun areItemsTheSame(oldItem: DataTransaction, newItem: DataTransaction): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DataTransaction,
            newItem: DataTransaction
        ): Boolean = oldItem == newItem
    }


    inner class TransactionHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: DataTransaction) {
            with(binding) {
                cvItemTransaction.startAnimation(
                    AnimationUtils.loadAnimation(
                        itemView.context,
                        R.anim.anim_one
                    )
                )
                tvItemDate.text = transaction.date
                Glide.with(itemView.context)
                    .load(transaction.image)
                    .placeholder(R.drawable.img_alert)
                    .error(R.drawable.img_alert)
                    .into(ivItemTransactionImgProduct)
                tvItemTransactionProductName.text = transaction.name
                tvItemTransactionTotalItem.text =
                    itemView.context.getString(R.string.item_s, transaction.items.size)
                tvItemTransactionTotalPayValue.text = currency(transaction.total)
                btnItemTransactionReview.isVisible =
                    transaction.review == null || transaction.rating == null
                btnItemTransactionReview.setOnClickListener {
                    moveToStatus(transaction)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}