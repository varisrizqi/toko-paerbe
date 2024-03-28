package com.tipiz.core.data.local.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tipiz.core.data.local.mediator.AppRemoteMediator
import com.tipiz.core.data.local.room.database.DataBaseClient
import com.tipiz.core.data.local.room.entity.ProductEntity
import com.tipiz.core.data.network.retrofit.ApiService
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalPagingApi::class)
class PagingDataSource(
    private val apiService: ApiService,
    private val database: DataBaseClient
) {

    fun fetchProduct(): Flow<PagingData<ProductEntity>> = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = 10,
            initialLoadSize = 10,
            prefetchDistance = 1
        ),
        remoteMediator = AppRemoteMediator(apiEndPoint = apiService, database = database),
        pagingSourceFactory = {
            database.appDao().retrieveAllProducts()
        }
    ).flow
}