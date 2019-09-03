package com.sanesedu.jsouptests;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;

public class CheckWordActivity extends AppCompatActivity {

    Button checkBt;
    EditText wordEt;
    TextView resTxt;
    ImageView homeIv;
    ConstraintLayout constraintLayout;

    public class GetWord extends AsyncTask<String, Void, Bundle> {

        @Override
        protected Bundle doInBackground(String... reqWord) {
            String word = reqWord[0];
            String phonetics = "";
            Bundle bundle = new Bundle();
            int Eoberta;  //1=oberta, 0=tancada
            int Ooberta;
            Eoberta = 0;
            Ooberta = 0;

            try {

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
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
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

    public Bundle checkWord (String word){
        Bundle bundle = new Bundle();
        Bundle bundle1;
        GetWord getWord = new GetWord();
        String phoneticus = "";
        int EobertaDB;
        int OobertaDB;
        try {
            String query = "SELECT * FROM words WHERE word = '" + word + "'";
            Cursor cursor = MainActivity.database.rawQuery(query, null);
            if (cursor.getCount() > 0){
                EobertaDB = cursor.getInt(cursor.getColumnIndex("Eoberta"));
                OobertaDB = cursor.getInt(cursor.getColumnIndex("Ooberta"));
                bundle.putInt("Eoberta", EobertaDB);
                bundle.putInt("Ooberta", OobertaDB);
                cursor.close();
                return bundle;
            } else {
                if (isNetworkAvailable()){
                    try {
                        bundle1 = getWord.execute(word).get();
                        EobertaDB = bundle1.getInt("Eoberta");
                        OobertaDB = bundle1.getInt("Ooberta");
                        phoneticus = bundle1.getString("phonetics");

                        try {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("word", word);
                            contentValues.put("phonetics", phoneticus);
                            contentValues.put("Eoberta", EobertaDB);
                            contentValues.put("Ooberta", OobertaDB);
                            MainActivity.database.insert("words", null, contentValues);
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                        return bundle1;

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            if (isNetworkAvailable()){
                try {
                    bundle1 = getWord.execute(word).get();
                    EobertaDB = bundle1.getInt("Eoberta");
                    OobertaDB = bundle1.getInt("Ooberta");
                    phoneticus = bundle1.getString("phonetics");

                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("word", word);
                        contentValues.put("phonetics", phoneticus);
                        contentValues.put("Eoberta", EobertaDB);
                        contentValues.put("Ooberta", OobertaDB);
                        MainActivity.database.insert("words", null, contentValues);
                    } catch (Exception er){
                        er.printStackTrace();
                    }

                    return bundle1;

                } catch (Exception er){
                    er.printStackTrace();
                }
            }
        }

        return  null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_word);

        checkBt = findViewById(R.id.checkBt);
        wordEt = findViewById(R.id.editWord);
        resTxt = findViewById(R.id.resTxt);
        constraintLayout = findViewById(R.id.checkWParent);
        homeIv = findViewById(R.id.homeCW);

        homeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                startActivity(intent);
            }
        });

        wordEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordEt.setCursorVisible(true);
            }
        });

        checkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);
                wordEt.setCursorVisible(false);
                int EobertaRes;
                int OobertaRes;
                String wordReq = wordEt.getText().toString();
                Bundle bundle;
                bundle = checkWord(wordReq);

                if (bundle != null){
                    EobertaRes = bundle.getInt("Eoberta");
                    OobertaRes = bundle.getInt("Ooberta");

                    if (EobertaRes == 1 && OobertaRes == 1){
                        resTxt.setText(wordReq + ": e oberta, o oberta" );
                    } else if (EobertaRes == 1 && OobertaRes == 0){
                        resTxt.setText(wordReq + ": e oberta, o tancada" );
                    } else if (EobertaRes == 0 && OobertaRes == 1){
                        resTxt.setText(wordReq + ": e tancada, o oberta" );
                    } else if (EobertaRes == 0 && OobertaRes == 0){
                        resTxt.setText(wordReq + ": e tancada, o tancada" );
                    }

                } else {
                    resTxt.setText("An error has ocurred, please try another word");
                }



            }
        });


    }
}
