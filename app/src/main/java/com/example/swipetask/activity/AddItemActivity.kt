package com.example.swipetask.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.swipetask.databinding.ActivityAddItemBinding
import com.example.swipetask.model.ProductDetailsItem
import com.example.swipetask.network.ApiInstance
import com.example.swipetask.network.ApiServices
import com.example.swipetask.repo.Repository
import com.example.swipetask.utilities.ApiResponce
import com.example.swipetask.viewmodel.ProductViewModel
import com.example.swipetask.viewmodel.ProductViewModelFactory

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private lateinit var viewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewModel()

        binding.submitButton.setOnClickListener {
            val name = binding.productName.text.toString()
            val type = binding.productType.text.toString()
            val prize = binding.productPrize.text.toString()
            val tax = binding.productTax.text.toString()

            if (name.isNotEmpty() && type.isNotEmpty() && prize.isNotEmpty() && tax.isNotEmpty()){
                viewModel.addProductDetails(ProductDetailsItem("",prize.toDouble(), name, type, tax.toDouble()))

                viewModel.productLiveData.observe(this, Observer {
                    when(it){
                        is ApiResponce.Loading ->{
                            binding.progressbar.visibility = View.VISIBLE
                        }
                        is ApiResponce.Successful ->{
                            binding.progressbar.visibility = View.GONE
                            binding.success.visibility = View.VISIBLE
                            binding.showResponceData.text = it.data.toString()
                        }
                        is ApiResponce.Error ->{
                            binding.progressbar.visibility = View.GONE
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }else{
                Toast.makeText(this, "please fill all details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeViewModel() {
        val apiInstance = ApiInstance.getApiIntence(this).create(ApiServices::class.java)
        viewModel = ViewModelProvider(this, ProductViewModelFactory(Repository(apiInstance)))[ProductViewModel::class.java]
    }
}