package com.sanesedu.basketballtracker;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sanes on 15/03/2017.
 */

public class GameAdapter extends ArrayAdapter<Game> {

    public GameAdapter(Context context, ArrayList<Game> games) {
        super(context, 0, games);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.game_item, parent, false);
        }

        Game currentGame = getItem(position);

        TextView winTxt = (TextView) listItemView.findViewById(R.id.winTxt);
        if (currentGame.getWin()){
            winTxt.setText("W");
            winTxt.setTextColor(Color.GREEN);
        } else {
            winTxt.setText("L");
            winTxt.setTextColor(Color.RED);
        }

        TextView homeTxt = (TextView) listItemView.findViewById(R.id.homeTxt);
        TextView points1Txt = (TextView) listItemView.findViewById(R.id.points1Txt);
        TextView points2Txt = (TextView) listItemView.findViewById(R.id.points2Txt);
        if (currentGame.getHome()){
            homeTxt.setText("vs");
            points1Txt.setText(String.valueOf(currentGame.getOurPoints()));
            points2Txt.setText(String.valueOf(currentGame.getOppPoints()));
        } else {
            homeTxt.setText("@");
            points2Txt.setText(String.valueOf(currentGame.getOurPoints()));
            points1Txt.setText(String.valueOf(currentGame.getOppPoints()));
        }

        TextView oppTxt = (TextView) listItemView.findViewById(R.id.oppTxt);
        oppTxt.setText(currentGame.getOpponent());

        TextView dateTxt = (TextView) listItemView.findViewById(R.id.dateTxt);
        dateTxt.setText(currentGame.getDate());

        TextView myPointsTxt = (TextView) listItemView.findViewById(R.id.myPointsTxt);
        myPointsTxt.setText(String.valueOf(currentGame.getMyPoints()) + " pts");

        TextView myRebsTxt = (TextView) listItemView.findViewById(R.id.myRebsTxt);
        myRebsTxt.setText(String.valueOf(currentGame.getMyRebs()) + " reb");

        TextView myAssisTxt = (TextView) listItemView.findViewById(R.id.myAssisTxt);
        myAssisTxt.setText(String.valueOf(currentGame.getMyAssis()) + " as");

        TextView myPERTxt = (TextView) listItemView.findViewById(R.id.myPERTxt);
        myPERTxt.setText(String.valueOf(currentGame.getMyPER()) + " PER");

        return listItemView;
    }
}
