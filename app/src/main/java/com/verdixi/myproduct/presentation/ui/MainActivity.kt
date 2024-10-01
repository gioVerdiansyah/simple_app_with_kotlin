package com.verdixi.myproduct.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.verdixi.myproduct.R
import com.verdixi.myproduct.databinding.ActivityMainBinding
import com.verdixi.myproduct.di.AppModule
import com.verdixi.myproduct.domain.model.Product
import com.verdixi.myproduct.presentation.adapter.ProductAdapter
import com.verdixi.myproduct.presentation.util.applyWindowInsets
import com.verdixi.myproduct.presentation.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val listProducts = ArrayList<Product>()
    private lateinit var viewModel: ProductViewModel
    private lateinit var skeleton: Skeleton
    private lateinit var handler: Handler
    private lateinit var timeoutRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        applyWindowInsets(binding.main)
        setupRecycleView()
        fetchProducts()
        showRecycleList()

        skeleton = binding.rvProducts.applySkeleton(R.layout.skeleton_item_product, 10).apply {
            showShimmer = true
            shimmerDurationInMillis = 1500
            shimmerAngle = 20
        }
        skeleton.showSkeleton()
    }

    private fun setupRecycleView() {
        with(binding.rvProducts) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchProducts() {
        viewModel = AppModule.provideProductViewModel()

        handler = Handler(Looper.getMainLooper())
        timeoutRunnable = Runnable {
            Toast.makeText(this, "Fetching timeout! Please reload the application.", Toast.LENGTH_SHORT).show()
        }

        viewModel.fetchProducts("1lnjQ7LbFTZtvdBx9amCxbIk4ypIWqwtb")

        viewModel.products.observe(this, Observer { productList ->
            handler.removeCallbacks(timeoutRunnable)

            if (productList != null && productList.isNotEmpty()) {
                listProducts.clear()
                listProducts.addAll(productList)
                binding.rvProducts.adapter?.notifyDataSetChanged()
                skeleton.showOriginal()
            } else {
                Toast.makeText(this, "No data available...", Toast.LENGTH_SHORT).show()
            }
        })

        handler.postDelayed(timeoutRunnable, 120000)
    }

    private fun showRecycleList() {
        val adapter = ProductAdapter(listProducts)
        binding.rvProducts.adapter = adapter

        adapter.setOnItemClickCallback(object : ProductAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Product) {
                showOnclickAction(data)
            }
        })
    }

    private fun showOnclickAction(product: Product) {
        val toDetail = Intent(this, ProductDetailActivity::class.java)
        toDetail.putExtra("detailLink", product.detailsLink)
        toDetail.putExtra("thumbnail", product.thumbnail)
        toDetail.putExtra("productName", product.name)
        toDetail.putExtra("price", product.price)
        toDetail.putExtra("description", product.description)
        startActivity(toDetail)
    }

    //    Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_me -> {
                startActivity(Intent(this, AboutMeActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}