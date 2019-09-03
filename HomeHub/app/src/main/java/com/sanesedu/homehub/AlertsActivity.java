package com.sanesedu.homehub;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AlertsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView alertsListView;
    ArrayList<Product> alertProducts;
    ArrayAdapter adapter;


    public void updateListView(){

        if(alertProducts.size() > 0){
            adapter.clear();
        }
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM products WHERE quantity <= alertQuantity", null);
        int productIndex = c.getColumnIndex("product");
        int quantityIndex = c.getColumnIndex("quantity");
        int departmentIndex = c.getColumnIndex("department");
        int shopIndex = c.getColumnIndex("shop");
        int alertQuantityIndex = c.getColumnIndex("alertQuantity");
        while (c.moveToNext()){
            alertProducts.add(new Product(c.getString(productIndex), c.getString(departmentIndex), c.getString(shopIndex), c.getInt(quantityIndex), c.getInt(alertQuantityIndex)));
        }
        adapter.notifyDataSetChanged();
        c.close();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        alertsListView = (ListView) findViewById(R.id.alertsListView);
        alertProducts = new ArrayList<>();
        adapter = new ProductListAdapter(getApplicationContext(), alertProducts);
        alertsListView.setAdapter(adapter);

        updateListView();
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
        getMenuInflater().inflate(R.menu.alerts, menu);
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
            Intent intent = new Intent(AlertsActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_alerts) {

        } else if (id == R.id.nav_shopping) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
