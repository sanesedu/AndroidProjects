package com.sanesedu.basketballtracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuartersActivity extends AppCompatActivity {

    TextView quarterTxt;
    TextView minClockTxt;
    ImageView playPauseBt;
    Button shotAdderBt;
    TextView pointCounterTxt;
    TextView percenPointTxt;
    Button rebAdderBt;
    TextView defRebTxt;
    TextView offRebTxt;
    Button assistAdderBt;
    TextView assistCounterTxt;
    Button stealAdderBt;
    TextView stealCounterTxt;
    Button toverAdderBt;
    TextView toverCounterTxt;
    Button blockAdderBt;
    TextView blockCounterTxt;
    Button foulAdderBt;
    TextView foulCounterTxt;
    Button endOfQuarterBt;


    int points = 0;
    int ftMade = 0;
    int ftShot = 0;
    int twoPointMade = 0;
    int twoPointShot = 0;
    int threePointMade = 0;
    int threePointShot = 0;
    int shotsMade;
    int shotsShot;
    int fouls = 0;
    int defRebs = 0;
    int offRebs = 0;
    int steals = 0;
    int assists = 0;
    int blocks = 0;
    int tovers = 0;
    int quarter = 1;
    int per = 0;
    String minutes = "0:00";
    String date;
    String homeAwayStr;
    String oppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quarters);
        Intent receivedIntent = getIntent();
        oppName = receivedIntent.getStringExtra("opponent");
        homeAwayStr = receivedIntent.getStringExtra("homeAway");
        date = receivedIntent.getStringExtra("date");
        android.app.ActionBar actionBar = getActionBar();
        try {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(oppName);
        } catch (Exception e){
            e.printStackTrace();
        }

        quarterTxt = (TextView) findViewById(R.id.quarterTxt);

        if (quarter == 1){
            quarterTxt.setText("1st Quarter");
        } else if (quarter==2){
            quarterTxt.setText("2nd Quarter");
        } else if (quarter==3){
            quarterTxt.setText("3rd Quarter");
        } else if (quarter==4){
            quarterTxt.setText("4th Quarter");
        } else if (quarter==5){
            quarterTxt.setText("Overtime");
        } else if (quarter==6){
            quarterTxt.setText("2nd Overtime");
        } else if (quarter==7){
            quarterTxt.setText("3rd Overtime");
        } else {
            quarterTxt.setText("Other Overtime");
        }

        minClockTxt = (TextView) findViewById(R.id.minClockQuart);
        playPauseBt = (ImageView) findViewById(R.id.playPauseQuarterBt);
        shotAdderBt = (Button) findViewById(R.id.shotAdderBt);
        pointCounterTxt = (TextView) findViewById(R.id.pointsQuarterCounter);
        percenPointTxt = (TextView) findViewById(R.id.shotPerQuarterCounter);
        rebAdderBt = (Button) findViewById(R.id.rebsAdderBt);
        defRebTxt = (TextView) findViewById(R.id.defRebsQuarterCounter);
        offRebTxt = (TextView) findViewById(R.id.offRebsQuarterConter);
        assistAdderBt = (Button) findViewById(R.id.assistAdderBt);
        assistCounterTxt = (TextView) findViewById(R.id.assistCounterTxt);
        stealAdderBt = (Button) findViewById(R.id.stealAdderBt);
        stealCounterTxt = (TextView) findViewById(R.id.stealCounterTxt);
        toverAdderBt = (Button) findViewById(R.id.tosAdderBt);
        toverCounterTxt = (TextView) findViewById(R.id.toverCounterTxt);
        blockAdderBt = (Button) findViewById(R.id.blockAdderBt);
        blockCounterTxt = (TextView) findViewById(R.id.blockQuarterCounter);
        foulAdderBt = (Button) findViewById(R.id.foulAdderBt);
        foulCounterTxt = (TextView) findViewById(R.id.foulCounterTxt);
        endOfQuarterBt = (Button) findViewById(R.id.endOfQuarterBt);


        shotAdderBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QuartersActivity.this)
                        .setTitle("Shot value")
                        .setPositiveButton("3pt", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                threePointShot++;

                                new AlertDialog.Builder(QuartersActivity.this)
                                        .setTitle("In or out?")
                                        .setPositiveButton("In", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                threePointMade++;
                                                points +=3;
                                                shotsMade = ftMade + twoPointMade + threePointMade;
                                                shotsShot = ftShot + twoPointShot + threePointShot;

                                                pointCounterTxt.setText(points + " pts");
                                                percenPointTxt.setText(shotsMade + "/" + shotsShot);
                                            }
                                        })
                                        .setNegativeButton("Out", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                shotsMade = ftMade + twoPointMade + threePointMade;
                                                shotsShot = ftShot + twoPointShot + threePointShot;

                                                pointCounterTxt.setText(points + " pts");
                                                percenPointTxt.setText(shotsMade + "/" + shotsShot);
                                            }
                                        }).show();
                            }
                        })
                        .setNegativeButton("2pt", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                twoPointShot++;

                                new AlertDialog.Builder(QuartersActivity.this)
                                        .setTitle("In or out?")
                                        .setPositiveButton("In", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                twoPointMade++;
                                                points +=2;
                                                shotsMade = ftMade + twoPointMade + threePointMade;
                                                shotsShot = ftShot + twoPointShot + threePointShot;

                                                pointCounterTxt.setText(points + " pts");
                                                percenPointTxt.setText(shotsMade + "/" + shotsShot);
                                            }
                                        })
                                        .setNegativeButton("Out", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                shotsMade = ftMade + twoPointMade + threePointMade;
                                                shotsShot = ftShot + twoPointShot + threePointShot;

                                                pointCounterTxt.setText(points + " pts");
                                                percenPointTxt.setText(shotsMade + "/" + shotsShot);
                                            }
                                        }).show();
                            }
                        })
                        .setNeutralButton("1pt", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ftShot++;

                                new AlertDialog.Builder(QuartersActivity.this)
                                        .setTitle("In or out?")
                                        .setPositiveButton("In", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ftMade++;
                                                points +=1;
                                                shotsMade = ftMade + twoPointMade + threePointMade;
                                                shotsShot = ftShot + twoPointShot + threePointShot;

                                                pointCounterTxt.setText(points + " pts");
                                                percenPointTxt.setText(shotsMade + "/" + shotsShot);
                                            }
                                        })
                                        .setNegativeButton("Out", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                shotsMade = ftMade + twoPointMade + threePointMade;
                                                shotsShot = ftShot + twoPointShot + threePointShot;

                                                pointCounterTxt.setText(points + " pts");
                                                percenPointTxt.setText(shotsMade + "/" + shotsShot);
                                            }
                                        }).show();
                            }
                        }).show();

            }
        });

        rebAdderBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QuartersActivity.this)
                        .setTitle("Rebound")
                        .setPositiveButton("Defensive", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                defRebs++;
                                defRebTxt.setText(defRebs + " def");
                            }
                        })
                        .setNegativeButton("Offensive", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                offRebs++;
                                offRebTxt.setText(offRebs + " off");
                            }
                        }).show();
            }

        });

        assistAdderBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QuartersActivity.this)
                        .setTitle("Assist")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                assists++;
                                assistCounterTxt.setText(assists + " as");
                            }
                        }).show();
            }
        });

        stealAdderBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QuartersActivity.this)
                        .setTitle("Steal")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                steals++;
                                stealCounterTxt.setText(steals + "st");
                            }
                        }).show();

            }
        });

        toverAdderBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QuartersActivity.this)
                        .setTitle("Turnover")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tovers++;
                                toverCounterTxt.setText(tovers + "to");
                            }
                        }).show();

            }
        });

        blockAdderBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QuartersActivity.this)
                        .setTitle("Block")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                blocks++;
                                blockCounterTxt.setText(blocks + " bl");
                            }
                        }).show();

            }
        });

        foulAdderBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fouls <= 4){
                    new AlertDialog.Builder(QuartersActivity.this)
                            .setTitle("Foul")
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fouls++;
                                    foulCounterTxt.setText(fouls + " fl");

                                    if (fouls == 5){
                                        new AlertDialog.Builder(QuartersActivity.this)
                                                .setTitle("Ejected")
                                                .setMessage("You've just been ejected")
                                                .show();
                                    }
                                }
                            }).show();
                } else {
                    new AlertDialog.Builder(QuartersActivity.this)
                            .setTitle("Foul")
                            .setMessage("You've already been ejected!")
                            .setPositiveButton("Continue", null)
                            .show();
                }
            }
        });

        endOfQuarterBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per = points + offRebs + defRebs + assists + steals + blocks - fouls - tovers - (ftShot - ftMade) - (twoPointShot - twoPointMade) - (threePointShot - threePointMade);
                MainActivity.database.execSQL("INSERT INTO quarter (opponent, homeAway, date, quarter, points, pointPercent, ftMade, ftShot, twoPointMade, " +
                        "twoPointShot, threePointMade, threePointShot, defRebs, offRebs, assists, steals, tovers, blocks, fouls, per, minutes) " +
                        "VALUES ('" + oppName + "', '" + homeAwayStr + "', '" + date + "', '" + quarter + "', '" + points + "', '" + percenPointTxt.getText() + "', '" +
                        ftMade + "', '" + ftShot + "', '" + twoPointMade + "', '" + twoPointShot + "', '" + threePointMade + "', '" + threePointShot + "', '" +
                        defRebs + "', '" + offRebs + "', '" + assists + "', '" + steals + "', '" + tovers + "', '" + blocks + "', '" + fouls +  "', '" + per + "', '" + minutes + "')");
                if (quarter <=3){
                    quarter++;
                } else if (quarter>3){
                    new AlertDialog.Builder(QuartersActivity.this)
                            .setTitle("End of game?")
                            .setMessage("Has the game ended?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(QuartersActivity.this, MainActivity.class);
                                    intent.putExtra("quarterCreated", true);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    quarter++;
                                }
                            }).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                per = points + offRebs + defRebs + assists + steals + blocks - fouls - tovers - (ftShot - ftMade) - (twoPointShot - twoPointMade) - (threePointShot - threePointMade);
                MainActivity.database.execSQL("INSERT INTO quarter (opponent, homeAway, date, quarter, points, pointPercent, ftMade, ftShot, twoPointMade, " +
                        "twoPointShot, threePointMade, threePointShot, defRebs, offRebs, assists, steals, tovers, blocks, fouls, per, minutes) " +
                        "VALUES ('" + oppName + "', '" + homeAwayStr + "', '" + date + "', '" + quarter + "', '" + points + "', '" + percenPointTxt.getText() + "', '" +
                        ftMade + "', '" + ftShot + "', '" + twoPointMade + "', '" + twoPointShot + "', '" + threePointMade + "', '" + threePointShot + "', '" +
                        defRebs + "', '" + offRebs + "', '" + assists + "', '" + steals + "', '" + tovers + "', '" + blocks + "', '" + fouls +  "', '" + per + "', '" + minutes + "')");
                Intent intent = new Intent(QuartersActivity.this, MainActivity.class);
                intent.putExtra("quarterCreated", true);
                startActivity(intent);
        }
        return true;
    }

}
