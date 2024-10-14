package com.app.kiosk.ui.nav


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kiosk.R
import com.app.kiosk.adapter.CartAdapter
import com.app.kiosk.databinding.FragmentOrderSummaryBinding
import com.app.kiosk.util.Utils
import com.app.kiosk.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderSummaryFragment : Fragment() {

    private lateinit var binding: FragmentOrderSummaryBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private var getTotalPrice: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Show progress bar while loading cart items
        binding.progressBar.visibility = View.VISIBLE

        // Observe cart items and update RecyclerView
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            // Hide progress bar once data is loaded
            binding.progressBar.visibility = View.GONE

            // Submit list to the adapter
            cartAdapter.submitList(cartItems)

            // Show RecyclerView if items are present
            if (cartItems.isNotEmpty()) {
                binding.cartRecyclerView.visibility = View.VISIBLE
                binding.emptyCartMessage.visibility = View.GONE
                binding.floatingActionContainer.visibility = View.VISIBLE // Show Place Order button
            } else {
                binding.cartRecyclerView.visibility = View.GONE
                binding.emptyCartMessage.visibility = View.VISIBLE // Show empty message
                binding.floatingActionContainer.visibility = View.GONE // Hide Place Order button
            }
        }

        // Observe total price and update UI
        cartViewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.addToCartButton.text = "Total: $$totalPrice"
            getTotalPrice = totalPrice
        }

        binding.addToCartButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("totalPrice", getTotalPrice.toString())

            // Ensure to use a View to find NavController
            val navController = findNavController(requireView())
            navController.navigate(R.id.paymentFragment, bundle)
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onRemoveClick = { cartItem ->
                Utils.showShortToast(requireContext(),"Item Removed Successfully")
                cartViewModel.removeFromCart(cartItem)
            }
        )

        binding.cartRecyclerView.apply {
            // Set the layout manager for the RecyclerView
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }
}
