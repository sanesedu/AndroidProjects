package edu.galileo.android.tipcalc;

import android.app.Application;

/**
 * Created by sanes on 15/07/2016.
 */
public class TipCalcApp extends Application {
    private final static String ABOUT_URL = "http://about.me/adriancatalan";

    public String getAboutUrl(){
        return ABOUT_URL;
    }
}
