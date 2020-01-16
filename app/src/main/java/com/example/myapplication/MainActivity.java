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
    private Button btnRegisterEmail,btnMenu;
    private EditText editTextEmail;
    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegisterEmail = findViewById(R.id.btnRegisterEmail);
        btnMenu = findViewById(R.id.btnMenu);
        editTextEmail = findViewById(R.id.editTextEmail);
        mDatabaseHelper = new DatabaseHelper(this);

        if(mDatabaseHelper.count() == 0 ){
            btnRegisterEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newEntry = editTextEmail.getText().toString().trim();
                    if(mDatabaseHelper.count() == 0 ){
                        //if there is no email registered yet
                        if(editTextEmail.length() != 0){
                            AddData(newEntry);
                            editTextEmail.setText("");
                        }else{
                            toastMessage("Put Something in the field");
                        }
                    }else{
                        Toast.makeText(MainActivity.this,
                                "You can only register once. please edit your email if you want to change", Toast.LENGTH_LONG).show();

                        final Intent intent = new Intent(MainActivity.this , Menu.class);
                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        thread.start();
                    }
                }
            });
        }else{
            startActivity(new Intent(this, Menu.class));
        }
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , Menu.class));
            }
        });
    }// end of on create

    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if(insertData){
            toastMessage("Data Inserted Successfully");
            startActivity(new Intent(this, Menu.class));
        }else{
            toastMessage("Something went wrong!");
        }
    }
    private void toastMessage( String message ){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}