package com.tipiz.toko_paerbe.ui.bottomnav.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipiz.core.domain.model.firebase.Notification
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemNotificationBinding

class NotificationAdapter(private val updateIsCheck: (Int, Boolean) -> Unit) :
    ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(DIFF_FOLLBACK) {

    companion object {
        val DIFF_FOLLBACK = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Notification, context: Context) {
            with(binding) {
                itemView.setBackgroundColor(
                    when (data.isChecked) {
                        true -> {
                            context.resources.getColor(R.color.white, context.theme)
                        }

                        false -> {
                            context.resources.getColor(R.color.p_90, context.theme)
                        }
                    }
                )
                Glide.with(context)
                    .load(data.image)
                    .placeholder(R.drawable.thumbnail_load_product)
                    .error(R.drawable.thumbnail_load_product)
                    .into(ivItemNotificationImgThumbnail)
                tvItemNotificationInfo.text = data.type
                tvItemNotificationSuccess.text = data.title
                tvItemNotificationDescription.text = data.body
                tvItemNotificationDateTime.text =
                    context.getString(R.string.date_time_notification, data.date, data.time)
                itemView.setOnClickListener {
                    updateIsCheck(data.id, true)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, holder.itemView.context)
    }
}