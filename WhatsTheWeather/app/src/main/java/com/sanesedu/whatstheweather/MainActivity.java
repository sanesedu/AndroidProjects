package com.sanesedu.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.IllegalFormatCodePointException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    TextView resultTextView;

    public void findWeather (View view){

        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        try {
            String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");

            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName  + "&apikey=a5caa6928fe7a21ffe407a421a38672b");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();

        }


        cityName.setCursorVisible(false);

    }

    public class DownloadTask extends AsyncTask <String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            HttpURLConnection urlConnection = null;
            String result = "";

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data != -1){

                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {

                e.printStackTrace();

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                String message = "";

                JSONObject jsonObject = new JSONObject(s);

                JSONArray weatherInfo = jsonObject.getJSONArray("weather");

                for (int i = 0; i < weatherInfo.length(); i++){

                    JSONObject object = weatherInfo.getJSONObject(i);

                    String main = "";
                    String description = "";

                    main = object.getString("main");
                    description = object.getString("description");

                    if (main != "" && description != ""){

                        message += main + ": " + description +"\r\n";
                    }
                }

                if (message != ""){

                    resultTextView.setText(message);

                } else {

                    Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.cityName);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName.setCursorVisible(true);
            }
        });

    }
}
