package com.sanesedu.numbershapes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    class Number {

        int number;

        public boolean isSquare(){

            double squareRoot = Math.sqrt(number);

            if (squareRoot == Math.floor(squareRoot)){

                return true;

            } else {

                return false;

            }

        }

        public boolean isTriangular(){

            int x = 1;

            int triangularNumber = 1;

            while (triangularNumber < number){

                x++;

                triangularNumber += x;

            }

            if (triangularNumber == number){

                return true;

            } else {

                return false;

            }

        }

    }

    public void testNumber(View view){

        EditText usersNumber = (EditText) findViewById(R.id.usersNumber);

        if (TextUtils.isEmpty(usersNumber.getText())){
            usersNumber.setError("Enter a number");
        } else {

            Number myNumber = new Number();
            myNumber.number = Integer.parseInt(usersNumber.getText().toString());

            String message = "";

            if (myNumber.isSquare()){

                if (myNumber.isTriangular()){

                    message = myNumber.number + " is both triangular and square";

                } else {

                    message = myNumber.number + " is square, but not triangular";
                }

            } else {

                if (myNumber.isTriangular()){

                    message = myNumber.number + " is triangular, but not square";

                } else {

                    message = myNumber.number + " is neither square nor triangular";
                }

            }

            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 40);
            toast.show();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
