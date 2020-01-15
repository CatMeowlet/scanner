package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //View Objects
    private Button btnRegisterEmail;
    private EditText editTextEmail;
    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegisterEmail = (Button) findViewById(R.id.btnRegisterEmail);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        btnRegisterEmail.setOnClickListener(this);
        mDatabaseHelper = new DatabaseHelper(this);

        btnRegisterEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = editTextEmail.getText().toString().trim();
                if(editTextEmail.length() != 0){
                    AddData(newEntry);
                    editTextEmail.setText("");
                }else{
                    toastMessage('You must put something in the field');
                }
            }
        });
    }// end of on create

    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if(insertData){
            toastMessage('Data Inserted Successfuly');
        }else{
            toastMessage('Something went wrong!');
        }
    }
    private void toastMessage( String message ){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}