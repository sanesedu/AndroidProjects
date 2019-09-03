package me.sanesedu.rockpaperscissors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int you_rsc = getIntent().getExtras().getInt("you_rsc");
        int opp_rsc = getIntent().getExtras().getInt("opp_rsc");

        String result;
        String elements;

        if (opp_rsc == R.drawable.rock1){
            if (you_rsc == R.drawable.rock1){
                result = getResources().getString(R.string.tie);
                elements = getResources().getString(R.string.rock_vs_rock);
            } else if (you_rsc == R.drawable.paper1){
                result = getResources().getString(R.string.you_win);
                elements = getResources().getString(R.string.paper_beats_rock);
            } else {
                result = getResources().getString(R.string.you_lose);
                elements = getResources().getString(R.string.rock_beats_scissors);
            }
        } else if (opp_rsc == R.drawable.paper1){
            if (you_rsc == R.drawable.rock1){
                result = getResources().getString(R.string.you_lose);
                elements = getResources().getString(R.string.paper_beats_rock);
            } else if (you_rsc == R.drawable.paper1){
                result = getResources().getString(R.string.tie);
                elements = getResources().getString(R.string.paper_vs_paper);
            } else {
                result = getResources().getString(R.string.you_win);
                elements = getResources().getString(R.string.scissors_beats_paper);
            }
        } else {
            if (you_rsc == R.drawable.rock1){
                result = getResources().getString(R.string.you_win);
                elements = getResources().getString(R.string.rock_beats_scissors);
            } else if (you_rsc == R.drawable.paper1){
                result = getResources().getString(R.string.you_lose);
                elements = getResources().getString(R.string.scissors_beats_paper);
            } else {
                result = getResources().getString(R.string.tie);
                elements = getResources().getString(R.string.scissors_vs_scissors);
            }
        }

        TextView elementsTxtView = (TextView) findViewById(R.id.elements);
        elementsTxtView.setText(elements);

        TextView resultTxtView = (TextView) findViewById(R.id.result);
        resultTxtView.setText(result);

        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        Button newGameResultButton = (Button) findViewById(R.id.new_game_result_button);
        newGameResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

    }
}
