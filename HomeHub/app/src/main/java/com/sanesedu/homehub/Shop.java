package com.sanesedu.homehub;

/**
 * Created by sanes on 20/09/2016.
 */

public class Shop {

    private String mShop;
    private int mDepartmentAmount;
    private int mProductAmount;
    private boolean mAlert;
    private boolean mDanger;

    public Shop (String shop, int departmentAmount, int productAmount, boolean alert, boolean danger){
        mShop = shop;
        mDepartmentAmount = departmentAmount;
        mProductAmount = productAmount;
        mAlert = alert;
        mDanger = danger;
    }

    public String getShop (){
        return mShop;
    }

    public int getDepartmentAmount (){
        return mDepartmentAmount;
    }

    public int getProductAmount (){
        return mProductAmount;
    }

    public boolean getAlert (){
        return mAlert;
    }

    public boolean getDanger (){
        return mDanger;
    }

}
