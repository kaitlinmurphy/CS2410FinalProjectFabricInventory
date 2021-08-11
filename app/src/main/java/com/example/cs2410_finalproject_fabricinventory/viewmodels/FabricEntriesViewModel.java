package com.example.cs2410_finalproject_fabricinventory.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class FabricEntriesViewModel extends AndroidViewModel {
    public FabricEntriesViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveFabricEntry(String fabricNameText, String fabricLineNameText, String fabricAmountText,
                                String fabricPriceText, String fabricStorePurchasedAtText,
                                String fabricAdditionalNotesText) {

    }
}
