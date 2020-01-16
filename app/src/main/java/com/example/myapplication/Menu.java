package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    private Button btnEdit, btnScan;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnEdit = findViewById(R.id.btnEdit);
        btnScan = findViewById(R.id.btnScan);
        mDatabaseHelper = new DatabaseHelper(this);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaseHelper.count() == 0){
                    startActivity(new Intent(Menu.this, MainActivity.class));
                }else{
                    startActivity(new Intent(Menu.this, Edit.class));
                }
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaseHelper.count() == 0){
                    startActivity(new Intent(Menu.this, MainActivity.class));
                }else{
                    startActivity(new Intent(Menu.this, Scan.class));
                }
            }
        });
    }//end of on create
}
