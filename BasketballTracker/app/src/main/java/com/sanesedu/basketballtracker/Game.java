package com.sanesedu.basketballtracker;

/**
 * Created by sanes on 15/03/2017.
 */

public class Game {

    private String mOpponent;
    private String mDate;
    private Boolean mWin;
    private Boolean mHome;
    private int mOppPoints;
    private int mOurPoints;
    private int mMyPoints;
    private int mMyRebs;
    private int mMyAssis;
    private int mMyPER;

    public Game (String opponent, String date, boolean home, boolean win, int oppPoints, int ourPoints, int myPoints, int myRebs, int myAssis,
                 int myPER){
        mOpponent = opponent;
        mDate = date;
        mWin = win;
        mHome = home;
        mOppPoints = oppPoints;
        mOurPoints = ourPoints;
        mMyPoints = myPoints;
        mMyRebs = myRebs;
        mMyAssis = myAssis;
        mMyPER = myPER;
    }

    public String getOpponent(){
        return mOpponent;
    }

    public String getDate(){
        return mDate;
    }

    public Boolean getWin() {
        return mWin;
    }

    public boolean getHome(){
        return mHome;
    }

    public int getOppPoints (){
        return mOppPoints;
    }

    public int getOurPoints (){
        return mOurPoints;
    }

    public int getMyPoints (){
        return mMyPoints;
    }

    public int getMyRebs(){
        return mMyRebs;
    }

    public int getMyAssis(){
        return mMyAssis;
    }

    public int getMyPER() {
        return mMyPER;
    }
}
