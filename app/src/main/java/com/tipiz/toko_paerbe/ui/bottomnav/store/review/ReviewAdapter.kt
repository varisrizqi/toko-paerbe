package com.tipiz.toko_paerbe.ui.bottomnav.store.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemReviewBinding

class ReviewAdapter : ListAdapter<DataReview, ReviewAdapter.ReviewHolder>(DIFF_CALLBACK) {


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataReview>() {
            override fun areItemsTheSame(oldItem: DataReview, newItem: DataReview): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataReview, newItem: DataReview): Boolean {
                return oldItem == newItem
            }


        }


    }
    class ReviewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(review:DataReview){

                with(binding){
                    Glide.with(itemView.context)
                        .load(review.userImage)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.img_alert).error(R.drawable.img_alert)
                        )
                        .into(binding.imgProfil)

                    tvName.text = review.userName
                    tvReview.text = review.userReview
                    rbReview.rating= review.userRating.toFloat()

                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context))
        return ReviewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        val user = getItem(position)

        holder.bind(user)
    }


}