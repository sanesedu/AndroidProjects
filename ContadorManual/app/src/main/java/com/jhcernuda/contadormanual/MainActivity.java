package com.jhcernuda.contadormanual;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public int contador;
    TextView resultado;

    @Override
    public void onSaveInstanceState(Bundle estado) {
        super.onSaveInstanceState(estado);
        estado.putInt("cuenta", contador);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = datos.edit();
        editor.putInt("cuenta", contador);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        contador = datos.getInt("cuenta", 0);
        resultado.setText("" + contador);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        resultado = (TextView) findViewById(R.id.cuenta);
        if (savedInstanceState == null){
            contador = 0;
        } else {
            contador = savedInstanceState.getInt("cuenta");
            resultado.setText(""+contador);
        }
        TextView.OnEditorActionListener teclado;
        teclado =  new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    reset(null);
                }
                return false;
            }
        };
        EditText set = (EditText) findViewById(R.id.set);
        set.setOnEditorActionListener(teclado);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void incrementar(View view){
        contador++;
        resultado.setText("" + contador);
    }

    public void decrementar(View view){
        contador--;
        if (contador < 0){
            CheckBox negativos = (CheckBox) findViewById(R.id.negativos);
            if (!negativos.isChecked()){
                contador = 0;
            }
        }

        resultado.setText("" + contador);
    }

    public void negativos (View view){
        CheckBox negativos = (CheckBox)findViewById(R.id.negativos);
        if (!negativos.isChecked() && contador < 0){
            contador = 0;
            resultado.setText("" + contador);
        }
    }

    public void reset(View view){
        EditText e = (EditText) findViewById(R.id.set);
        try {
            contador = Integer.parseInt(e.getText().toString());
        } catch (Exception exception){
            contador = 0;
        }

        e.setText("");
        resultado.setText("" + contador);
        InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(e.getWindowToken(), 0);
    }

}
