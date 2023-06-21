package com.example.swipetask.modelproductadd

data class AddProductItem(
    val message: String,
    val product_details: ProductDetails,
    val product_id: Int,
    val success: Boolean
)