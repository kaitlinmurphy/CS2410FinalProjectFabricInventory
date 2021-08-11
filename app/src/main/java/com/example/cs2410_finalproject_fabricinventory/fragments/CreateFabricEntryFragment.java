package com.example.cs2410_finalproject_fabricinventory.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cs2410_finalproject_fabricinventory.R;
import com.google.android.material.textfield.TextInputEditText;

public class CreateFabricEntryFragment extends Fragment {

    public CreateFabricEntryFragment() {
        super(R.layout.fragment_create_fabric_entry);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.save_button).setOnClickListener(saveButton -> {
            TextInputEditText fabricNameText = view.findViewById(R.id.fabric_name_text);
            TextInputEditText fabricLineNameText = view.findViewById(R.id.fabric_line_name_text);
            TextInputEditText fabricAmountText = view.findViewById(R.id.fabric_amount_text);
            TextInputEditText fabricPriceText = view.findViewById(R.id.fabric_price_text);
            TextInputEditText fabricStorePurchasedAtText = view.findViewById(R.id.fabric_store_purchased_text);
            TextInputEditText fabricAdditionalNotesText = view.findViewById(R.id.fabric_add_notes_text);
        });
    }

}
