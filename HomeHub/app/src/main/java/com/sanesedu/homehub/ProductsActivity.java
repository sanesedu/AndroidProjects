package com.sanesedu.homehub;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    String shopName;
    String departmentName;
    ListView productsListView;
    ArrayList<Product> products;
    ArrayList<String> shops;
    ArrayList<String> department;
    ProductListAdapter adapter;

    public void createProduct (View view){
        Intent intent = new Intent(ProductsActivity.this, ProductCreationActivity.class);
        intent.putExtra("shopName", shopName);
        intent.putExtra("departmentName", departmentName);
        startActivity(intent);
    }

    public void updateListView (){
        if (products.size() > 0){
            adapter.clear();
        }

        Cursor c = MainActivity.database.rawQuery("SELECT * FROM products WHERE department='" + departmentName +"' AND shop ='" + shopName + "'", null);
        int productIndex = c.getColumnIndex("product");
        int quantityIndex = c.getColumnIndex("quantity");
        int departmentIndex = c.getColumnIndex("department");
        int shopIndex = c.getColumnIndex("shop");
        int alertQuantityIndex = c.getColumnIndex("alertQuantity");
        while (c.moveToNext()){
            products.add(new Product(c.getString(productIndex), c.getString(departmentIndex), c.getString(shopIndex), c.getInt(quantityIndex), c.getInt(alertQuantityIndex)));
        }
        adapter.notifyDataSetChanged();
        c.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        android.app.ActionBar actionBar = getActionBar();
        try {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e){
            e.printStackTrace();
        }

        MainActivity.database.execSQL("CREATE TABLE IF NOT EXISTS products (product VARCHAR, department VARCHAR, shop VARCHAR, quantity INTEGER, alertQuantity INTEGER, id INTEGER PRIMARY KEY) ");
        Intent intent = getIntent();
        shopName = intent.getStringExtra("shopName");
        departmentName = intent.getStringExtra("departmentName");


        productsListView = (ListView) findViewById(R.id.productsListView);
        products = new ArrayList<>();
        adapter = new ProductListAdapter(getApplicationContext(), products);
        productsListView.setAdapter(adapter);

        updateListView();

        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = products.get(i);
                String productName = product.getProduct();
                int quantity = product.getQuantity();
                int alertQuantity = product.getAlertQuantity();
                Intent intent1 = new Intent(ProductsActivity.this, ProductCreationActivity.class);
                intent1.putExtra("shopName", shopName);
                intent1.putExtra("departmentName", departmentName);
                intent1.putExtra("productName", productName);
                intent1.putExtra("quantity", quantity);
                intent1.putExtra("alertQuantity", alertQuantity);
                startActivity(intent1);
                MainActivity.database.execSQL("DELETE FROM products WHERE product = '" + productName + "'");
            }
        });

        productsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Product product = products.get(i);
                final String productName= product.getProduct();

                new AlertDialog.Builder(ProductsActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete product")
                        .setMessage("The product will be deleted")
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.database.execSQL("DELETE FROM products WHERE product ='" + productName + "'");
                                updateListView();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return true;
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(ProductsActivity.this, DepartmentActivity.class);
        intent.putExtra("shopName", shopName);
        startActivity(intent);

        return true;
    }
}
