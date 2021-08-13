package com.example.cs2410_finalproject_fabricinventory.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.GetContent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs2410_finalproject_fabricinventory.DataBinderMapperImpl;
import com.example.cs2410_finalproject_fabricinventory.R;
import com.example.cs2410_finalproject_fabricinventory.viewmodels.FabricEntriesViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOrUpdateFabricEntryFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private static final int TAKE_PICTURE = 2;
    private boolean previouslySaving = false;
    String currentPhotoPath = "";

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
                    fabricImageView.setImageURI(Uri.parse(entry.pictureUri));
                }
            }
        });

        viewModel.getPictureUri().observe(getViewLifecycleOwner(), (pictureUri) -> {
            ImageView imageView = view.findViewById(R.id.fabric_image_view);
            imageView.setImageURI(pictureUri);
        });

        viewModel.getSaving().observe(getViewLifecycleOwner(), (saving) -> {
            if(saving && !previouslySaving) {
                previouslySaving = saving;
            }
            else if (previouslySaving && !saving) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        view.findViewById(R.id.add_picture_fab).setOnClickListener(addPictureButton -> {
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Choose Image")
                    .setItems(new CharSequence[]{"From Camera", "From Photos"}, (v, i) -> {
                        if (i == 0) {
                            handleTakePicturePress();
                        }
                        else {
                            handleSelectPicturePress();
                        }
                    })
                    .show();
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
                        viewModel.getPictureUri().toString()
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

    public void handleTakePicturePress() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + ".jpeg";
        File imageFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName);
        currentPhotoPath = imageFile.getAbsolutePath();
        Uri uri = FileProvider.getUriForFile(getActivity(), "com.example.fabricinventory.fileprovider", imageFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public void handleSelectPicturePress() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FabricEntriesViewModel viewModel = new ViewModelProvider(getActivity()).get(FabricEntriesViewModel.class);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            viewModel.handlePictureSelected(data.getData());
        }

        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            Object uriExtra = data.getExtras().get(MediaStore.EXTRA_OUTPUT);
            if(uriExtra instanceof Uri)
            viewModel.handlePictureSelected((Uri) uriExtra);
            else
                Log.e("CreateOrUpdateFabricEntryFragment", "Returned media extra of unexpected type");
        }

    }

}
