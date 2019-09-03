package com.sanesedu.homehub;

/**
 * Created by sanes on 18/09/2016.
 */
public class Product {

    private String mProduct;
    private String mDepartment;
    private String mShop;
    private int mQuantity;
    private int mAlertQuantity;

    public Product (String product, String department, String shop, int quantity, int alertQuantity){
        mProduct = product;
        mQuantity = quantity;
        mAlertQuantity = alertQuantity;
        mShop = shop;
        mDepartment = department;
    }

    public String getProduct(){
        return mProduct;
    }

    public int getQuantity(){
        return mQuantity;
    }

    public String getDepartment (){
        return mDepartment;
    }

    public String getShop (){
        return mShop;
    }

    public int getAlertQuantity (){
        return mAlertQuantity;
    }

}
