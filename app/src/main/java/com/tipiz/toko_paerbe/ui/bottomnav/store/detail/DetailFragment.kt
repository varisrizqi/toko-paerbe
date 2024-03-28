package com.tipiz.toko_paerbe.ui.bottomnav.store.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.model.products.ProductVariant
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentDetailBinding
import com.tipiz.toko_paerbe.ui.bottomnav.store.StoreViewModel
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import com.tipiz.toko_paerbe.ui.utils.Constant
import com.tipiz.toko_paerbe.ui.utils.currency
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : BaseFragment<FragmentDetailBinding, StoreViewModel>(FragmentDetailBinding::inflate){
    override val viewModel: StoreViewModel by viewModel() //ktx
    private var isFav: Boolean = false

    override fun initView() {
        val detail = arguments?.getString(Constant.extra_detail)
        viewModel.detailProducts(detail ?: "")

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.cbDetailsFavorite.setOnClickListener{
            isFav = if (!isFav) {

                binding.cbDetailsFavorite.setImageResource(R.drawable.ic_favorite)
                true


            } else {

                context?.let { context ->
                    Toast.makeText(context, getString(R.string.remove_fav), Toast.LENGTH_SHORT).show()
                }
                binding.cbDetailsFavorite.setImageResource(R.drawable.ic_favorite_border)
                false
            }
        }

        binding.btnDetailsReviewAll.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString(Constant.extra_detail, detail)
            findNavController().navigate(R.id.action_detailFragment_to_reviewFragment, mBundle)
        }
    }

    override fun initViewModel() {
       with(viewModel){
          responseDetail.launchAndCollectIn(viewLifecycleOwner) { products ->
              products.onSuccess { data ->
                  setUp(data)
                  addVariant(data.productVariant, data.productPrice)


              }

          }
       }
    }

    private fun setUp(detail: DataDetailProduct) {

        val sectionsPagerAdapter = DetailSectionsPagerLayout(detail.image)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabDetailsTabView, binding.viewPager) { _, _ -> }.attach()
        (requireActivity() as AppCompatActivity).supportActionBar?.elevation = 0f

        with(binding) {
            toolbar.title = getString(R.string.detail_product)
            ibDetailsShare.setImageResource(R.drawable.ic_share)
            tvDetailsPrice.text = currency(detail.productPrice)
            tvDetailsTitle.text = detail.productName
            tvSold.text = getString(R.string.sold_10)
                .replace("%10%", detail.sale.toString())
            tvRate.text = getString(R.string.rating_dan_ulasan)
                .replace("%4.5%", detail.productRating.toString())
                .replace("%2%", detail.totalRating.toString())
            tvDetailsSelectVariant.text = getString(R.string.select_variant)
            tvDetailsProductDescTitle.text = getString(R.string.product_description)
            tvDetailsDesc.text = detail.description
            tvDetailsReviewTitle.text = getString(R.string.buyer_reviews)
            btnDetailsReviewAll.text = getString(R.string.see_all)
            ivDetailsStarReview.setImageResource(R.drawable.ic_star)
            tvDetailsStarReview.text = detail.productRating.toString()
            tvDetailsStarMax.text = getString(R.string._5_0)
            tvDetailsSatisfaction.text = getString(R.string._100_buyers_feel_satisfied)
                .replace("%100%", detail.totalSatisfaction.toString())
            tvDetailsRatingReview.text = getString(R.string.rating_dan_review)
                .replace("%2%", detail.productRating.toString())
                .replace("%3%", detail.totalRating.toString())
            btnDirectBuy.text = getString(R.string.buy)
            btnAddToCart.text = getString(R.string.cart_plus)
            imgStar.setImageResource(R.drawable.ic_star)
            cbDetailsFavorite.setImageResource(R.drawable.ic_favorite_border)
        }

    }
    private fun addVariant(variants: List<ProductVariant>, productPrice: Int) {

        for (variant in variants) {
            val chip = Chip(context)

            val totalVariantPrice = variant.variantPrice + productPrice
            val chipText = variant.variantName
            chip.text = chipText
            binding.cgDetailsVariants.addView(chip)
            chip.setOnClickListener {
                binding.tvDetailsPrice.text = currency(totalVariantPrice)
                chip.isSelected = true
                resetBackgroundColors(binding.cgDetailsVariants, chip)
            }
        }
    }
    private fun resetBackgroundColors(chipGroup: ChipGroup, selectedChip: Chip) {

        for (index in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(index) as Chip
            if (chip != selectedChip) {
                chip.isSelected = false
            }
        }
    }
}