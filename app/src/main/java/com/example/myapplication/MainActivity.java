package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    //View Objects
    private Button btnRegisterEmail;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegisterEmail = (Button) findViewById(R.id.btnRegisterEmail);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        btnRegisterEmail.setOnClickListener(this);


    }// end of on create
    @Override
    public void onClick(View view) {
       //register email to local db

    }
}