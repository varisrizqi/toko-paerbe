package com.tipiz.core.data.local.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tipiz.core.data.network.retrofit.ApiService
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.core.domain.model.products.ProductsBody
import com.tipiz.core.utils.Constant.INITIAL_PAGE_INDEX
import com.tipiz.core.utils.DataMapper.toUiListData

class ProductPagingSource(
    private val api: ApiService,
    private val productsBody: ProductsBody?
) : PagingSource<Int, DataProduct>() {
    override fun getRefreshKey(state: PagingState<Int, DataProduct>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataProduct> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = api.fetchProduct(
                search = productsBody?.search,
                brand = productsBody?.brand,
                lowest = productsBody?.lowest,
                highest = productsBody?.highest,
                sort = productsBody?.sort,
                10,
                position
            )

            LoadResult.Page(
                data = responseData.toUiListData(),
                prevKey = null,
                nextKey = if (position == responseData.data.totalPages) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }

    }
}