package com.example.swipetask.network

import com.example.swipetask.model.ProductDetails
import com.example.swipetask.modelproductadd.AddProductItem
import com.example.swipetask.utilities.Constants
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @GET(Constants.GET)
    suspend fun getProductList() : Response<ProductDetails>

    @FormUrlEncoded
    @POST(Constants.POST)
    suspend fun addProductDetails(
        @Field("product_name") product_name : String,
        @Field("product_type") product_type : String,
        @Field("price") price : Double,
        @Field("tax") tax : Double,
    ) : Response<AddProductItem>
}