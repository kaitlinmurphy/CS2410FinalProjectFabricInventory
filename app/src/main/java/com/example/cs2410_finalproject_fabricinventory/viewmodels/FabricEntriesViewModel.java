package com.example.cs2410_finalproject_fabricinventory.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import com.example.cs2410_finalproject_fabricinventory.database.AppDatabase;
import com.example.cs2410_finalproject_fabricinventory.models.FabricEntry;

public class FabricEntriesViewModel extends AndroidViewModel {
    private AppDatabase database;
    public FabricEntriesViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(application, AppDatabase.class, "fabricdb").build();
    }

    public void saveFabricEntry(String fabricNameText, String fabricLineNameText, double fabricAmountText,
                                double fabricPriceText, String fabricStorePurchasedAtText,
                                String fabricAdditionalNotesText) {
        new Thread(() -> {

            FabricEntry newEntry = new FabricEntry();

            newEntry.fabricName = fabricNameText;
            newEntry.fabricLineName = fabricLineNameText;
            newEntry.amount = fabricAmountText;
            newEntry.price = fabricPriceText;
            newEntry.storePurchasedAt = fabricStorePurchasedAtText;
            newEntry.additionalNotes = fabricAdditionalNotesText;
            newEntry.createdAt = System.currentTimeMillis();
            newEntry.id = database.getFabricEntriesDao().insert(newEntry);
        }).start();

    }
}
