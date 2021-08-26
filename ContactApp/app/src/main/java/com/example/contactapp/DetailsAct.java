package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsAct extends AppCompatActivity {
    String id, name, address;
    TextView txt_id, txt_name;
    Button btn1,btn2;
    DbHelper SQLite = new DbHelper(this);

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deatilactivity);
        SQLite = new DbHelper(getApplicationContext());

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);
        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_name = (TextView) findViewById(R.id.txt_name);
        btn1 = (Button) findViewById(R.id.buttonedt);
        btn2 = (Button) findViewById(R.id.buttondel);
        txt_id.setText(name);
        txt_name.setText(address);
        btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(DetailsAct.this, AddEdit.class);
                                        intent.putExtra(TAG_ID, id);
                                        intent.putExtra(TAG_NAME, name);
                                        intent.putExtra(TAG_ADDRESS, address);
                                        //startActivity(intent);
                                        startActivityForResult(intent, 1);

                                    }
                                }
        );
        btn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        SQLite.delete(Integer.parseInt(id));
finish();
                                    }
                                }
        );

    }
    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
                id = getIntent().getStringExtra(MainActivity.TAG_ID);
                name = data.getExtras().getString("namee");
                address = data.getExtras().getString("addresss");
                // set text view with string
                txt_id.setText(name);
                txt_name.setText(address);
            }
        }
    }
}
