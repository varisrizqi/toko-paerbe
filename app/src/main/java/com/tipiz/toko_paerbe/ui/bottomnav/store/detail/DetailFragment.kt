package com.tipiz.toko_paerbe.ui.bottomnav.store.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.core.domain.model.favorite.DataFavorite
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.model.products.ProductVariant
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onLoading
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentDetailBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import com.tipiz.toko_paerbe.ui.utils.Constant
import com.tipiz.toko_paerbe.ui.utils.Constant.CART_ADDED
import com.tipiz.toko_paerbe.ui.utils.Constant.extra_setImage
import com.tipiz.toko_paerbe.ui.utils.currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment :
    BaseFragmentBottomNav<FragmentDetailBinding, DetailViewModel>(FragmentDetailBinding::inflate) {
    override val viewModel: DetailViewModel by viewModel() //ktx
    private var isCreated: Boolean = false
    private var checkedChipId: Int = 0
    override fun initView() {


        val detail = arguments?.getString(Constant.extra_detail)
        if (detail != null && viewModel.responseDetail.value !is UiState.Success) {
            viewModel.detailProducts(detail)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDetailsReviewAll.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString(Constant.extra_detail, detail)
            findNavController().navigate(R.id.action_detailFragment_to_reviewFragment, mBundle)
        }

    }

    override fun initViewModel() {
        success()

    }

    private fun success() {
        with(viewModel) {


            responseDetail.launchAndCollectIn(viewLifecycleOwner) { products ->
                products.onSuccess { data ->
                    Log.e("Database", "detail $data")

                    binding.pgBar.visibility = View.INVISIBLE
                    binding.llBottomBar.visibility = View.VISIBLE
                    binding.scrollView.visibility = View.VISIBLE

                    binding.cgDetailsVariants.setOnCheckedStateChangeListener { _, _ ->
                        checkedChipId = binding.cgDetailsVariants.checkedChipId
                        data.setChip = binding.cgDetailsVariants.checkedChipId

                        data.productVariant[checkedChipId].variantName =
                            data.productVariant[checkedChipId].variantName
                        data.productVariant[checkedChipId].variantPrice =
                            data.productVariant[checkedChipId].variantPrice

                        val totalVariantPrice =
                            data.productPrice + data.productVariant[checkedChipId].variantPrice
                        binding.tvDetailsPrice.text = currency(totalVariantPrice)


                        setDataFavorite(
                            DataFavorite(
                                productId = data.productId,
                                productName = data.productName,
                                productPrice = data.productPrice,
                                image = data.image[0],
                                brand = data.brand,
                                description = data.description,
                                store = data.store,
                                sale = data.sale,
                                stock = data.stock,
                                totalRating = data.totalRating,
                                totalSatisfaction = data.totalSatisfaction,
                                productRating = data.productRating,
                                variantName = data.productVariant[checkedChipId].variantName,
                                variantPrice = data.productVariant[checkedChipId].variantPrice,
                                totalReview = data.totalReview,
                                setChip = checkedChipId
                            )
                        )

                        setChartData(
                            DataCart(
                                productId = data.productId,
                                brand = data.brand,
                                description = data.description,
                                image = data.image[0],
                                productName = data.productName,
                                productPrice = data.productPrice,
                                productRating = data.productRating,
                                variantName = data.productVariant[checkedChipId].variantName,
                                variantPrice = data.productVariant[checkedChipId].variantPrice,
                                sale = data.sale,
                                stock = data.stock,
                                store = data.store,
                                totalRating = data.totalRating,
                                totalReview = data.totalReview,
                                totalSatisfaction = data.totalSatisfaction
                            )
                        )

                        binding.btnAddToCart.setOnClickListener {
                            Log.e("varis", "detail chip ${data.setChip}")
                            lifecycleScope.launch(Dispatchers.IO) {
                                val message =
                                    viewModel.dataCart?.let { it1 -> viewModel.addChart(it1, true) }
                                if (message == CART_ADDED) {
                                    Snackbar.make(
                                        binding.root,
                                        getString(R.string.success_added_to_chart),
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Snackbar.make(
                                        binding.root,
                                        getString(R.string.out_of_stock),
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }


                    }

                    setUp(data)


                }.onLoading { setLoading() }

            }


        }
    }

    private fun fav(data: DataDetailProduct) {
        val detail = arguments?.getString(Constant.extra_detail)

        binding.ivDetailFav.setOnClickListener {
            viewModel.isFav = !viewModel.isFav
            if (viewModel.isFav) {
                viewModel.insertFav()
                Snackbar.make(
                    binding.root,
                    getString(R.string.success_added_to_wishlist),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                viewModel.deleteWishlist(detail ?: "")
                Snackbar.make(
                    binding.root,
                    getString(R.string.remove_fav),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.getIsFav(
            detail ?: ""
        ).observe(viewLifecycleOwner) {
            viewModel.isFav = it

            binding.ivDetailFav.setImageResource(
                when (viewModel.isFav) {
                    true -> R.drawable.ic_favorite_red
                    false -> R.drawable.ic_favorite_border

                }
            )
        }

    }


    private fun firstChipGroup() {
        val detailChip = arguments?.getInt(Constant.extra_chip)
        val detailVariant = arguments?.getString(Constant.extra_variant)

        if (detailVariant == null) {
            binding.cgDetailsVariants.check(
                binding.cgDetailsVariants.getChildAt(
                    detailChip ?: 0
                ).id
            )
        } else {
            binding.cgDetailsVariants.check(
                binding.cgDetailsVariants.getChildAt(
                    detailChip ?: 0
                ).id
            )
        }
    }

    private fun setLoading() {
        with(binding) {
            pgBar.visibility = View.VISIBLE
            llBottomBar.visibility = View.INVISIBLE
            scrollView.visibility = View.INVISIBLE
        }


    }


    private fun setUp(data: DataDetailProduct) {

        if (!isCreated) {

            setImageAdapter(data)
            with(binding) {

                ibDetailsShare.setImageResource(R.drawable.ic_share)
                binding.tvDetailsPrice.text =
                    currency(data.productPrice + data.productVariant[checkedChipId].variantPrice)
                tvDetailsTitle.text = data.productName
                tvSold.text = getString(R.string.sold_10)
                    .replace("%10%", data.sale.toString())
                tvRate.text = getString(R.string.rating_dan_ulasan)
                    .replace("%4.5%", data.productRating.toString())
                    .replace("%2%", data.totalRating.toString())
                tvDetailsSelectVariant.text = getString(R.string.select_variant)
                tvDetailsProductDescTitle.text = getString(R.string.product_description)
                tvDetailsDesc.text = data.description
                tvDetailsReviewTitle.text = getString(R.string.buyer_reviews)
                btnDetailsReviewAll.text = getString(R.string.see_all)
                ivDetailsStarReview.setImageResource(R.drawable.ic_star)
                tvDetailsStarReview.text = data.productRating.toString()
                tvDetailsStarMax.text = getString(R.string._5_0)
                tvDetailsSatisfaction.text = getString(R.string._100_buyers_feel_satisfied)
                    .replace("%100%", data.totalSatisfaction.toString())
                tvDetailsRatingReview.text = getString(R.string.rating_dan_review)
                    .replace("%2%", data.productRating.toString())
                    .replace("%3%", data.totalRating.toString())
                btnDirectBuy.text = getString(R.string.buy)
                btnAddToCart.text = getString(R.string.cart_plus)
                imgStar.setImageResource(R.drawable.ic_star)
                createVariant(data.productVariant)
                firstChipGroup()
                fav(data)
                isCreated = true
            }
        }

    }

    private fun setImageAdapter(detail: DataDetailProduct) {
        val setImage = arguments?.getInt(extra_setImage)
        val sectionsPagerAdapter = DetailSectionsPagerLayout(detail.image)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabDetailsTabView, binding.viewPager) { _, _ -> }.attach()
        (requireActivity() as AppCompatActivity).supportActionBar?.elevation = 0f

        /* // Di dalam fungsi setUp() atau tempat lainnya di mana Anda menginisialisasi ViewPager
         binding.viewPager.setCurrentItem(setImage ?: 0, false)
         binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
             override fun onPageSelected(position: Int) {
                 super.onPageSelected(position)
                 // Simpan indeks gambar yang dipilih di sini
                 detail.setImage = position
             }
         })*/

    }

    private fun createVariant(variant: List<ProductVariant>) {
        var i = 0
        variant.forEach {
            val chip = Chip(context)
            chip.text = it.variantName
            chip.id = i
            chip.isCloseIconVisible = false
            val drawable = ChipDrawable.createFromAttributes(
                requireContext(),
                null,
                0,
                R.style.Widget_App_Chip
            )
            chip.setChipDrawable(drawable)
            chip.isHorizontalFadingEdgeEnabled = false
            binding.cgDetailsVariants.addView(chip)
            i++
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        isCreated = false
    }
}
