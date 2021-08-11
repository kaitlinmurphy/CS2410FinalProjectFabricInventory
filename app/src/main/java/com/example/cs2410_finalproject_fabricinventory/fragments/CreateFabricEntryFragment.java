package com.example.cs2410_finalproject_fabricinventory.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs2410_finalproject_fabricinventory.R;
import com.example.cs2410_finalproject_fabricinventory.viewmodels.FabricEntriesViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CreateFabricEntryFragment extends Fragment {

    public CreateFabricEntryFragment() {
        super(R.layout.fragment_create_fabric_entry);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FabricEntriesViewModel viewModel = new ViewModelProvider(getActivity()).get(FabricEntriesViewModel.class);

        view.findViewById(R.id.save_button).setOnClickListener(saveButton -> {
            TextInputEditText fabricNameText = view.findViewById(R.id.fabric_name_text);
            TextInputEditText fabricLineNameText = view.findViewById(R.id.fabric_line_name_text);
            EditText fabricAmountText = view.findViewById(R.id.fabric_amount_text);
            EditText fabricPriceText = view.findViewById(R.id.fabric_price_text);
            TextInputEditText fabricStorePurchasedAtText = view.findViewById(R.id.fabric_store_purchased_text);
            TextInputEditText fabricAdditionalNotesText = view.findViewById(R.id.fabric_add_notes_text);
            viewModel.saveFabricEntry(fabricNameText.getText().toString(),
                    fabricLineNameText.getText().toString(),
                    Double.parseDouble(fabricAmountText.getText().toString()),
                    Double.parseDouble(fabricPriceText.getText().toString()),
                    fabricStorePurchasedAtText.getText().toString(),
                    fabricAdditionalNotesText.getText().toString()
                    );
        });
    }

}
