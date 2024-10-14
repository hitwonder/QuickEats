package com.app.kiosk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.kiosk.databinding.CartItemBinding
import com.app.kiosk.model.CartItem

class CartAdapter(
    private val onRemoveClick: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        // Inflate the layout using the generated CartItemBinding class
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        // Bind the cart item to the ViewHolder
        val cartItem = getItem(position)
        holder.bind(cartItem, onRemoveClick)
    }

    class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem, onRemoveClick: (CartItem) -> Unit) {
            // Set item details in the view using View Binding
            binding.itemNameText.text = cartItem.name
            binding.itemPriceText.text = cartItem.price.toString()
            binding.itemQuantityText.text = "Qty: ${cartItem.quantity}"

            // Handle the remove button click
            binding.removeItemButton.setOnClickListener {
                onRemoveClick(cartItem)
            }
        }
    }
}

class CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}
