package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Scan extends AppCompatActivity implements  View.OnClickListener {
    //View Objects
    private Button buttonScan;
    private static String URL_INSERT = "http://192.168.22.149/LaravelQR_api/insert.php";
    private String currentEmail;
    private TextView textViewName, textViewAddress,textViewEmail;
    //qr code scanner object
    private IntentIntegrator qrScan;
    private DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        //View objects
        buttonScan = findViewById(R.id.buttonScan);
        textViewName = findViewById(R.id.textViewName);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewEmail = findViewById(R.id.textViewEmail);
        //initializing Scan object
        qrScan = new IntentIntegrator(this);

        // get curr user
        Cursor cursor = mDatabaseHelper.fetch();
        if(cursor.getCount() == 0){
            toastMessage(" NO DATA FOUND ");
        }else{
            while(cursor.moveToNext()){
                currentEmail = cursor.getString(1);
            }
        }
        //attaching onclick listener
        buttonScan.setOnClickListener(this);
    }

    //Getting the Scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qr code has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    //setting values to textviews
                    JSONObject obj = new JSONObject(result.getContents());
                        textViewEmail.setText(currentEmail);

                        String email = obj.getString("email");
                        String serial = obj.getString("serial");
                        textViewName.setText(email);
                        textViewAddress.setText(serial);
                        // insert to mysql
                        volleyInsert(email, serial, currentEmail);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view) {
        //initiating the qr code Scan
        qrScan.initiateScan();
    }

    public void volleyInsert(final String owner_email , final String serial , final String currentEmail){
        // gradle > implementation 'com.android.volley:volley:1.1.0' > MySQL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            Log.d(TAG, "param: " + jsonObject);
                            if(success.equals("1")){
                                toastMessage("Success");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            toastMessage("Error! Can't Insert"+e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastMessage("Error! Can't Insert !!"+error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("borrower_email",currentEmail );
                params.put("owner_item_serial", serial);
                params.put("owner_email", owner_email);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void toastMessage( String message ){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}