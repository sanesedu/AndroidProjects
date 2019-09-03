package com.sanesedu.homehub;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity {

    String shopName;
    ListView departmentListView;
    ArrayList<String> departments;
    ArrayList<String> shops;
    ArrayAdapter adapter;

    public void createDialog (View view){
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Create a new department")
                .setMessage("Enter the name of the new department")
                .setView(editText)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (TextUtils.isEmpty(editText.getText())){
                            editText.setError("Enter a department");
                        } else {
                            MainActivity.database.execSQL("INSERT INTO departments (department, shop) VALUES ('" + editText.getText().toString() + "', '" + shopName + "')" );
                            updateListView();
                        }

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void updateListView(){

        if(departments.size() > 0){
            adapter.clear();
        }
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM departments WHERE shop='" + shopName + "'", null);
        int departmentIndex = c.getColumnIndex("department");
        int shopIndex = c.getColumnIndex("shop");
        while (c.moveToNext()){
            departments.add(c.getString(departmentIndex));
            shops.add(c.getString(shopIndex));
        }
        adapter.notifyDataSetChanged();
        c.close();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        android.app.ActionBar actionBar = getActionBar();
        try {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e){
            e.printStackTrace();
        }

        MainActivity.database.execSQL("CREATE TABLE IF NOT EXISTS departments (department VARCHAR, shop VARCHAR, id INTEGER PRIMARY KEY) ");

        final Intent intent = getIntent();
        shopName = intent.getStringExtra("shopName");

        shops = new ArrayList<>();

        departmentListView = (ListView) findViewById(R.id.departmentListView);
        departments = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, departments);
        departmentListView.setAdapter(adapter);
        updateListView();

        departmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String department = departments.get(i);
                String shop = shops.get(i);
                Intent intent1 = new Intent(DepartmentActivity.this, ProductsActivity.class);
                intent1.putExtra("departmentName", department);
                intent1.putExtra("shopName", shop);
                startActivity(intent1);
            }
        });

        departmentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final String department = departments.get(i);

                new AlertDialog.Builder(DepartmentActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete department")
                        .setMessage("The department will be deleted")
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.database.execSQL("DELETE FROM departments WHERE department ='" + department + "'");
                                updateListView();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });

    }
}
