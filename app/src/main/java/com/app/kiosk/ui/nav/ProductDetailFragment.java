package com.app.kiosk.ui.nav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.kiosk.R;
import com.app.kiosk.databinding.FragmentProductDetailsBinding;
import com.app.kiosk.model.CartItem;
import com.app.kiosk.model.Item; // Ensure the path is correct
import com.app.kiosk.util.Utils;
import com.app.kiosk.viewmodel.CartViewModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetailFragment extends Fragment {

    private FragmentProductDetailsBinding binding;
    private int quantity = 1; // Initial quantity
    private double getPrice = 0.00;
    private CartViewModel cartViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the JSON string from the arguments (assuming you pass it when navigating)
        String itemJson = getArguments() != null ? getArguments().getString("selectedItem") : null;

        // Convert the JSON string back to an Item object
        Gson gson = new Gson();
        Item item = gson.fromJson(itemJson, Item.class);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);


        if (item != null) {
            // Set the title
            getActivity().setTitle(item.getName());

            // Set item details using binding
            binding.itemNameDetail.setText(item.getName());
            binding.itemDescriptionDetail.setText(item.getDescription());
            binding.itemPriceDetail.setText("$" + item.getPrice());
            getPrice = item.getPrice();

            // Load image using Glide (if URL is available)
            Glide.with(this)
                    .load(item.getImageUrl())  // Assuming your Item model has getImageUrl()
                    .placeholder(R.drawable.ic_launcher_foreground)  // Placeholder image while loading
                    .into(binding.itemImageDetail);
        }

        // Handle add to cart button click
        binding.addToCartButton.setOnClickListener(v -> {
            assert item != null;
            item.setQuantity(quantity);

            CartItem cartItem = new CartItem(0, item.getName(), item.getDescription(), item.getImageUrl(), item.getPrice(), item.getQuantity());
            cartViewModel.addToCart(cartItem);

            // Ensure to use a View to find NavController
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.orderSummaryFragment);

        });

        // Set initial quantity to the TextView
        updateQuantityText();

        // Set OnClickListener for the Increase Quantity button
        binding.buttonIncreaseQuantity.setOnClickListener(v -> {
            quantity++;
            updateQuantityText();
        });

        // Set OnClickListener for the Decrease Quantity button
        binding.buttonDecreaseQuantity.setOnClickListener(v -> {
            if (quantity > 1) { // Prevent quantity from going below 1
                quantity--;
                updateQuantityText();
            }
        });
    }

    // Method to update the quantity displayed in the TextView
    private void updateQuantityText() {
        double totalPrice = Utils.INSTANCE.calculateTotalPrice(getPrice, quantity); // Adjust this line if needed

        binding.addToCartButton.setText("Add item $" + totalPrice);
        binding.quantityText.setText(String.valueOf(quantity));
    }
}
