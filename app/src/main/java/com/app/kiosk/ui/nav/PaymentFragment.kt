package com.app.kiosk.ui.nav


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.app.kiosk.R
import com.app.kiosk.databinding.FragmentPaymentBinding
import com.app.kiosk.util.Utils.showShortToast
import com.app.kiosk.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    // ViewBinding variable
    private lateinit var binding: FragmentPaymentBinding // Replace with your actual Fragment layout binding class
    private var totalAmount: Double = 100.00 // Example total amount
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        // Get total price from arguments
        arguments?.let {
            val sampleName = it.getString("totalPrice", "0.00")
            totalAmount = sampleName.toDouble()
        }

        // Display total amount
        updateTotalAmount()

        // Handle Pay Now button click
        binding.payNowButton.setOnClickListener {
            val selectedOptionId = binding.paymentOptionsGroup.checkedRadioButtonId

            if (selectedOptionId != -1) {
                val selectedRadioButton = binding.root.findViewById<RadioButton>(selectedOptionId)
                val paymentMethod = selectedRadioButton.text.toString()

                cartViewModel.clearCart()

                showShortToast(requireContext(), "Payment successfully")

                val navController = findNavController(requireView())

                val navOptions = NavOptions.Builder()
                    .setPopUpTo(navController.graph.startDestinationId, true)
                    .build()

                navController.navigate(R.id.mainFragment, null, navOptions)

            } else {
                showShortToast(requireContext(), "Please select a payment method")
            }
        }
    }

    // Update total amount display
    private fun updateTotalAmount() {
        binding.totalAmount.text = String.format("$%.2f", totalAmount)
    }


}

