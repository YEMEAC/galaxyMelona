package com.idi.Entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Chronometer;
import java.util.Calendar;
import java.util.Date;

public class Estadistica {

    int nivel;
    long puntuacion;
    Date inicio; 
    //private Chronometer cronometro;

    public Estadistica(int nivel2, Context context) {
        nivel = nivel2;
        puntuacion = 0;
        inicio = (Calendar.getInstance()).getTime();
    }


    public void addBonus(long a) {
        puntuacion += a;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public long getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(long puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void pintaPuntuacion(Canvas canvas) {
        long a =getPuntuacion();

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(25);
        canvas.drawText("SCORE " + a, (Constantes.ANCHO_PANTALLA / 2)-50, 45, paint);
    }

    public void pintaCronometro(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        
        Date now = (Calendar.getInstance()).getTime();
       
        long tiempo = now.getTime()-inicio.getTime();

        int minutes = (int)(tiempo / (60 * 1000));
        int restante = (int)(tiempo % (60 * 1000));
        
        int segundos = (int)(restante / 1000);
        
        int mili = (int)(((int)tiempo % 1000) / 100);
        
        String a = minutes + ":" + segundos + ":" + mili;
        paint.setTextSize(25);
        canvas.drawText("TIME " + a, Constantes.ANCHO_PANTALLA-170, 45, paint);
    }
}
