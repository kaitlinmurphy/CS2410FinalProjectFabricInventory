package com.example.cs2410_finalproject_fabricinventory.models;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FabricEntry {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String fabricName;

    @ColumnInfo
    public String fabricLineName;

    @ColumnInfo
    public double amount; // in yards

    @ColumnInfo
    public double price; // per yard

    @ColumnInfo
    public String storePurchasedAt;

    @ColumnInfo
    public String additionalNotes;

    @ColumnInfo(name= "created_at")
    public long createdAt;

    @ColumnInfo
    public String pictureUri;
}
