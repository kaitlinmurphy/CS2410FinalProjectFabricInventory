package com.example.cs2410_finalproject_fabricinventory.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FabricEntry {
    @PrimaryKey(autoGenerate = true)
    long id;

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
    //Image???
}
