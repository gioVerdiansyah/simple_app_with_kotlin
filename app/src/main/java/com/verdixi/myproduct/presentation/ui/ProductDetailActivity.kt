package com.verdixi.myproduct.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.createSkeleton
import com.verdixi.myproduct.databinding.ActivityProductDetailBinding
import com.verdixi.myproduct.di.AppModule.provideProductDetailViewModel
import com.verdixi.myproduct.domain.model.ProductDetail
import com.verdixi.myproduct.presentation.util.applyWindowInsets
import com.verdixi.myproduct.presentation.util.formatRupiah
import com.verdixi.myproduct.presentation.viewmodel.ProductDetailViewModel
import kotlin.properties.Delegates

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }
    private lateinit var detailLink: String
    private lateinit var thumbnail: String
    private lateinit var productName: String
    private lateinit var description: String
    private var price by Delegates.notNull<Double>()
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var skeleton: Skeleton
    private lateinit var handler: Handler
    private lateinit var timeoutRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        applyWindowInsets(binding.main)

        detailLink = intent.getStringExtra("detailLink").toString()
        thumbnail = intent.getStringExtra("thumbnail").toString()
        productName = intent.getStringExtra("productName").toString()
        description = intent.getStringExtra("description").toString()
        price = intent.getLongExtra("price", 0L).toDouble()

        skeleton = binding.content.createSkeleton()
        skeleton.showSkeleton()

        fetchProducts()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchProducts() {
        viewModel = provideProductDetailViewModel()

        handler = Handler(Looper.getMainLooper())
        timeoutRunnable = Runnable {
            Toast.makeText(this, "Fetching timeout! Please reload the application.", Toast.LENGTH_SHORT).show()
        }

        viewModel.fetchProductDetails(detailLink)

        viewModel.products.observe(this, Observer { detail ->
            handler.removeCallbacks(timeoutRunnable)

            Log.e("DETAIL: ", detail.toString())

            if (detail != null) {
                showDataToWidget(detail)
                skeleton.showOriginal()
            } else {
                Toast.makeText(this, "No data available...", Toast.LENGTH_SHORT).show()
            }
        })

        handler.postDelayed(timeoutRunnable, 120000)
    }

    @SuppressLint("SetTextI18n")
    private fun showDataToWidget(detail: ProductDetail){
        Glide.with(binding.root.context)
            .load(thumbnail)
            .into(binding.ivThumbnail)

        val priceLayoutParams = binding.tvPrice.layoutParams
        priceLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        binding.tvPrice.layoutParams = priceLayoutParams

        val productNameLayoutParams = binding.tvProductName.layoutParams
        productNameLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        binding.tvProductName.layoutParams = productNameLayoutParams

        val descriptionLayoutParams = binding.tvDescription.layoutParams
        descriptionLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        descriptionLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        binding.tvDescription.layoutParams = descriptionLayoutParams

        val categoryLayoutParams = binding.tvCategory.layoutParams as ConstraintLayout.LayoutParams
        categoryLayoutParams.width = 0
        binding.tvCategory.layoutParams = categoryLayoutParams

        binding.tvPrice.text = formatRupiah(price)
        binding.tvProductName.text = productName
        binding.tvSold.text = "Terjual: ${detail.sold}"
        binding.tvStock.text = "Stok: ${detail.stock}"
        binding.tvRating.text = "Rating: ${detail.rating}"
        binding.tvDescription.text = description
        binding.tvMaterial.text = "Material: ${detail.material}"
        binding.tvManufacture.text = "Produsen: ${detail.manufacturer}"
        binding.tvWarranty.text = "Garansi: ${detail.warranty}"
        binding.tvReleaseDate.text = "Tanggal rilis: ${detail.date}"
        binding.tvCategory.text = detail.category
    }

}