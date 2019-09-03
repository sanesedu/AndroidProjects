package com.jhcernuda.tresenraya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Partida partida;
    private ImageView[] casillas;
    private int jugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout tablero = (GridLayout) findViewById(R.id.tablero);
        casillas = new ImageView[9];
        int tamLado =(int) getResources().getDimension(R.dimen.casilla);
        LinearLayout.LayoutParams lado = new LinearLayout.LayoutParams(tamLado, tamLado);
        View.OnClickListener celda = new View.OnClickListener(){
            public void onClick(View view) {toque(view);}
        };
        for (int i = 0; i < 9; i++){
            casillas[i] = new ImageView(this);
            casillas[i].setId(1000+i);
            if (i%2==0){
                casillas[i].setImageResource(R.drawable.aspa);
            } else {
                casillas[i].setImageResource(R.drawable.circulo);
            }
            casillas[i].setLayoutParams(lado);
            casillas[i].setOnClickListener(celda);
            tablero.addView(casillas[i]);
        }
    }

    public void nuevoJuego(View view){
        for (ImageView casilla: casillas){
            casilla.setImageResource(R.drawable.casilla);
        }
        jugadores = 1;
        if (view.getId() == R.id.dosjug) jugadores=2;
        RadioGroup config = (RadioGroup)findViewById(R.id.config);
        int id = config.getCheckedRadioButtonId();
        int dificultad = 0;
        if (id== R.id.normal) dificultad = 1;
        else if(id == R.id.imposible) dificultad = 2;
        partida = new Partida(dificultad);
        ((Button)findViewById(R.id.unjug)).setEnabled(false);
        ((RadioGroup)findViewById(R.id.config)).setAlpha(0);
        ((Button)findViewById(R.id.dosjug)).setEnabled(false);
    }

    public void toque(View view){
        if (partida == null) return;
        int casilla = 0;
        for (int i = 0; i < 9; i++){
            if (casillas[i].getId() == view.getId()){
                casilla = i;
                break;
            }
        }
        if (!partida.marca(casilla)) return;
        marca(casilla);
        int resultado = partida.turno();
        if (resultado > 0){
            termina(resultado);
            return;
        }
        if (jugadores == 2) return;
        casilla = partida.ai();
        partida.marca(casilla);
        marca(casilla);
        resultado = partida.turno();
        if (resultado > 0) termina(resultado);
    }

    private void marca(int casilla){
        ImageView imagen = casillas[casilla];
        if (partida.jugador==1) imagen.setImageResource(R.drawable.circulo);
        else imagen.setImageResource(R.drawable.aspa);
    }

    private void termina(int resultado){
        String m;
        if (resultado==1) m = getString(R.string.circulos);
        else if (resultado==2) m = getString(R.string.aspas);
        else m = getString(R.string.empate);
        Toast toast = Toast.makeText(this, m, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        partida = null;
        ((Button)findViewById(R.id.unjug)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.config)).setAlpha(1);
        ((Button)findViewById(R.id.dosjug)).setEnabled(true);
    }
}
