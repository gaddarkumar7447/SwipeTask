package com.example.swipetask.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.swipetask.model.ProductDetails
import com.example.swipetask.modelproductadd.AddProductItem
import com.example.swipetask.network.ApiServices
import com.example.swipetask.utilities.ApiResponce
import com.example.swipetask.utilities.Constants
import retrofit2.Response

class Repository(private val apiServices: ApiServices, private val context: Context) {
    private val mutableLiveData : MutableLiveData<ApiResponce<ProductDetails>> = MutableLiveData()
    val liveData: LiveData<ApiResponce<ProductDetails>> = mutableLiveData

    suspend fun getLiveData(){
        if (Constants.isNetworkAvailable(context)){
            try {
                mutableLiveData.postValue(ApiResponce.Loading())
                val data = apiServices.getProductList().body()
                if (data != null){
                    mutableLiveData.postValue(ApiResponce.Successful(data))
                }else{
                    mutableLiveData.postValue(ApiResponce.Error("Some error occurs"))
                }
            }catch (e : Exception){
                mutableLiveData.postValue(ApiResponce.Error("Somethings went to wrong"))
            }
        }else{
            mutableLiveData.postValue(ApiResponce.Error("No internet connection"))
        }
    }


    suspend fun addProductData(productName : String, productType: String, price : Double, tax : Double) : Response<AddProductItem>{
        return apiServices.addProductDetails(productName, productType, price, tax)
    }
}