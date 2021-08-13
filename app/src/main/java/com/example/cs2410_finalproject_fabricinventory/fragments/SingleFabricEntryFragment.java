package com.example.cs2410_finalproject_fabricinventory.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs2410_finalproject_fabricinventory.R;
import com.example.cs2410_finalproject_fabricinventory.viewmodels.FabricEntriesViewModel;

public class SingleFabricEntryFragment extends Fragment {

    public SingleFabricEntryFragment() {
        super(R.layout.fragment_single_fabric_entry);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FabricEntriesViewModel viewModel = new ViewModelProvider(getActivity()).get(FabricEntriesViewModel.class);

        viewModel.getCurrentEntry().observe(getViewLifecycleOwner(), (entry) -> {
            if (entry == null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
            else {
                TextView fabricNameView = view.findViewById(R.id.fabric_name_display);
                TextView fabricLineNameView = view.findViewById(R.id.fabric_line_name_display);
                TextView fabricAmountView = view.findViewById(R.id.fabric_amount_display);
                TextView fabricPriceView = view.findViewById(R.id.fabric_price_display);
                TextView fabricPurchasedAtView = view.findViewById(R.id.fabric_purchased_at_display);
                TextView fabricAdditionalNotesView = view.findViewById(R.id.fabric_additional_notes_display);
                ImageView fabricImageView = view.findViewById(R.id.fabric_image_display);

                fabricNameView.setText(entry.fabricName);
                fabricLineNameView.setText("From Line: " + entry.fabricLineName);

                if (entry.amount < 0) {
                    fabricAmountView.setText("??? yards");
                } else {
                    fabricAmountView.setText(entry.amount + " yards");
                }

                if (entry.price < 0) {
                    fabricPriceView.setText("$ ??? per yard");
                } else {
                    fabricPriceView.setText("$" + entry.price + " per yard");
                }

                fabricPurchasedAtView.setText("From Store: " + entry.storePurchasedAt);
                fabricAdditionalNotesView.setText("Additional Notes: \n" + entry.additionalNotes);

                if(entry.pictureUri != null && !entry.pictureUri.isEmpty()) {
                    fabricImageView.setImageURI(Uri.parse(entry.pictureUri));
                }
            }
        });

        view.findViewById(R.id.floating_delete_button).setOnClickListener((fab) -> {
            viewModel.deleteCurrentEntry();
        });

        view.findViewById(R.id.floating_edit_button).setOnClickListener((fab) -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, CreateOrUpdateFabricEntryFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
