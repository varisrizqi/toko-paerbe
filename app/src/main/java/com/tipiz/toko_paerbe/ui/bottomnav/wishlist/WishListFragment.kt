package com.tipiz.toko_paerbe.ui.bottomnav.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.core.domain.model.favorite.DataFavorite
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentWishListBinding
import com.tipiz.toko_paerbe.ui.utils.Constant
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
            showError(listFav!!.isEmpty())
            showFavorite(listFav.isEmpty(), listFav)
            binding.tvWishlistItems.text = getString(R.string.item_s, listFav.size)
        }

        adapter = WishlistListAdapter(requireContext(), { id ->
            deleteItem(id)
        }, { item ->
//            addChart(item)
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

    private fun deleteItem(id: String) {
        viewModel.deleteFav(id)
    }

    private fun showFavorite(state: Boolean, listFav: List<DataFavorite>) {
        if (!state) {
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
    }

    private fun showError(state: Boolean) {
        binding.ivWishlistError.isVisible = state == true
        binding.tvWishlistErrorCode.isVisible = state == true
        binding.tvWishlistErrorMessage.isVisible = state == true
        binding.tvWishlistItems.isVisible = state == false
        binding.ivWishlistLayoutType.isVisible = state == false
        binding.dvdrWishlist1.isVisible = state == false
        binding.rvWishlist.isVisible = state == false
    }

    companion object {
        const val SPAN_SIZE = 2
    }

}