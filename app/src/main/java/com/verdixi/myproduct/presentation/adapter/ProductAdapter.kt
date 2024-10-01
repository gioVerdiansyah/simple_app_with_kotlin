package com.verdixi.myproduct.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.verdixi.myproduct.databinding.ItemProductBinding
import com.verdixi.myproduct.domain.model.Product
import com.verdixi.myproduct.presentation.util.formatRupiah
import com.verdixi.myproduct.presentation.util.limitDescription

class ProductAdapter(private val listProduct: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, onItemClickCallback: OnItemClickCallback) {
            binding.tvItemName.text = product.name
            binding.tvItemPrice.text = formatRupiah(product.price.toDouble())
            binding.tvItemDescription.text = limitDescription(product.description, 10)
            Glide.with(binding.root.context)
                .load(product.thumbnail)
                .into(binding.imgItemPhoto)

            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val product = listProduct[position]
        holder.bind(product, onItemClickCallback)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Product)
    }
}