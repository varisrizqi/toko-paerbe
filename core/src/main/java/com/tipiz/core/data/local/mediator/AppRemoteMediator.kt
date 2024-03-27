package com.tipiz.core.data.local.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tipiz.core.data.local.room.database.DataBaseClient
import com.tipiz.core.data.local.room.entity.PagingKeys
import com.tipiz.core.data.local.room.entity.ProductEntity
import com.tipiz.core.data.network.retrofit.ApiService
import com.tipiz.core.utils.Constant.INITIAL_PAGE_INDEX
import com.tipiz.core.utils.DataMapper.toLocalListData

@OptIn(ExperimentalPagingApi::class)
class AppRemoteMediator(
    private val apiEndPoint: ApiService,
    private val database: DataBaseClient
):RemoteMediator<Int, ProductEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
       val page = when (loadType){
           LoadType.REFRESH ->{
               val pagingKeys = getPagingKeyClosetToCurrentPosition(state)
               pagingKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
           }

           LoadType.PREPEND ->{
               val pagingKeys = getPagingKeyForFirstItem(state)
               val prevKey = pagingKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = pagingKeys != null)
               prevKey
           }

           LoadType.APPEND ->{
               val pagingKeys = getPagingKeyForLastItem(state)
               val nextKey = pagingKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = pagingKeys != null)
               nextKey
           }
       }

        return try {
            val responseData = apiEndPoint.fetchProduct(limit = state.config.initialLoadSize, page = page)
            val endOfPaginationReached = responseData.data.items.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    database.appDao().deleteAllKey()
                    database.appDao().deleteAll()
                }
                val prevKey = if (page == 1) null else - 1
                val nextKey = if(endOfPaginationReached) null else page + 1
                val keys = responseData.data.items.map {
                    PagingKeys(id = it.productId, prevKey = prevKey, nextKey = nextKey )
                }
                database.appDao().insertAll(keys)
                database.appDao().insertProduct(responseData.toLocalListData())
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e:Exception){
            MediatorResult.Error(e)
        }
    }

    private suspend fun getPagingKeyForLastItem(state: PagingState<Int, ProductEntity>): PagingKeys? {
        return state.pages.lastOrNull{it.data.isNotEmpty()}?.data?.lastOrNull()?.let { data ->
            database.appDao().getPagingKeysId(data.productId)
        }
    }

    private suspend fun getPagingKeyForFirstItem(state: PagingState<Int, ProductEntity>):PagingKeys? {
        return state.pages.firstOrNull{it.data.isNotEmpty()}?.data?.firstOrNull()?.let { data ->
            database.appDao().getPagingKeysId(data.productId)
        }
    }

    private suspend fun getPagingKeyClosetToCurrentPosition(state: PagingState<Int, ProductEntity>): PagingKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.productId?.let { id ->
                database.appDao().getPagingKeysId(id)
            }
        }
    }


}