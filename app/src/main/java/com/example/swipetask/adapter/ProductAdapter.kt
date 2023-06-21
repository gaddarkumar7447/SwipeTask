package com.example.swipetask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swipetask.databinding.ProductitemcardBinding
import com.example.swipetask.model.ProductDetailsItem

class ProductAdapter(private val lisItem : List<ProductDetailsItem>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        context = parent.context
        return ProductViewHolder(ProductitemcardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val itemPosition = lisItem[position]
        holder.productItemCardBinding.apply {
            nameProduct.text = "Name : ${itemPosition.product_name}"
            productPrise.text = "Prize : ${itemPosition.price}"
            productTax.text = "Tax : ${itemPosition.tax}"
            productType.text = "Type : ${itemPosition.product_type}"

            Glide.with(context).load(itemPosition.image).into(imageProduct)
        }
    }

    override fun getItemCount(): Int {
        return lisItem.size
    }
    class ProductViewHolder(val productItemCardBinding: ProductitemcardBinding) : RecyclerView.ViewHolder(productItemCardBinding.root)
}