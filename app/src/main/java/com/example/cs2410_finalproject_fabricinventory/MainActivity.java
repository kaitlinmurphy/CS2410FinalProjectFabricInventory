package com.example.cs2410_finalproject_fabricinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.cs2410_finalproject_fabricinventory.fragments.FabricEntriesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, FabricEntriesFragment.class, null)
                    .commit();

            Log.d("fragment setting", "setting fragment to new fabric entry on opening");
        }
    }
}