package com.sanesedu.homehub;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sanes on 18/09/2016.
 */
public class ProductListAdapter extends ArrayAdapter<Product>{

    public ProductListAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.product_item, parent, false);
        }

        Product currentProduct = getItem(position);

        TextView product = (TextView) listItemView.findViewById(R.id.product);

        product.setText(currentProduct.getProduct());

        TextView quantity = (TextView) listItemView.findViewById(R.id.quantity);

        quantity.setText(String.valueOf(currentProduct.getQuantity()));

        TextView locationProduct = (TextView) listItemView.findViewById(R.id.locationProduct);

        String department = currentProduct.getDepartment();
        String shop = currentProduct.getShop();

        locationProduct.setText("en " + department + ", " + shop);

        LinearLayout container = (LinearLayout) listItemView.findViewById(R.id.productInfoContainer);

        if (currentProduct.getQuantity() == currentProduct.getAlertQuantity()){
            container.setBackgroundColor(Color.YELLOW);
            product.setTextColor(Color.WHITE);
            quantity.setTextColor(Color.WHITE);
            locationProduct.setTextColor(Color.WHITE);
        } else if (currentProduct.getQuantity() < currentProduct.getAlertQuantity()){
            container.setBackgroundColor(Color.RED);
            product.setTextColor(Color.WHITE);
            quantity.setTextColor(Color.WHITE);
            locationProduct.setTextColor(Color.WHITE);
        }

        return listItemView;
    }
}
