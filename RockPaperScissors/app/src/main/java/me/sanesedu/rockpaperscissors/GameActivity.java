package me.sanesedu.rockpaperscissors;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by sanes on 05/08/2016.
 */
public class GameActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Button play_button = (Button) findViewById(R.id.play_button);
        final ImageView oppImageView = (ImageView) findViewById(R.id.opp_sel_item);
        final ImageView youImageView = (ImageView) findViewById(R.id.you_sel_item);

        final ImageView rockImageView = (ImageView) findViewById(R.id.rock);
        rockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oppImageView.setImageResource(R.drawable.opponent_items);
                youImageView.setImageResource(R.drawable.rock);
                youImageView.setTag(R.drawable.rock1);
                play_button.setVisibility(View.VISIBLE);

            }
        });

        final ImageView paperImageView = (ImageView) findViewById(R.id.paper);
        paperImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oppImageView.setImageResource(R.drawable.opponent_items);
                youImageView.setImageResource(R.drawable.paper);
                youImageView.setTag(R.drawable.paper1);
                play_button.setVisibility(View.VISIBLE);
            }
        });

        final ImageView scissorsImageView = (ImageView) findViewById(R.id.scissors);
        scissorsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oppImageView.setImageResource(R.drawable.opponent_items);
                youImageView.setImageResource(R.drawable.scissors);
                youImageView.setTag(R.drawable.scissors1);
                play_button.setVisibility(View.VISIBLE);
            }
        });

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (Integer) youImageView.getTag();
                Intent intent = new Intent(GameActivity.this, MatchupActivity.class);
                intent.putExtra("rsc", tag);
                startActivity(intent);

            }
        });


    }

}
