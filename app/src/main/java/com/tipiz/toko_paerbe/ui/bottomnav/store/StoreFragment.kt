package com.tipiz.toko_paerbe.ui.bottomnav.store

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.core.domain.model.products.ProductsBody
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onError
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentStoreBinding
import com.tipiz.toko_paerbe.ui.bottomnav.store.adapter.LoadStateAdapterProduct
import com.tipiz.toko_paerbe.ui.bottomnav.store.adapter.ProductPagingAdapter
import com.tipiz.toko_paerbe.ui.bottomnav.store.adapter.StorePagingGridAdapter
import com.tipiz.toko_paerbe.ui.bottomnav.store.adapter.StorePagingListAdapter
import com.tipiz.toko_paerbe.ui.bottomnav.store.bottomsheet.BottomSheetFragment
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import com.tipiz.toko_paerbe.ui.utils.Constant
import com.tipiz.toko_paerbe.ui.utils.Constant.PAGING_PAGE
import com.tipiz.toko_paerbe.ui.utils.Constant.PAGING_PAGE_LIMIT
import com.tipiz.toko_paerbe.ui.utils.Constant.extra_btm_sheet
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import java.io.IOException


class StoreFragment :
    BaseFragmentBottomNav<FragmentStoreBinding, StoreViewModel>(FragmentStoreBinding::inflate) {
    override val viewModel: StoreViewModel by viewModel()
    private var pagingData: PagingData<DataProduct>? = null
    private lateinit var adapter: ProductPagingAdapter
    private lateinit var lmLinear: LinearLayoutManager
    private lateinit var lmGrid: GridLayoutManager
    private lateinit var footerAdapter: LoadStateAdapterProduct


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

        initPagingGit()

        binding.rlStore.setOnRefreshListener {
            println("varis btnrefresh 1")
            adapter.refresh()
            binding.rlStore.isRefreshing = false
        }

        // Reset button
        binding.btnStoreRefresh.setOnClickListener {
            if (binding.btnStoreRefresh.text.toString() == getString(R.string.reset)){
                viewModel.productsBody.postValue(
                    ProductsBody(
                        search = null,
                        brand = null,
                        lowest = null,
                        highest = null,
                        sort = null,
                        limit = PAGING_PAGE_LIMIT,
                        page = PAGING_PAGE
                    )
                )
                binding.edSearch.text = null
            }else{
                adapter.refresh()
            }
        }



        binding.chipRv.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.isGridLayout = true
                binding.chipRv.chipIcon = context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        R.drawable.ic_grid_view
                    )
                }
            } else {
                viewModel.isGridLayout = false
                binding.chipRv.chipIcon = context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        R.drawable.ic_list_linear
                    )
                }
            }
            binding.rvStore.layoutManager = if (viewModel.isGridLayout) lmGrid else lmLinear
            adapter.setLayoutType(viewModel.isGridLayout)
            binding.rvStore.adapter = adapter.withLoadStateFooter(
                footer = footerAdapter
            )
            adapter.notifyItemChanged(0)
        }

        binding.chipFilter.setOnClickListener {
        val btmSheet = BottomSheetFragment()
            btmSheet.show(childFragmentManager,extra_btm_sheet)
        }

    }

    override fun initViewModel() {
        with(viewModel) {
            products.observe(viewLifecycleOwner){
                showPagingGit(it)
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

    private fun initPagingGit() {
        adapter = ProductPagingAdapter(object : ProductPagingAdapter.OnPagingListener {
            override fun onClick(store: DataProduct) {
                val mBundle = Bundle()
                mBundle.putString(Constant.extra_detail, store.productId)
                activity?.supportFragmentManager?.findFragmentById(R.id.container_main_nav_host)
                    ?.findNavController()
                    ?.navigate(R.id.action_dashBoardFragment_to_detailFragment, mBundle)
            }
        })

        lmLinear = LinearLayoutManager(requireContext())
        lmGrid = GridLayoutManager(context, 2)
        footerAdapter = LoadStateAdapterProduct()
        lmGrid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount && footerAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }

        adapter.addLoadStateListener { loadState ->
            showLoading(loadState.refresh is LoadState.Loading)
            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            when (val throwable = errorState?.error) {
                is IOException -> {

                    //jika internet tidak ada
                    showError(
                        getString(R.string.io_exception_code),
                        getString(R.string.io_exception_message)
                    )
                }

                is HttpException -> {

                    //jika data kosong
                    if (throwable.code() == 404) {
                        showError(
                            getString(R.string.http_exception_404_code),
                            getString(R.string.http_exception_404_message)
                        )
                    } else {
                        showError(throwable.code().toString(), throwable.message().toString())
                    }
                }
            }
        }


    }

    private fun showPagingGit(data: PagingData<DataProduct>) {
        binding.rvStore.layoutManager = if (viewModel.isGridLayout) lmGrid else lmLinear
        adapter.setLayoutType(viewModel.isGridLayout)
        binding.rvStore.adapter = adapter.withLoadStateFooter(
            footer = footerAdapter
        )
        adapter.submitData(lifecycle, data)
        adapter.notifyItemChanged(0)
    }

    private fun showLoading(loadState:Boolean){
        binding.apply {
            // Visible component
            if (!viewModel.isGridLayout){
               shimmerLinear.skelShimmerLayoutLinear.visibility = if (loadState) View.VISIBLE else View.INVISIBLE
            } else{
                shimmerGrid.skelShimmerLayoutGrid.visibility = if (loadState) View.VISIBLE else  View.INVISIBLE
            }
            // Invisible component
            rvStore.visibility = if (loadState) View.INVISIBLE else View.VISIBLE
            chipFilter.visibility = if (loadState) View.INVISIBLE else View.VISIBLE
            chipRv.visibility = if (loadState) View.INVISIBLE else View.VISIBLE
            dvdr1.visibility = if (loadState) View.INVISIBLE else View.VISIBLE
            chipStoreGroup.visibility = if (loadState) View.INVISIBLE else View.VISIBLE
            ivStoreError.visibility = View.INVISIBLE
            tvStoreErrorCode.visibility = View.INVISIBLE
            tvStoreErrorMessage.visibility = View.INVISIBLE
            btnStoreRefresh.visibility = View.INVISIBLE

        }
    }

    private fun showError(code:String, message:String){
        binding.apply {
            // Invisible component
            rvStore.visibility = View.INVISIBLE
            chipFilter.visibility = View.INVISIBLE
            chipRv.visibility = View.INVISIBLE
            dvdr1.visibility = View.INVISIBLE
            chipStoreGroup.visibility = View.INVISIBLE
            // Visible component
            ivStoreError.visibility = View.VISIBLE
            tvStoreErrorCode.visibility = View.VISIBLE
            tvStoreErrorMessage.visibility = View.VISIBLE
            btnStoreRefresh.visibility = View.VISIBLE
            // Data
            btnStoreRefresh.text =
                if (code == getString(R.string.http_exception_404_code)) {
                    getString(R.string.reset)
                } else {
                    getString(
                        R.string.refresh
                    )
                }
            tvStoreErrorCode.text = code
            tvStoreErrorMessage.text = message
        }
    }


}