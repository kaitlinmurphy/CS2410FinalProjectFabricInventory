package com.example.cs2410_finalproject_fabricinventory.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.GetContent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs2410_finalproject_fabricinventory.R;
import com.example.cs2410_finalproject_fabricinventory.viewmodels.FabricEntriesViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreateOrUpdateFabricEntryFragment extends Fragment {

    private boolean previouslySaving = false;

    private Uri pictureUri;

    public CreateOrUpdateFabricEntryFragment() {
        super(R.layout.fragment_create_fabric_entry);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FabricEntriesViewModel viewModel = new ViewModelProvider(getActivity()).get(FabricEntriesViewModel.class);

        viewModel.getCurrentEntry().observe(getViewLifecycleOwner(), (entry) -> {
            if (entry != null) {
                TextInputEditText fabricNameText = view.findViewById(R.id.fabric_name_display);
                TextInputEditText fabricLineNameText = view.findViewById(R.id.fabric_line_name_display);
                EditText fabricAmountText = view.findViewById(R.id.fabric_amount_text);
                EditText fabricPriceText = view.findViewById(R.id.fabric_price_text);
                TextInputEditText fabricStorePurchasedAtText = view.findViewById(R.id.fabric_store_purchased_text);
                TextInputEditText fabricAdditionalNotesText = view.findViewById(R.id.fabric_add_notes_text);
                ImageView fabricImageView = view.findViewById(R.id.fabric_image_view);

                fabricNameText.setText(entry.fabricName);
                fabricLineNameText.setText(entry.fabricLineName);
                fabricAmountText.setText("" + entry.amount);
                fabricPriceText.setText("" + entry.price);
                fabricStorePurchasedAtText.setText(entry.storePurchasedAt);
                fabricAdditionalNotesText.setText(entry.additionalNotes);
                if(entry.pictureUri != null && !entry.pictureUri.isEmpty()) {
                    pictureUri = Uri.parse(entry.pictureUri);
                    fabricImageView.setImageURI(pictureUri);
                }
            }
        });

        viewModel.getSaving().observe(getViewLifecycleOwner(), (saving) -> {
            if(saving && !previouslySaving) {
//                MaterialButton button = view.findViewById(R.id.save_button);
//                button.setEnabled(false);
//                button.setText("Saving...");
                previouslySaving = saving;
            }
            else if (previouslySaving && !saving) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // TODO actually do this
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new GetContent(),
                uri -> {
                    ImageView imageView = view.findViewById(R.id.fabric_image_view);
                    pictureUri = uri;
                    imageView.setImageURI(pictureUri);
                });

        view.findViewById(R.id.add_picture_fab).setOnClickListener(addPictureButton -> {

            mGetContent.launch("image/*");
        });

        view.findViewById(R.id.save_button).setOnClickListener(saveButton -> {
            TextInputEditText fabricNameText = view.findViewById(R.id.fabric_name_display);
            TextInputEditText fabricLineNameText = view.findViewById(R.id.fabric_line_name_display);
            EditText fabricAmountText = view.findViewById(R.id.fabric_amount_text);
            EditText fabricPriceText = view.findViewById(R.id.fabric_price_text);
            TextInputEditText fabricStorePurchasedAtText = view.findViewById(R.id.fabric_store_purchased_text);
            TextInputEditText fabricAdditionalNotesText = view.findViewById(R.id.fabric_add_notes_text);
            ImageView fabricImageView = view.findViewById(R.id.fabric_image_view);

            boolean valid = validateEntries(fabricNameText.getText().toString(),
                    fabricLineNameText.getText().toString(),
                    fabricAmountText.getText().toString(),
                    fabricPriceText.getText().toString(),
                    fabricStorePurchasedAtText.getText().toString(),
                    fabricAdditionalNotesText.getText().toString());

            if (valid) {

                double fabricAmount;
                if (fabricAmountText.getText().toString().isEmpty()) {
                    fabricAmount = -1;
                } else {
                    fabricAmount = Double.parseDouble(fabricAmountText.getText().toString());
                }

                double fabricPrice;
                if (fabricPriceText.getText().toString().isEmpty()) {
                    fabricPrice = -1;
                } else {
                    fabricPrice = Double.parseDouble(fabricPriceText.getText().toString());
                }

                viewModel.saveFabricEntry(fabricNameText.getText().toString(),
                        fabricLineNameText.getText().toString(),
                        fabricAmount,
                        fabricPrice,
                        fabricStorePurchasedAtText.getText().toString(),
                        fabricAdditionalNotesText.getText().toString(),
                        pictureUri.toString()
                );
            }
        });
    }

    private boolean validateEntries(String fabricNameText, String fabricLineNameText, String fabricAmountText,
                                    String fabricPriceText, String fabricStorePurchasedAtText,
                                    String fabricAdditionalNotesText) {
        // required field: fabricNameText
        if (fabricNameText.isEmpty()) {
            return false;
        }

        if(!fabricAmountText.isEmpty()) {
            try {
                Double.parseDouble(fabricAmountText);
            } catch( NumberFormatException e) {
                return false;
            }
        }

        if(!fabricPriceText.isEmpty()) {
            try {
                Double.parseDouble(fabricPriceText);
            } catch( NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

}
