package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sanes on 27/07/2016.
 */
public class Earthquake {

    private double mMag;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mUrl;

    public Earthquake (double mag, String location, long timeInMilliseconds, String url){
        mMag = mag;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;

    }

    public double getMag() {
        return mMag;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getUrl(){
        return mUrl;
    }

}
