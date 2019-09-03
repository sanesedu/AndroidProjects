package me.sanesedu.rockpaperscissors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MatchupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchup);

        int rsc = getIntent().getExtras().getInt("rsc");

        final ImageView youMatchup = (ImageView) findViewById(R.id.you_matchup);
        youMatchup.setImageResource(rsc);
        youMatchup.setTag(rsc);

        final ImageView oppMatchup = (ImageView) findViewById(R.id.opponent_matchup);
        Random random = new Random();
        int opp_rsc = random.nextInt(2);
        switch (opp_rsc){
            case 0:
                oppMatchup.setImageResource(R.drawable.rock1);
                oppMatchup.setTag(R.drawable.rock1);
                break;
            case 1:
                oppMatchup.setImageResource(R.drawable.paper1);
                oppMatchup.setTag(R.drawable.paper1);
                break;
            case 2:
                oppMatchup.setImageResource(R.drawable.scissors1);
                oppMatchup.setTag(R.drawable.scissors1);
                break;
            default:
                oppMatchup.setImageResource(R.drawable.opponent_items);
                oppMatchup.setTag(R.drawable.opponent_items);

        }

        Button next_button = (Button) findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int you_rsc = (Integer) youMatchup.getTag();
                int opp_rsc = (Integer) oppMatchup.getTag();

                Intent intent = new Intent(MatchupActivity.this, ResultActivity.class);
                intent.putExtra("you_rsc", you_rsc);
                intent.putExtra("opp_rsc", opp_rsc);
                startActivity(intent);
            }
        });

    }
}
