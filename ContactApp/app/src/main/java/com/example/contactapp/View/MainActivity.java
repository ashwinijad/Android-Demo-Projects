package com.example.contactapp.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.contactapp.DbHelper;
import com.example.contactapp.Model.Data;
import com.example.contactapp.R;
import com.example.contactapp.View.Adapter;
import com.example.contactapp.View.AddEdit;
import com.example.contactapp.View.DetailsAct;
import com.example.contactapp.ViewModel.Viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    DbHelper SQLite = new DbHelper(this);
    Viewmodel viewmodel;
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLite = new DbHelper(getApplicationContext());
        viewmodel = ViewModelProviders.of(this).get(Viewmodel.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.list_view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                startActivity(intent);
            }
        });

        adapter = new Adapter(MainActivity.this, itemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final String name = itemList.get(position).getName();
                final String address = itemList.get(position).getAddress();
                Intent intent = new Intent(MainActivity.this, DetailsAct.class);
                intent.putExtra(TAG_ID, idx);
                intent.putExtra(TAG_NAME, name);
                intent.putExtra(TAG_ADDRESS, address);
                startActivity(intent);
            }
        });

        getAllData();

    }

    private void getAllData() {
        viewmodel.getAllNotes();
        viewmodel.allNotesLivedata.observe(this, new Observer<ArrayList<HashMap<String, String>>>() {
            @Override
            public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {
                for (int i = 0; i < hashMaps.size(); i++) {
                    String id = hashMaps.get(i).get(TAG_ID);
                    String poster = hashMaps.get(i).get(TAG_NAME);
                    String title = hashMaps.get(i).get(TAG_ADDRESS);

                    Data data = new Data();

                    data.setId(id);
                    data.setName(poster);
                    data.setAddress(title);

                    itemList.add(data);
                }

                adapter.notifyDataSetChanged();
            }


        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        viewmodel = ViewModelProviders.of(this).get(Viewmodel.class);
        getAllData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
