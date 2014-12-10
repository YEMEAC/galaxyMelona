package com.idi.galaxiamelona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.idi.Entity.Constantes;

public class MenuActivity extends Activity {

    boolean flaginstruciones;
    float  antiguaYinstruciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anadirListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void ocultarBarras() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void empiezaJuego() {
        Intent juego = new Intent(this, EscenaActivity.class);
        this.startActivity(juego);
    }

    private void anadirListeners() {

        TextView jugar = (TextView) findViewById(R.id.boton_jugar);
        jugar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                empiezaJuego();
            }
        });

        TextView salir = (TextView) findViewById(R.id.boton_salir);
        salir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TextView instrucciones = (TextView) findViewById(R.id.boton_instrucciones);
        instrucciones.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView salir = (TextView) findViewById(R.id.boton_salir);
                TextView jugar = (TextView) findViewById(R.id.boton_jugar);

                if (!flaginstruciones) {
                    salir.setVisibility(View.INVISIBLE);
                    jugar.setVisibility(View.INVISIBLE);
                    antiguaYinstruciones=instrucciones.getY();
                    instrucciones.setY(1280-(1280/7));
                    instrucciones.setText("Volver");

                }else{
                    salir.setVisibility(View.VISIBLE);
                    jugar.setVisibility(View.VISIBLE);
                    instrucciones.setY(antiguaYinstruciones);
                    instrucciones.setText("Intrucciones");
                }
                flaginstruciones = !flaginstruciones;
            }
        });

    }
}
