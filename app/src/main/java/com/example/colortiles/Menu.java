package com.example.colortiles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    EditText rows;
    EditText columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        columns = (EditText) findViewById(R.id.columns);
        rows = (EditText) findViewById(R.id.rows);
    }

    public void startClick(View view){
        int rowsNumber = Integer.parseInt(rows.getText().toString());
        int columnsNumber = Integer.parseInt(columns.getText().toString());
//        if(Math.abs(rowsNumber - columnsNumber) >= 3){
//            Toast.makeText(this, "rows cannot differ from columns by more than 2", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if( columnsNumber >= 10 ){
//            Toast.makeText(this, "maximum number of columns is 9", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(rowsNumber >= 10){
//            Toast.makeText(this, "maximum number of rows is 9", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if( columnsNumber <= 1 ){
//            Toast.makeText(this, "minimum number of columns is 2", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(rowsNumber <= 1){
//            Toast.makeText(this, "minimum number of rows is 2", Toast.LENGTH_SHORT).show();
//            return;
//        }

        Intent intent = new Intent(this, MainActivity.class);


        boolean checkHints = ((CheckBox) findViewById(R.id.checkHints)).isChecked();
        intent.putExtra("rowsNumber", rowsNumber);
        intent.putExtra("columnsNumber", columnsNumber);
        intent.putExtra("checkHints", checkHints);
        startActivity(intent);

    }
}