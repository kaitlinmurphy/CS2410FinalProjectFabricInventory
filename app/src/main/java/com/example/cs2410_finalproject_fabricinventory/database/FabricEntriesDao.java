package com.example.cs2410_finalproject_fabricinventory.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cs2410_finalproject_fabricinventory.models.FabricEntry;

import java.util.List;

@Dao
public interface FabricEntriesDao {
    @Insert
    public long insert(FabricEntry entry);

    @Query("SELECT * FROM fabricentry")
    public List<FabricEntry> getAll();

    @Query("SELECT * FROM fabricentry WHERE id = :id LIMIT 1")
    public FabricEntry findByID(long id);

    @Update
    public void update(FabricEntry entry);

    @Delete
    public void delete(FabricEntry entry);

}
