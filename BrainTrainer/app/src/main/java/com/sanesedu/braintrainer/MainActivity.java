package com.sanesedu.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button button;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    TextView resultTextView;
    TextView sumTextView;
    TextView pointsTextView;
    TextView timerTextView;
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    RelativeLayout gameRelativeLayout;
    boolean gameIsActive = true;

    public void playAgain (View view ) {

        score = 0;
        numberOfQuestions = 0;

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        generateQuestion();


        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long l) {

                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {

                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your score: " + score + "/" + numberOfQuestions);
                gameIsActive = false;
            }
        }.start();

        gameIsActive = true;

    }

    public void generateQuestion (){

        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOfCorrectAnswer = random.nextInt(4);

        answers.clear();

        int incorrectAnswer;

        for (int i = 0; i < 4; i++){

            if (i == locationOfCorrectAnswer){

                answers.add(a+b);

            } else {

                incorrectAnswer = random.nextInt(41);

                while (incorrectAnswer == a + b){

                    incorrectAnswer = random.nextInt(41);
                }

                answers.add(incorrectAnswer);
            }
        }

        button.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer (View view){

        if (gameIsActive == true){
            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){

                score++;
                resultTextView.setText("Correct!");

            } else {

                resultTextView.setText("Wrong!");
            }

            numberOfQuestions++;

            pointsTextView.setText(score + "/" + numberOfQuestions);

            generateQuestion();
        }

    }

    public void start (View view){

        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
        gameIsActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);

        sumTextView = (TextView) findViewById(R.id.sumTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);

    }
}
