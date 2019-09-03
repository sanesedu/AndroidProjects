package com.sanesedu.homehub;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static com.sanesedu.homehub.R.id.shop;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // #9E9E9E for shop
    // #BDBDBD for department
    // #E0E0E0 for products

    ListView listView;
    ArrayList<String> shops;
    ArrayList<Shop> shopList;
    ShopListAdapter adapter;
    static SQLiteDatabase database;

    public void createDialog (View view){
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Create a new shop")
                .setMessage("Enter the name of the new shop")
                .setView(editText)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (TextUtils.isEmpty(editText.getText())){
                            editText.setError("Enter a shop");
                        } else {
                            database.execSQL("INSERT INTO shops (name) VALUES ('" + editText.getText().toString() + "')" );
                            updateListView();
                        }

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void updateListView(){

        if (shops.size() > 0){
            adapter.clear();
            shops.clear();
        }

        Cursor cursor = database.rawQuery("SELECT * FROM shops", null);
        int shopName = cursor.getColumnIndex("name");
        while (cursor.moveToNext()){
            shops.add(cursor.getString(shopName));
        }

        for (int i  = 0; i < shops.size(); i++){
            String shop = shops.get(i);

            int products = 0;
            int departments = 0;

            Cursor c = database.rawQuery("SELECT * FROM products WHERE shop ='" + shop + "' AND quantity > alertQuantity", null);
            while (c.moveToNext()){

                products++;

                Cursor d = database.rawQuery("SELECT * FROM departments WHERE shop = '" + shop + "'", null);
                while (d.moveToNext()){
                    departments++;
                }
                d.close();

            }
            c.close();

            if (products > 0 && departments > 0){
                shopList.add(new Shop(shop, departments, products, false, false));
            }

            Cursor e = database.rawQuery("SELECT * FROM products WHERE shop ='" + shop + "' AND quantity = alertQuantity", null);
            while (e.moveToNext()){

                products++;

                Cursor f = database.rawQuery("SELECT * FROM departments WHERE shop = '" + shop + "'", null);
                while (f.moveToNext()){
                    departments++;
                }
                f.close();

            }
            e.close();

            if (products > 0 && departments > 0){
                shopList.add(new Shop(shop, departments, products, true, false));
            }

            Cursor g = database.rawQuery("SELECT * FROM products WHERE shop ='" + shop + "' AND quantity = alertQuantity", null);
            while (g.moveToNext()){

                products++;

                Cursor h = database.rawQuery("SELECT * FROM departments WHERE shop = '" + shop + "'", null);
                while (h.moveToNext()){
                    departments++;
                }
                h.close();

            }
            g.close();

            if (products > 0 && departments > 0){
                shopList.add(new Shop(shop, departments, products, false, true));
            }

            if (products == 0 || departments == 0){
                shopList.add(new Shop(shop, departments, products, false, false));
            }

        }

        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = openOrCreateDatabase("Homehub", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS shops (name VARCHAR, id INTEGER PRIMARY KEY)");

        listView = (ListView) findViewById(R.id.listView);
        shops = new ArrayList<>();
        shopList = new ArrayList<>();

        adapter = new ShopListAdapter(getApplicationContext(), shopList);
        listView.setAdapter(adapter);
        updateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
                intent.putExtra("shopName", shops.get(i));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                final String name = shops.get(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete shop")
                        .setMessage("The shop will be deleted")
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.execSQL("DELETE FROM shops WHERE name ='" + name + "'");
                                updateListView();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_products) {

        } else if (id == R.id.nav_alerts) {
            Intent intent = new Intent(MainActivity.this, AlertsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_shopping) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
