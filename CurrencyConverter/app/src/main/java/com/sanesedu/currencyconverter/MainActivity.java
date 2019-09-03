package com.sanesedu.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private double euroResultValue;
    private double usdResultValue;

    public void convert(View view){
        EditText usdInput = (EditText) findViewById(R.id.usdInput);
        if (usdInput.getText()!=null){
            double usdInputValue = Integer.parseInt(usdInput.getText().toString());
            euroResultValue = usdInputValue*0.881331;
        } else {
            euroResultValue = 0.00;
        }
        TextView euroResultTxt = (TextView) findViewById(R.id.eurTxt);
        euroResultTxt.setText(String.format("%.2f", euroResultValue) + " EUR");

        EditText euroInput = (EditText) findViewById(R.id.euroInput);
        if (euroInput.getText()!=null){
            double euroInputValue = Integer.parseInt(euroInput.getText().toString());
            usdResultValue = euroInputValue*1.134172;
        } else {
            usdResultValue = 0.00;
        }
        TextView usdResultTxt = (TextView) findViewById(R.id.usdTxt);
        usdResultTxt.setText(String.format("%.2f", usdResultValue) + " USD");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
