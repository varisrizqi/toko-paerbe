package com.tipiz.toko_paerbe.ui.bottomnav.store

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onError
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentStoreBinding
import com.tipiz.toko_paerbe.ui.bottomnav.store.adapter.ProductPagingAdapter
import com.tipiz.toko_paerbe.ui.bottomnav.store.adapter.StorePagingGridAdapter
import com.tipiz.toko_paerbe.ui.bottomnav.store.adapter.StorePagingListAdapter
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import com.tipiz.toko_paerbe.ui.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException


class StoreFragment : BaseFragment<FragmentStoreBinding,StoreViewModel>(FragmentStoreBinding::inflate){
    override val viewModel: StoreViewModel by viewModel()
    private var pagingData: PagingData<DataProduct>? = null
    private lateinit var adapter: ProductPagingAdapter
    private lateinit var lmLinear: LinearLayoutManager
    private lateinit var lmGrid: GridLayoutManager


    private val listAdapter by lazy {
        StorePagingListAdapter(object : StorePagingListAdapter.OnAdapterListener {
            override fun onClick(store: DataProduct) {
                val mBundle = Bundle()
                mBundle.putString(Constant.extra_detail, store.productId)
                activity?.supportFragmentManager?.findFragmentById(R.id.container_main_nav_host)
                    ?.findNavController()
                    ?.navigate(R.id.action_dashBoardFragment_to_detailFragment, mBundle)
            }
        })
    }

    private val gridAdapter by lazy {
        StorePagingGridAdapter(object : StorePagingGridAdapter.OnAdapterListener {
            override fun onClick(store: DataProduct) {
                val mBundle = Bundle()
                mBundle.putString(Constant.extra_detail, store.productId)
                activity?.supportFragmentManager?.findFragmentById(R.id.container_main_nav_host)
                    ?.findNavController()
                    ?.navigate(R.id.action_dashBoardFragment_to_detailFragment, mBundle)
            }
        })
    }

    override fun initView() {

        showProductList()
        binding.chipRv.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                fetchGrid()
                showProductGrid()
                binding.chipRv.chipIcon = context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        R.drawable.ic_grid_view
                    )
                }
            } else {
                showProductList()
                binding.chipRv.chipIcon = context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        R.drawable.ic_list_linear
                    )
                }
            }

        }

    }

    override fun initViewModel() {
        with(viewModel){
            fetchProduct().launchAndCollectIn(viewLifecycleOwner) { product ->
                product.onSuccess { data ->
                    pagingData = data
                    listAdapter.submitData(viewLifecycleOwner.lifecycle, data)
                }

                product.onError { error ->
                    val errorMessage = when (error) {
                        is HttpException -> {
                            val errorBody = error.response()?.errorBody()?.string()
                            "$errorBody"
                        }

                        else -> "${error.message}"
                    }
                    context?.let {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

    }

    private fun fetchGrid() {
        viewModel.fetchProduct().launchAndCollectIn(viewLifecycleOwner) { product ->
            product.onSuccess { data ->
                pagingData = data
                gridAdapter.submitData(viewLifecycleOwner.lifecycle, data)

            }

            product.onError { error ->
                val errorMessage = when (error) {
                    is HttpException -> {
                        val errorBody = error.response()?.errorBody()?.string()
                        "$errorBody"
                    }

                    else -> "${error.message}"
                }
                context?.let {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun showProductList() {
        binding.rvStore.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
            setHasFixedSize(true)
        }
        binding.chipRv.isChecked = false
    }

    private fun showProductGrid() {
        binding.rvStore.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = gridAdapter
            setHasFixedSize(true)
        }
        binding.chipRv.isChecked = true
    }

    private fun initPagingGit(){
        adapter = ProductPagingAdapter(object: ProductPagingAdapter.OnPagingListener{
            override fun onClick(store: DataProduct) {
                val mBundle = Bundle()
                mBundle.putString(Constant.extra_detail, store.productId)
                activity?.supportFragmentManager?.findFragmentById(R.id.container_main_nav_host)
                    ?.findNavController()
                    ?.navigate(R.id.action_dashBoardFragment_to_detailFragment, mBundle)
            }
        })

        lmLinear = LinearLayoutManager(context)
        lmGrid = GridLayoutManager(context, 2)

    }


}