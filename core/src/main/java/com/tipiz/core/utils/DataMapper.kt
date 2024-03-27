package com.tipiz.core.utils

import com.tipiz.core.data.local.room.entity.ProductEntity
import com.tipiz.core.data.network.data.login.LoginResponse
import com.tipiz.core.data.network.data.products.ItemsItem
import com.tipiz.core.data.network.data.products.ProductsResponse
import com.tipiz.core.data.network.data.profile.ProfileResponse
import com.tipiz.core.data.network.data.refresh.RefreshResponse
import com.tipiz.core.data.network.data.register.RegisterResponse
import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataProfile
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.core.domain.model.products.ProductVariant
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.core.remote.data.detail.DataDetail
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

    fun DataDetail.toUIData() = DataDetailProduct(
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
        productVariant = productVariant.map { variant -> variant.toUIVariantData() }
    )

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
}