package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Edit extends AppCompatActivity {

    private EditText editTextEmail;
    private Button btnUpdate;
    private String email;
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnUpdate = findViewById(R.id.btnUpdate);
        Cursor cursor = mDatabaseHelper.fetch();

        if(cursor.getCount() == 0){
            toastMessage(" NO DATA FOUND ");
        }else{
            while(cursor.moveToNext()){
                 email = cursor.getString(1);
                 id = cursor.getInt(0);
                editTextEmail.setText(email);
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.updateData(editTextEmail.getText().toString().trim(), id );
                startActivity(new Intent(Edit.this, Menu.class));
            }
        });
    }//end of oncreate
    private void toastMessage( String message ){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
