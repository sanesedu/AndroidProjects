package com.sanesedu.homehub;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;
import static com.sanesedu.homehub.R.id.locationProduct;
import static com.sanesedu.homehub.R.id.product;
import static com.sanesedu.homehub.R.id.quantity;

/**
 * Created by sanes on 20/09/2016.
 */

public class ShopListAdapter extends ArrayAdapter<Shop> {

    public ShopListAdapter(Context context, ArrayList<Shop> shops) {
        super(context, 0, shops);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.shop_item, parent, false);
        }

        Shop currentShop = getItem(position);

        TextView shop = (TextView) listItemView.findViewById(R.id.shop);
        shop.setText(currentShop.getShop());

        TextView departmentAmount = (TextView) listItemView.findViewById(R.id.departmentsAmount);
        departmentAmount.setText("Departments: " + Integer.toString(currentShop.getDepartmentAmount()));

        TextView productAmount = (TextView) listItemView.findViewById(R.id.productsAmount);
        productAmount.setText("Products: " + Integer.toString(currentShop.getProductAmount()));

        LinearLayout shopInfoContainer = (LinearLayout) listItemView.findViewById(R.id.shopInfoConatiner);

        if (currentShop.getAlert()){
            shopInfoContainer.setBackgroundColor(Color.YELLOW);
            shop.setTextColor(Color.WHITE);
            departmentAmount.setTextColor(Color.WHITE);
            productAmount.setTextColor(Color.WHITE);
        } else if (currentShop.getDanger()){
            shopInfoContainer.setBackgroundColor(Color.RED);
            shop.setTextColor(Color.WHITE);
            departmentAmount.setTextColor(Color.WHITE);
            productAmount.setTextColor(Color.WHITE);
        }
        return listItemView;
    }
}
