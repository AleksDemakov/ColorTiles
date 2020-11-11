package com.example.colortiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {
    int rowsNumber, columnsNumber;
    boolean checkHints;
    TilesView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        rowsNumber = intent.getIntExtra("rowsNumber", 3);
        columnsNumber = intent.getIntExtra("columnsNumber", 3);
        checkHints = intent.getBooleanExtra("checkHints", false);
        view = (TilesView) findViewById(R.id.tiles);
        view.setSize(rowsNumber, columnsNumber, checkHints);

    }

    @Override
    protected void onStop() {
        super.onStop();
        view.hintTimer.cancel();
    }
}