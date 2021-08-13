package com.example.cs2410_finalproject_fabricinventory.fragments;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs2410_finalproject_fabricinventory.R;
import com.example.cs2410_finalproject_fabricinventory.viewmodels.FabricEntriesViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

                if (entry.fabricLineName.isEmpty()) {
                    fabricLineNameView.setText("From Line: N/A");
                }
                else {
                    fabricLineNameView.setText("From Line: " + entry.fabricLineName);
                }

                if (entry.amount < 0) {
                    fabricAmountView.setText("Amount: ??? yards");
                } else {
                    fabricAmountView.setText("Amount: " + entry.amount + " yards");
                }

                if (entry.price < 0) {
                    fabricPriceView.setText("Price: $ ??? per yard");
                } else {
                    fabricPriceView.setText("Price: $" + entry.price + " per yard");
                }

                if (entry.storePurchasedAt.isEmpty()) {
                    fabricPurchasedAtView.setText("From Store: N/A");
                }
                else {
                    fabricPurchasedAtView.setText("From Store: " + entry.storePurchasedAt);
                }

                fabricAdditionalNotesView.setText("Additional Notes: \n" + entry.additionalNotes);

                Log.d("Loading picture", entry.pictureUri);

                if(entry.pictureUri != null && !entry.pictureUri.isEmpty()) {
                    fabricImageView.setImageURI(Uri.parse(entry.pictureUri));
                }
            }
        });

        view.findViewById(R.id.floating_delete_button).setOnClickListener((fab) -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Deleting Fabric Entry")
                    .setMessage("Are you sure you want to delete this fabric from your library?")
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }
                            // respond to cancel
                    )
                    .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.deleteCurrentEntry();
                                }
                            }
                            // respond to cancel
                    );
            builder.create().show();
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
