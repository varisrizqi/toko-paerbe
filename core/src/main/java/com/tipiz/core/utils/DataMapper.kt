package com.tipiz.core.utils

import com.tipiz.core.data.local.room.entity.FavoriteEntity
import com.tipiz.core.data.local.room.entity.ProductEntity
import com.tipiz.core.data.network.data.login.LoginResponse
import com.tipiz.core.data.network.data.products.ItemsItem
import com.tipiz.core.data.network.data.products.ProductsResponse
import com.tipiz.core.data.network.data.profile.ProfileResponse
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.refresh.RefreshResponse
import com.tipiz.core.data.network.data.register.RegisterResponse
import com.tipiz.core.domain.model.favorite.DataFavorite
import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataProfile
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.core.domain.model.products.ProductVariant
import com.tipiz.core.domain.model.refresh.RefreshBody
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.core.remote.data.detail.DetailResponse
import com.tipiz.core.remote.data.detail.ProductVariantItem
import com.tipiz.core.remote.data.review.DataItemReview
import com.tipiz.core.remote.data.review.ReviewResponse

object DataMapper {

    fun RegisterResponse.toUiData() = DataToken(
        accessToken = data.accessToken,
        expiresAt = data.expiresAt,
        refreshToken = data.refreshToken
    )

    fun LoginResponse.toUiData() = DataLogin(
        userImage = data.userImage,
        userName = data.userName,
        accessToken = data.accessToken,
        expiresAt = data.expiresAt,
        refreshToken = data.refreshToken
    )

    fun RefreshResponse.toUiData() = DataToken(
        accessToken = data.accessToken,
        expiresAt = data.expiresAt,
        refreshToken = data.refreshToken
    )

    fun ProfileResponse.toUiData() = DataProfile(
        userName = data.userName,
        userImage = data.userImage
    )

    /*
   * sample mengambil data langsung dari API
   * dari response ke model
   * tanpa menyimpan ke room
   * */
    private fun ItemsItem.toUiData() = DataProduct(
        productId = productId,
        productName = productName,
        productPrice = productPrice,
        image = image,
        store = store,
        sale = sale,
        productRating = productRating
    )

    fun ProductsResponse.toUiListData() = data.items.map { item -> item.toUiData() }.toList()


    /*
     * sample mengubah data dari response ke entity room(local)
     * */
    private fun ItemsItem.toLocalData() = ProductEntity(
        productId = productId,
        image = image,
        productName = productName,
        productPrice = productPrice,
        productRating = productRating,
        sale = sale,
        store = store
    )

    fun ProductsResponse.toLocalListData() =
        data.items.map { itemsItem -> itemsItem.toLocalData() }.toList()

    fun ProductEntity.toUIData() = DataProduct(
        productId = productId,
        productName = productName,
        productPrice = productPrice,
        productRating = productRating,
        image = image,
        store = store,
        sale = sale
    )

    fun DetailResponse.toUIData() = DataDetailProduct(
        productId = data.productId,
        productName = data.productName,
        productPrice = data.productPrice,
//        image = data.image[0],
        image = data.image,
        brand = data.brand,
        description = data.description,
        store = data.store,
        sale = data.sale,
        stock = data.stock,
        totalRating = data.totalRating,
        totalSatisfaction = data.totalSatisfaction,
        productRating = data.productRating,
//        variantName = data.productVariant[0].variantName,
//        variantPrice = data.productVariant[0].variantPrice,
        productVariant = data.productVariant.map { variant -> variant.toUIVariantData() },
        totalReview = data.totalReview
    )

    //Cadangan
  /*  fun DetailResponse.toUiData() = DataDetailProduct(
        productId = data.productId,
        productName = data.productName,
        productPrice = data.productPrice,
        image = data.image,
        brand = data.brand,
        description = data.description,
        store = data.store,
        sale = data.sale,
        stock = data.stock,
        totalRating = data.totalRating,
        totalSatisfaction = data.totalSatisfaction,
        productRating = data.productRating,
        productVariant = data.productVariant.map { variant -> variant.toUIVariantData() },
//        variantName = data.productVariant[0].variantName,
//        variantPrice = data.productVariant[0].variantPrice,
        totalReview = data.totalReview
    )*/

    //ORI
    private fun ProductVariantItem.toUIVariantData() =
        ProductVariant(
            variantName = variantName,
            variantPrice = variantPrice
        )


    private fun DataItemReview.toUiData() = DataReview(
        userImage = userImage,
        userName = userName,
        userRating = userRating,
        userReview = userReview
    )

    fun ReviewResponse.toUiListData() = data.map { review -> review.toUiData() }.toList()

    fun RefreshRequest.toUiData() = RefreshBody(
        token = token
    )

    fun DataFavorite.toEntity() = FavoriteEntity(
        productId = productId,
        productName = productName,
        productPrice = productPrice,
        image = image,
        brand = brand,
        description = description,
        store = store,
        sale = sale,
        stock = stock,
        totalRating = totalRating,
        totalSatisfaction = totalSatisfaction,
        productRating = productRating,
        variantName = variantName,
        variantPrice = variantPrice,
        totalReview = totalReview,
        setChip = setChip
    )

    /*fun DataDetailProduct.toEntity() = FavoriteEntity(
        productId = productId,
        productName = productName,
        productPrice = productPrice,
        image = image,
        brand = brand,
        description = description,
        store = store,
        sale = sale,
        stock = stock,
        totalRating = totalRating,
        totalSatisfaction = totalSatisfaction,
        productRating = productRating,
//        variantName = variantName,
//        variantPrice = variantPrice,
        totalReview = totalReview
    )*/

    fun List<FavoriteEntity>.toUiData(): List<DataFavorite> {
        return this.map {
            DataFavorite(
                productId = it.productId,
                productName = it.productName,
                productPrice = it.productPrice,
                image = it.image,
                brand = it.brand,
                description = it.description,
                store = it.store,
                sale = it.sale,
                stock = it.stock,
                totalRating = it.totalRating,
                totalSatisfaction = it.totalSatisfaction,
                productRating = it.productRating,
                variantName = it.variantName,
                variantPrice = it.variantPrice,
                totalReview = it.totalReview,
                setChip = it.setChip
            )
        }
    }


    //cadangan detail
    /*fun List<FavoriteEntity>.toUiData2(): List<DataDetailProduct> {
        val listFav = mutableListOf<DataDetailProduct>()
        this.forEach {
            listFav.add(
                DataDetailProduct(
                    productId = it.productId,
                    productName = it.productName,
                    productPrice = it.productPrice,
                    image = it.image,
                    brand = it.brand,
                    description = it.description,
                    store = it.store,
                    sale = it.sale,
                    stock = it.stock,
                    totalRating = it.totalRating,
                    totalSatisfaction = it.totalSatisfaction,
                    productRating = it.productRating,
                    variantName = it.variantName,
                    variantPrice = it.variantPrice,
                    totalReview = it.totalReview
                )
            )
        }
        return  listFav

    }*/

}

