package com.example.swipetask.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swipetask.adapter.ProductAdapter
import com.example.swipetask.databinding.ActivityMainBinding
import com.example.swipetask.model.ProductDetails
import com.example.swipetask.model.ProductDetailsItem
import com.example.swipetask.network.ApiInstance
import com.example.swipetask.network.ApiServices
import com.example.swipetask.repo.Repository
import com.example.swipetask.utilities.ApiResponce
import com.example.swipetask.viewmodel.ProductViewModel
import com.example.swipetask.viewmodel.ProductViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    lateinit var productDetailsItem : List<ProductDetailsItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewModel()

        viewModel.getLiveData()
        getProductData()

        productDetailsItem = emptyList()

        searchData()

        binding.addItem.setOnClickListener { startActivity(Intent(this, AddItemActivity::class.java)) }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getProductData() {
        viewModel.liveData.observe(this, Observer {
            when(it){
                is ApiResponce.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponce.Successful ->{
                    productDetailsItem = it.data!!

                    productAdapter = ProductAdapter(it.data)
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.recyclerView.adapter = productAdapter
                    binding.recyclerView.setHasFixedSize(true)
                    productAdapter.notifyDataSetChanged()

                    binding.progressBar.visibility = View.GONE
                }
                is ApiResponce.Error ->{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun initializeViewModel() {
        val apiInstance = ApiInstance.getApiIntence().create(ApiServices::class.java)
        viewModel = ViewModelProvider(this, ProductViewModelFactory(Repository(apiInstance, this)))[ProductViewModel::class.java]
    }

    private fun searchData() {
        binding.searchData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val filteredUsers = productDetailsItem.filter { data ->
                    data.product_name.contains(p0.toString()) || data.product_type.contains(p0.toString())
                }
                if (filteredUsers.isEmpty()) {
                    Snackbar.make(binding.root, "No data found ${p0.toString()}", Snackbar.LENGTH_LONG).show()
                }

                productAdapter = ProductAdapter(filteredUsers)
                binding.recyclerView.adapter = productAdapter
                binding.recyclerView.setHasFixedSize(true)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
    override fun onRestart() {
        super.onRestart()
        viewModel.getLiveData()
    }
}

