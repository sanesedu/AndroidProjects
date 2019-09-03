package com.sanesedu.basketballtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GameCreatorActivity extends AppCompatActivity {

    EditText oppNameEdTxt;
    EditText dateEdTxt;
    RadioGroup homeAwayRdGr;
    RadioButton homeAwayRdBt;
    Button startGameBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamecreator);

        android.app.ActionBar actionBar = getActionBar();
        try {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e){
            e.printStackTrace();
        }

        oppNameEdTxt = (EditText) findViewById(R.id.oppNameEdTxt);
        dateEdTxt = (EditText) findViewById(R.id.dateEdTxt);

        startGameBt = (Button) findViewById(R.id.startGameBt);
        startGameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oppName = String.valueOf(oppNameEdTxt.getText());
                String date = String.valueOf(dateEdTxt.getText());
                homeAwayRdGr = (RadioGroup) findViewById(R.id.homeAwayRadGr);

                int radBt = homeAwayRdGr.getCheckedRadioButtonId();
                homeAwayRdBt = (RadioButton) findViewById(radBt);
                final String homeAway = String.valueOf(homeAwayRdBt.getText());
                int homeAwayInt;
                if (homeAway == "Home"){
                    homeAwayInt = 1;
                } else {
                    homeAwayInt = 0;
                }

                String homeAwayStr = String.valueOf(homeAwayInt);
                MainActivity.database.execSQL("INSERT INTO games (opponent, homeAway, date) VALUES ('" + oppName + "', '" + homeAwayStr + "', '" + date + "')" );

                Intent intent = new Intent(GameCreatorActivity.this, QuartersActivity.class);
                intent.putExtra("opponent", oppName);
                intent.putExtra("homeAway", homeAwayStr);
                intent.putExtra("date", date);
                intent.putExtra("quarter", "1st Quarter");
                startActivity(intent);
            }
        });

    }

}
