package com.sanesedu.basketballtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView gamesListView;
    ArrayList<Game> gameList;
    GameAdapter gameAdapter;
    static SQLiteDatabase database;

    boolean quarterDbCreated = false;

    public void updateViews(){

        if (gameList.size() > 0){
            gameList.clear();
            gameAdapter.clear();
        }

        Cursor cursor = database.rawQuery("SELECT * FROM games", null);
        int opponentIndex = cursor.getColumnIndex("opponent");
        int homeAwayIndex = cursor.getColumnIndex("homeAway");
        int dateIndex = cursor.getColumnIndex("date");
        int winLoseIndex = cursor.getColumnIndex("winLose");
        int oppPointsIndex = cursor.getColumnIndex("oppPoints");
        int ourPointsIndex = cursor.getColumnIndex("ourPoints");

        while (cursor.moveToNext()){
            String oppName = cursor.getString(opponentIndex);
            String homeAway = cursor.getString(homeAwayIndex);
            boolean homeAwayBo;
            if (Integer.valueOf(homeAway) == 1){
                homeAwayBo = true;
            } else {
                homeAwayBo = false;
            }
            String date = cursor.getString(dateIndex);
            String winLose = cursor.getString(winLoseIndex);
            boolean winLoseBo;
            if (winLose.equals("1")){
                winLoseBo = true;
            } else {
                winLoseBo = false;
            }
            int oppPoints = cursor.getInt(oppPointsIndex);
            int ourPoints = cursor.getInt(ourPointsIndex);

            if (quarterDbCreated){
                Cursor cursorQuarter = database.rawQuery("SELECT * FROM quarter WHERE opponent=" + oppName + " AND date=" + date , null);
                int myPointsIndex = cursorQuarter.getColumnIndex("points");
                int defRebsIndex = cursorQuarter.getColumnIndex("defRebs");
                int offRebsIndex = cursorQuarter.getColumnIndex("offRebs");
                int assistIndex = cursorQuarter.getColumnIndex("assists");
                int perIndex = cursorQuarter.getColumnIndex("per");

                int myPoints = 0;
                int defRebs;
                int offRebs;
                int assist = 0;
                int per = 0;
                int rebsTotal = 0;

                while (cursorQuarter.moveToNext()){
                    myPoints = cursorQuarter.getInt(myPointsIndex);
                    defRebs = cursorQuarter.getInt(defRebsIndex);
                    offRebs = cursorQuarter.getInt(offRebsIndex);
                    assist = cursorQuarter.getInt(assistIndex);
                    per = cursorQuarter.getInt(perIndex);
                    rebsTotal = defRebs + offRebs;
                }

                gameList.add(new Game(oppName, date, homeAwayBo, winLoseBo, oppPoints, ourPoints, myPoints, rebsTotal, assist, per));

            } else {
                gameList.add(new Game(oppName, date, homeAwayBo, winLoseBo, oppPoints, ourPoints, 0, 0, 0, 0));
            }

        }
        cursor.close();
        gameAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent receivedIntent = getIntent();
        quarterDbCreated = receivedIntent.getBooleanExtra("quarterCreated", false);


        database = openOrCreateDatabase("Basketball Tracker", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS games (opponent VARCHAR, homeAway VARCHAR DEFAULT '1', date VARCHAR, winLose VARCHAR DEFAULT '1', " +
                "oppPoints INTEGER DEFAULT 0, ourPoints INTEGER DEFAULT 0, id INTEGER PRIMARY KEY)");

        database.execSQL("CREATE TABLE IF NOT EXISTS quarter (opponent VARCHAR, homeAway VARCHAR DEFAULT '1', date VARCHAR, quarter INTEGER, " +
                " points INTEGER, pointPercent VARCHAR, ftMade INTEGER, ftShot INTEGER, twoPointMade INTEGER, twoPointShot INTEGER, threePointMade INTEGER, threePointShot INTEGER," +
                " defRebs INTEGER, offRebs INTEGER, assists INTEGER, steals INTEGER, tovers INTEGER, blocks INTEGER, fouls INTEGER, per INTEGER, minutes VARCHAR, id INTEGER PRIMARY KEY)");

        gamesListView = (ListView) findViewById(R.id.gamesListView);
        gameList = new ArrayList<>();
        gameAdapter = new GameAdapter(getApplicationContext(), gameList);
        gamesListView.setAdapter(gameAdapter);
        updateViews();

        gamesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Game game = gameList.get(position);
                final String oppName = game.getOpponent();

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete game")
                        .setMessage("The game will be deleted")
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.execSQL("DELETE FROM games WHERE opponent ='" + oppName + "'");
                                updateViews();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });

        FloatingActionButton addGameBt = (FloatingActionButton) findViewById(R.id.addGameBt);
        addGameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameCreatorActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
