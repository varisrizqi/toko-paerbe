package com.tipiz.toko_paerbe.ui.bottomnav.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.core.domain.model.favorite.DataFavorite
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentWishListBinding
import com.tipiz.toko_paerbe.ui.utils.Constant
import com.tipiz.toko_paerbe.ui.utils.Constant.CART_ADDED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class WishListFragment : Fragment() {
    private var _binding: FragmentWishListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WishlistViewModel by viewModel()

    private lateinit var adapter: WishlistListAdapter
    private lateinit var lmLinear: LinearLayoutManager
    private lateinit var lmGrid: GridLayoutManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getAllFav().observe(viewLifecycleOwner) { listFav ->
            showError(listFav.isEmpty())
            showFavorite(listFav)
            binding.tvWishlistItems.text = getString(R.string.item_s, listFav.size)

        }

        adapter = WishlistListAdapter(requireContext(), { id ->
            deleteItem(id.wishlistId)
        }, { item ->
            addChart(item)
        })

        lmLinear = LinearLayoutManager(requireContext())
        lmGrid = GridLayoutManager(requireContext(), SPAN_SIZE)
        binding.rvWishlist.layoutManager = if (viewModel.isGridLayout) lmGrid else lmLinear
        adapter.setLayoutType(viewModel.isGridLayout)
        adapter.setOnItemClickCallback(object : WishlistListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataFavorite) {
                val mBundle = Bundle()
                mBundle.putString(Constant.extra_detail, data.productId)
                mBundle.putString(Constant.extra_variant, data.variantName)
                mBundle.putInt(Constant.extra_chip, data.setChip )
                activity?.supportFragmentManager?.findFragmentById(R.id.container_main_nav_host)
                    ?.findNavController()
                    ?.navigate(R.id.action_dashBoardFragment_to_detailFragment, mBundle)
            }
        })
        binding.rvWishlist.itemAnimator = null
        binding.rvWishlist.adapter = adapter
    }

    private fun deleteItem(id: Int) {
        viewModel.deleteFav(id)
    }

    private fun showFavorite( listFav: List<DataFavorite>) {
        adapter.submitList(listFav)
        binding.ivWishlistLayoutType.setOnClickListener {
            viewModel.isGridLayout = !viewModel.isGridLayout
            binding.ivWishlistLayoutType.setImageResource(
                when (viewModel.isGridLayout) {
                    true -> R.drawable.ic_list_grid
                    else -> R.drawable.ic_list_linear
                }
            )
            binding.rvWishlist.layoutManager = if (viewModel.isGridLayout) lmGrid else lmLinear
            adapter.setLayoutType(viewModel.isGridLayout)
            binding.rvWishlist.adapter = adapter
            adapter.submitList(listFav)
        }
    }

    private fun showError(state: Boolean) {
        // Visible view
        binding.ivWishlistError.isVisible = state
        binding.tvWishlistErrorCode.isVisible = state
        binding.tvWishlistErrorMessage.isVisible = state
        // Invisible view
        binding.tvWishlistItems.isVisible = !state
        binding.ivWishlistLayoutType.isVisible = !state
        binding.dvdrWishlist1.isVisible = !state
        binding.rvWishlist.isVisible = !state
    }

    private fun addChart(data:DataFavorite){

        viewModel.setChartData(
            DataCart(
                productId = data.productId,
                brand = data.brand,
                description = data.description,
                image = data.image,
                productName = data.productName,
                productPrice = data.productPrice,
                productRating = data.productRating,
                variantName = data.variantName,
                variantPrice = data.variantPrice,
                sale = data.sale,
                stock = data.stock,
                store = data.store,
                totalRating = data.totalRating,
                totalReview = data.totalReview,
                totalSatisfaction = data.totalSatisfaction
            )
        )


        lifecycleScope.launch(Dispatchers.IO) {
            val message = viewModel.dataCart?.let { viewModel.addChart(it,true) }
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

    companion object {
        const val SPAN_SIZE = 2
    }

}