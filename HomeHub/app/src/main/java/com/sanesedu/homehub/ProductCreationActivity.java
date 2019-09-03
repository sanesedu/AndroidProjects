package com.sanesedu.homehub;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProductCreationActivity extends AppCompatActivity {

    String shopName;
    String departmentName;
    String productName;
    TextView quantityTextView;
    TextView alertQuantityTextView;
    TextView shopNameTextView;
    TextView departmentNameTextView;
    Button createButton;
    int quantityAmount;
    int alertQuantityAmount;
    int previousQuantity;
    int previousAlertQuantity;
    EditText productEditText;

    public void increaseQuantity (View view){
        quantityAmount = Integer.valueOf(quantityTextView.getText().toString());
        quantityAmount++;
        quantityTextView.setText(Integer.toString(quantityAmount));

    }

    public void decreaseQuantity (View view){
        quantityAmount = Integer.valueOf(quantityTextView.getText().toString());
        if (quantityAmount > 0){
            quantityAmount--;
        }
        quantityTextView.setText(Integer.toString(quantityAmount));
    }

    public void decreaseAlertQuantity (View view){
        alertQuantityAmount = Integer.valueOf(alertQuantityTextView.getText().toString());
        if (alertQuantityAmount > 0){
            alertQuantityAmount--;
        }
        alertQuantityTextView.setText(Integer.toString(alertQuantityAmount));
    }

    public void increaseAlertQuantity (View view){
        alertQuantityAmount = Integer.valueOf(alertQuantityTextView.getText().toString());
        alertQuantityAmount++;
        alertQuantityTextView.setText(Integer.toString(alertQuantityAmount));
    }

    public void createProductInfo (View view){
        if (TextUtils.isEmpty(productEditText.getText())){
            productEditText.setError("Enter a product");
        } else {
            MainActivity.database.execSQL("INSERT INTO products (product, department, shop, quantity, alertQuantity) " +
                        "VALUES ('" + productEditText.getText().toString() + "', '" + departmentName + "', '" + shopName + "', " + quantityAmount + ", " + alertQuantityAmount + ")");
            Intent intent = new Intent(ProductCreationActivity.this, ProductsActivity.class);
            intent.putExtra("shopName", shopName);
            intent.putExtra("departmentName", departmentName);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_creation);

        android.app.ActionBar actionBar = getActionBar();
        try {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e){
            e.printStackTrace();
        }

        quantityTextView = (TextView) findViewById(R.id.quantityNumberTextView);
        alertQuantityTextView = (TextView) findViewById(R.id.alertQuantityNumberTextView);
        shopNameTextView = (TextView) findViewById(R.id.shopTextView);
        departmentNameTextView = (TextView) findViewById(R.id.departmentTextView);
        productEditText = (EditText) findViewById(R.id.productEditText);
        createButton = (Button) findViewById(R.id.createButton);

        Intent intent = getIntent();
        shopName = intent.getStringExtra("shopName");
        departmentName = intent.getStringExtra("departmentName");
        productName = intent.getStringExtra("productName");
        previousQuantity = intent.getIntExtra("quantity", -1);
        previousAlertQuantity = intent.getIntExtra("alertQuantity", -1);

        if (productName != null){
            productEditText.setText(productName);
            createButton.setText("Update");
        }
        if (previousQuantity != -1){
            quantityAmount = previousQuantity;
            quantityTextView.setText(String.valueOf(previousQuantity));
        }
        if (previousAlertQuantity != -1){
            alertQuantityAmount = previousAlertQuantity;
            alertQuantityTextView.setText(String.valueOf(previousAlertQuantity));
        }

        shopNameTextView.setText("Shop: " + shopName);
        departmentNameTextView.setText("Department: " + departmentName);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MainActivity.database.execSQL("INSERT INTO products (product, department, shop, quantity, alertQuantity) " +
                "VALUES ('" + productEditText.getText().toString() + "', '" + departmentName + "', '" + shopName + "', " + quantityAmount + ", " + alertQuantityAmount + ")");
        Intent intent = new Intent(ProductCreationActivity.this, ProductsActivity.class);
        intent.putExtra("shopName", shopName);
        intent.putExtra("departmentName", departmentName);
        startActivity(intent);

        return true;
    }
}
