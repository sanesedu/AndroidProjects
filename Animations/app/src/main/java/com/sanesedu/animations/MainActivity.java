package com.sanesedu.animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DrawableUtils;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void crossFade(View view){

        ImageView bart = (ImageView) findViewById(R.id.bart);

        bart.animate().translationXBy(1000f).translationYBy(1000f).rotation(1800f)
                .scaleX(1.5f).scaleY(1.5f).setDuration(2000);

        //ImageView homer = (ImageView) findViewById(R.id.homer);
        //homer.animate().alpha(1f).setDuration(2000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView bart = (ImageView) findViewById(R.id.bart);

        bart.setTranslationX(-1000f);
        bart.setTranslationY(-1000f);
    }
}
