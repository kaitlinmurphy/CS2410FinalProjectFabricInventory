package com.example.cs2410_finalproject_fabricinventory.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.cs2410_finalproject_fabricinventory.database.AppDatabase;
import com.example.cs2410_finalproject_fabricinventory.models.FabricEntry;

import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;

public class FabricEntriesViewModel extends AndroidViewModel {
    private AppDatabase database;
    private MutableLiveData<Boolean> saving = new MutableLiveData<>();
    private ObservableArrayList<FabricEntry> entries = new ObservableArrayList<>();
    private MutableLiveData<FabricEntry> currentEntry = new MutableLiveData<>();

    public FabricEntriesViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        database = Room.databaseBuilder(application, AppDatabase.class, "fabricdb").build();

        new Thread(() -> {
            ArrayList<FabricEntry> fabricEntries =
                    (ArrayList<FabricEntry>) database.getFabricEntriesDao().getAll();
            entries.addAll(fabricEntries);
        }).start();
    }

    public MutableLiveData<FabricEntry> getCurrentEntry() {
        return currentEntry;
    }

    public void setCurrentEntry(FabricEntry entry) {
        this.currentEntry.setValue(entry);
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }

    public ObservableArrayList<FabricEntry> getEntries() {
        return entries;
    }

    public void deleteCurrentEntry() {
        new Thread(() -> {
            database.getFabricEntriesDao().delete(currentEntry.getValue());
            entries.remove(currentEntry.getValue());
            currentEntry.postValue(null);
        }).start();
    }

    public void saveFabricEntry(String fabricNameText, String fabricLineNameText, double fabricAmountText,
                                double fabricPriceText, String fabricStorePurchasedAtText,
                                String fabricAdditionalNotesText) {
        saving.setValue(true);

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (currentEntry.getValue() != null) {
                // update existing entry
                FabricEntry current = currentEntry.getValue();
                current.fabricName = fabricNameText;
                current.fabricLineName = fabricLineNameText;
                current.amount = fabricAmountText;
                current.price = fabricPriceText;
                current.additionalNotes = fabricAdditionalNotesText;

                database.getFabricEntriesDao().update(current);
                currentEntry.postValue(current);

                int index = entries.indexOf(current);
                entries.set(index, current);
            }
            else {
                FabricEntry newEntry = new FabricEntry();

                newEntry.fabricName = fabricNameText;
                newEntry.fabricLineName = fabricLineNameText;
                newEntry.amount = fabricAmountText;
                newEntry.price = fabricPriceText;
                newEntry.storePurchasedAt = fabricStorePurchasedAtText;
                newEntry.additionalNotes = fabricAdditionalNotesText;
                newEntry.createdAt = System.currentTimeMillis();
                newEntry.id = database.getFabricEntriesDao().insert(newEntry);

                entries.add(newEntry);
                // put into a list
            }
            saving.postValue(false);
        }).start();

    }
}
