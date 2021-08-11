package com.example.cs2410_finalproject_fabricinventory.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cs2410_finalproject_fabricinventory.models.FabricEntry;

public class FabricEntriesViewModel extends AndroidViewModel {
    public FabricEntriesViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveFabricEntry(String fabricNameText, String fabricLineNameText, double fabricAmountText,
                                double fabricPriceText, String fabricStorePurchasedAtText,
                                String fabricAdditionalNotesText) {
        FabricEntry newEntry = new FabricEntry();
        newEntry.fabricName = fabricNameText;
        newEntry.fabricLineName = fabricLineNameText;
        newEntry.amount = fabricAmountText;
        newEntry.price = fabricPriceText;
        newEntry.storePurchasedAt = fabricStorePurchasedAtText;
        newEntry.additionalNotes = fabricAdditionalNotesText;
        newEntry.createdAt = System.currentTimeMillis();

    }
}
