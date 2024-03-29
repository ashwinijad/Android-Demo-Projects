package com.example.contactapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.contactapp.DbHelper;
import com.example.contactapp.R;
import com.example.contactapp.ViewModel.Viewmodel;

public class AddEdit extends AppCompatActivity {

    EditText txt_id, txt_name, txt_address;
    Button btn_submit, btn_cancel;
    DbHelper SQLite = new DbHelper(this);
    String id, name, address;
    Viewmodel viewmodel;

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewmodel = ViewModelProviders.of(this).get(Viewmodel.class);

        txt_id = (EditText) findViewById(R.id.txt_id);
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_address = (EditText) findViewById(R.id.txt_address);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);

        if (id == null || id == "") {
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            txt_id.setText(id);
            txt_name.setText(name);
            txt_address.setText(address);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (txt_id.getText().toString().equals("")) {
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e){
                    Log.e("Submit", e.toString());
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Make blank all Edit Text
    private void blank() {
        txt_name.requestFocus();
        txt_id.setText(null);
        txt_name.setText(null);
        txt_address.setText(null);
    }

    // Save data to SQLite database
    private void save() {
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            // SQLite.insert(txt_name.getText().toString().trim(), txt_address.getText().toString().trim());
            viewmodel.insert(txt_name.getText().toString().trim(), txt_address.getText().toString().trim());
   /*         Intent intent = new Intent(AddEdit.this, AddEdit.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_NAME, name);
            intent.putExtra(TAG_ADDRESS, address);
            setResult(RESULT_OK, intent);
            startActivity(intent)*/;
            name=txt_name.getText().toString().trim();
            address=txt_address.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra("namee", name);
            intent.putExtra("addresss", address);
            setResult(RESULT_OK, intent);
            blank();
            finish();
        }
    }

    // Update data in SQLite database
    private void edit() {
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
           /* SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()), txt_name.getText().toString().trim(),
                    txt_address.getText().toString().trim());*/
            viewmodel.update(Integer.parseInt(txt_id.getText().toString().trim()), txt_name.getText().toString().trim(),
                    txt_address.getText().toString().trim());
            name=txt_name.getText().toString().trim();
            address=txt_address.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra("namee", name);
            intent.putExtra("addresss", address);
            setResult(RESULT_OK, intent);
            blank();
            finish();
        }
    }



}

