package com.example.cs2410_finalproject_fabricinventory.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cs2410_finalproject_fabricinventory.models.FabricEntry;

@Database(entities = {FabricEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FabricEntriesDao getFabricEntriesDao();

}
