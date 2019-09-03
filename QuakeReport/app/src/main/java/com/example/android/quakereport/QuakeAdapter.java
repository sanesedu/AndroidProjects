package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by sanes on 27/07/2016.
 */
public class QuakeAdapter extends ArrayAdapter<Earthquake> {

    public QuakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResId;
        int mag = (int) Math.floor(magnitude);
        switch (mag){
            case 0:
            case 1:
                magnitudeColorResId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final Earthquake currentEarthquake = getItem(position);

        double magnit = currentEarthquake.getMag();
        DecimalFormat decformat = new DecimalFormat("0.0");
        String magnitude = decformat.format(magnit);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) listItemView.findViewById(R.id.magnitude).getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        TextView mag = (TextView) listItemView.findViewById(R.id.magnitude);
        mag.setText(magnitude);

        String locationString = currentEarthquake.getLocation();
        if (locationString.contains("of")){
            int stop = locationString.indexOf("of");
            String proximity = locationString.substring(0, stop+2);
            TextView offset = (TextView)listItemView.findViewById(R.id.proximity);
            offset.setText(proximity);

            String place = locationString.substring(stop+3);
            TextView location = (TextView) listItemView.findViewById(R.id.location);
            location.setText(place);

        } else {
            TextView offset = (TextView)listItemView.findViewById(R.id.proximity);
            offset.setText(R.string.proximity);
            TextView location = (TextView) listItemView.findViewById(R.id.location);
            location.setText(locationString);
        }

        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        TextView date = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        date.setText(formattedDate);

        TextView time = (TextView)listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        time.setText(formattedTime);

        return listItemView;
    }
}
