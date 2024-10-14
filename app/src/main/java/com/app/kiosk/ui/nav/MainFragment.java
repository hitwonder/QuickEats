package com.app.kiosk.ui.nav;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.kiosk.ui.OnItemClickListener;
import com.app.kiosk.R;
import com.app.kiosk.adapter.ItemAdapter;
import com.app.kiosk.databinding.FragmentMainBinding;
import com.app.kiosk.model.Item;
import com.app.kiosk.viewmodel.ItemViewModel;
import com.google.gson.Gson;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment implements OnItemClickListener {

    private FragmentMainBinding binding;
    private ItemAdapter itemAdapter;
    private ItemViewModel itemViewModel;

    private boolean isEnable = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using view binding
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView
        RecyclerView recyclerView = binding.recyclerView; // Assuming recycler_view ID in the layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //  EditText et_search = binding.etSearch;

        // Initialize ItemAdapter and set it to RecyclerView
        itemAdapter = new ItemAdapter(this);
        recyclerView.setAdapter(itemAdapter);

        // Initialize ViewModel using ViewModelProvider
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        // Observe LiveData from ViewModel
        itemViewModel.getItemsLiveData().observe(getViewLifecycleOwner(), items -> {
            if (items != null && !items.isEmpty()) {
                // Hide progress bar if data is available
                binding.progressBar.setVisibility(View.GONE);
                // Update the adapter with the items
                Log.d("TAG", "onViewCreated: items " + items.size());
                itemAdapter.setItems(items);
            } else {
                // Show the progress bar if no items are available
                binding.progressBar.setVisibility(View.VISIBLE);
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No implementation needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("TAG", "onTextChanged changed ");
                if (isEnable) {
                    itemAdapter.getFilter().filter(s.toString());
                } else {
                    isEnable = true;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // No implementation needed
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }


    @Override
    public void onItemClick(Item item) {
        Gson gson = new Gson();
        String itemJson = gson.toJson(item);

        Bundle bundle = new Bundle();
        bundle.putString("selectedItem", itemJson);

        // Ensure to use a View to find NavController
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.itemDetailFragment, bundle);
    }

    @Override
    public void toggleNoResultsMessage(boolean isEmpty) {
        if (isEmpty) {
            binding.errorMessage.setVisibility(View.VISIBLE);
        } else {
            binding.errorMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isEnable) {
            itemAdapter.getFilter().filter("");
            binding.etSearch.setText("");
        }
    }


}
