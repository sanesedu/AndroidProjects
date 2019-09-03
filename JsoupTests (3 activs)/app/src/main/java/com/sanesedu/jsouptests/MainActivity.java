package com.sanesedu.jsouptests;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView wordTxt;
    TextView marcador;
    String phonetics;
    Button oberta;
    Button tancada;
    ConstraintLayout layout;
    TextView resultat;
    int EobertaCheck;
    int OobertaCheck;
    int points;
    static SQLiteDatabase database;


    public class GetPhonetics extends AsyncTask<String, Void, Bundle> {

        @Override
        protected Bundle doInBackground(String... urls) {
            String word = "";
            Boolean end = false;
            Bundle bundle = new Bundle();
            int Eoberta;  //1=oberta, 0=tancada
            int Ooberta;
            Eoberta = 0;
            Ooberta = 0;

            try {

                while (!end) {
                    org.jsoup.nodes.Document document1 = Jsoup.connect(urls[0]).get();
                    String html1 = document1.toString();
                    String[] break1 = html1.split("<div style=\"font-size:3em; color:#6200C5;\">");
                    String part1 = break1[1];
                    String[] break2 = part1.split("</div>");
                    word = break2[0].replaceAll("\\s+", "");
                    if (word.contains("o") || word.contains("e")) {
                        org.jsoup.nodes.Document document = Jsoup.connect("http://dcvb.iecat.net/results.asp?Word=" + word).get();
                        String html = document.toString();
                        String[] division = html.split("Fon.");
                        try {
                            String fon = division[1];
                            String[] division1;
                            if (fon.contains("Val.")) {
                                division1 = fon.split("Val.");
                                String val = division1[0];
                                String[] division2 = val.split("</font>");
                                String font = division2[division2.length - 2];
                                String[] division3 = font.split(">");
                                phonetics = division3[1];
                                if (!phonetics.contains("<") && !phonetics.trim().isEmpty()) {
                                    if (phonetics.contains("ɛ") || phonetics.contains("έ") || phonetics.contains("έ̞")) {
                                        Eoberta = 1;
                                    } else if (phonetics.contains("ɔ") || phonetics.contains("ɔ̞́") || phonetics.contains("ɔ́")) {
                                        Ooberta = 1;
                                    }
                                    end = true;
                                }

                            } else if (fon.contains("val.")) {
                                division1 = fon.split("val.");
                                String val = division1[0];
                                String[] division2 = val.split("</font>");
                                String font = division2[division2.length - 2];
                                String[] division3 = font.split(">");
                                phonetics = division3[1];
                                if (!phonetics.contains("<") && !phonetics.trim().isEmpty()) {
                                    if (phonetics.contains("ɛ") || phonetics.contains("έ") || phonetics.contains("έ̞")) {
                                        Eoberta = 1;
                                    } else if (phonetics.contains("ɔ") || phonetics.contains("ɔ̞́") || phonetics.contains("ɔ́")) {
                                        Ooberta = 1;
                                    }
                                    end = true;
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }

                    } else {
                        return null;
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("word", word);
            bundle.putString("phonetics", phonetics);
            bundle.putInt("Eoberta", Eoberta);
            bundle.putInt("Ooberta", Ooberta);

            return bundle;

        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Bundle updateWord() {

        Bundle bundle = new Bundle();
        Bundle bundle1;
        String wordDB = "";
        int EobertaDB = 0;
        int OobertaDB = 0;
        try {
            Random random = new Random();
            Cursor cursor = database.rawQuery("SELECT * FROM words WHERE id IN (SELECT id FROM words ORDER BY RANDOM() LIMIT 1)", null);
            if (cursor.moveToFirst()){
                wordDB = cursor.getString(cursor.getColumnIndex("word"));
                EobertaDB = cursor.getInt(cursor.getColumnIndex("Eoberta"));
                OobertaDB = cursor.getInt(cursor.getColumnIndex("Ooberta"));
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        wordTxt.setText(wordDB + " " + EobertaDB + " " + OobertaDB);
        bundle.putString("word", wordDB);
        bundle.putInt("Eoberta", EobertaDB);
        bundle.putInt("Ooberta", OobertaDB);

        GetPhonetics getPhonetics = new GetPhonetics();

        if (isNetworkAvailable()){
            try {
                bundle1 = getPhonetics.execute("https://www.palabrasaleatorias.com/paraula-aleatoria.php?fs=1&fs2=0&Submit=Nova+paraula").get();
                if (bundle1 != null){
                    String wordR = bundle1.getString("word");
                    int EobertaR = bundle1.getInt("Eoberta");
                    int OobertaR = bundle1.getInt("Ooberta");


                    String query1 = "SELECT * FROM words WHERE word = '" + wordR + "'";

                    Cursor cursor = database.rawQuery(query1, null);
                    if (cursor.getCount() <= 0) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("word", wordR);
                        contentValues.put("Eoberta", EobertaR);
                        contentValues.put("Ooberta", OobertaR);
                        database.insert("words", null, contentValues);
                        cursor.close();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bundle;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordTxt = findViewById(R.id.word);
        oberta = findViewById(R.id.oberta);
        tancada = findViewById(R.id.tancada);
        layout = findViewById(R.id.layout);
        resultat = findViewById(R.id.resultat);
        marcador = findViewById(R.id.marcador);
        points = 0;

        database = openOrCreateDatabase("Phoneticus", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS words (word VARCHAR, Eoberta INTEGER, Ooberta INTEGER, id INTEGER PRIMARY KEY AUTOINCREMENT)");

        marcador.setText("x " + points);

        try {
            Bundle bundle = updateWord();
            EobertaCheck = bundle.getInt("Eoberta");
            OobertaCheck = bundle.getInt("Ooberta");

        } catch (Exception e) {
            e.printStackTrace();
        }

        oberta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EobertaCheck == 1 || OobertaCheck == 1) {
                    resultat.setText("Correcte!");
                    points++;
                } else {
                    resultat.setText("Incorrecte!");
                    if (points > 0) {
                        points--;
                    }

                }

                marcador.setText("x " + points);

                try {
                    Bundle bundle = updateWord();
                    EobertaCheck = bundle.getInt("Eoberta");
                    OobertaCheck = bundle.getInt("Ooberta");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tancada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EobertaCheck == 0 && OobertaCheck == 0) {
                    resultat.setText("Correcte!");
                    points++;
                } else {
                    resultat.setText("Incorrecte!");
                    if (points > 0) {
                        points--;
                    }
                }

                marcador.setText("x " + points);

                try {
                    Bundle bundle = updateWord();
                    EobertaCheck = bundle.getInt("Eoberta");
                    OobertaCheck = bundle.getInt("Ooberta");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
