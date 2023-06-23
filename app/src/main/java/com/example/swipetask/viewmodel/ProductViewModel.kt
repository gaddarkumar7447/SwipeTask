package com.example.swipetask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipetask.model.ProductDetails
import com.example.swipetask.modelproductadd.AddProductItem
import com.example.swipetask.repo.Repository
import com.example.swipetask.utilities.ApiResponce
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: Repository) : ViewModel() {

    val liveData: LiveData<ApiResponce<ProductDetails>> = repository.liveData
    fun getLiveData(){
        viewModelScope.launch {
            repository.getLiveData()
        }
    }

    // for adding product item
    private val addProductData : MutableLiveData<ApiResponce<AddProductItem>> = MutableLiveData()
    val productLiveData : LiveData<ApiResponce<AddProductItem>> = addProductData

    fun addProductDetails(productName : String, productType: String, price : Double, tax : Double){
        viewModelScope.launch {
            addProductData.postValue(ApiResponce.Loading())
            try {
                val responce = repository.addProductData(productName, productType, price, tax).body()
                if (responce?.success == true){
                    addProductData.postValue(ApiResponce.Successful(responce))
                }else{
                    addProductData.postValue(ApiResponce.Error("Not adding"))
                }
            }catch (e : Exception){
                addProductData.postValue(ApiResponce.Error("Somethings went to wrong"))
            }
        }
    }
}