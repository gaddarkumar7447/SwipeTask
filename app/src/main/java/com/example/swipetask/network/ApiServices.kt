package com.example.swipetask.network

import com.example.swipetask.model.ProductDetails
import com.example.swipetask.model.ProductDetailsItem
import com.example.swipetask.modelproductadd.AddProductItem
import com.example.swipetask.utilities.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @GET(Constants.GET)
    suspend fun getProductList() : Response<ProductDetails>

    @POST(Constants.POST)
    suspend fun addProductDetails(@Body productDetailsItem: ProductDetailsItem) : Response<AddProductItem>
}