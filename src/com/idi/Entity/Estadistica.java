package com.idi.Entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.widget.Chronometer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Estadistica {

    private int nivel;
    private long puntuacionActual;
    private Date inicio;
    private boolean nuevoRecord;
    private Set<Long> HistoricoPuntuacionesLong;

    public Estadistica(int nivel2, Context context) {
        nivel = nivel2;
        puntuacionActual = 0;
        inicio = (Calendar.getInstance()).getTime();
        recuperarPuntuaciones(context);
    }

    private void recuperarPuntuaciones(Context context) {
        String puntuacionesAux = PreferenceManager.getDefaultSharedPreferences(context).getString("PUNTUACIONES", "vacio");
        List<String> HistoricoPuntuacionesString;

        if (!puntuacionesAux.equals("vacio")) {
            HistoricoPuntuacionesString = Arrays.asList(puntuacionesAux.split("/"));
        } else {
            HistoricoPuntuacionesString = new ArrayList<String>();
        }

        HistoricoPuntuacionesLong = new TreeSet<Long>();
        for (int i = 0; i < HistoricoPuntuacionesString.size(); ++i) {
            HistoricoPuntuacionesLong.add(Long.parseLong(HistoricoPuntuacionesString.get(i)));
        }
    }

    //devuelve true si he superado algun record
    void actualizarGuardarPuntucion(Context context) {
        boolean reemplazo = false;
        Iterator<Long> it = HistoricoPuntuacionesLong.iterator();
        while (it.hasNext() && !reemplazo) {
            if (it.next() < puntuacionActual) {
                reemplazo = true;
            }
        }

        HistoricoPuntuacionesLong.add(puntuacionActual);

        StringBuilder aux = new StringBuilder();
        it = HistoricoPuntuacionesLong.iterator();

        //solo queremos los 5 mas grandes y este aray no tendra como maximo 6
        //asi que elimino el primero que sera el mas peque porque esta lista esta ordenada y sin repetidos
        while (HistoricoPuntuacionesLong.size() > Constantes.NUMERO_RECORS_GUARDADOS && it.hasNext()) {
            it.next();
            it.remove();
        }

        it = HistoricoPuntuacionesLong.iterator();
        while (it.hasNext()) {
            aux.append(Long.toString(it.next()) + "/");
        }

        boolean commit = PreferenceManager.getDefaultSharedPreferences(context).edit().putString("PUNTUACIONES", aux.toString()).commit();
        nuevoRecord=reemplazo;        
    }

    public void pintaPuntuacion(Canvas canvas) {
        long a = getPuntuacion();

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(25);
        canvas.drawText("SCORE " + a, (Constantes.ANCHO_PANTALLA / 2) - 50, 45, paint);
    }

    public void pintaCronometro(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        Date now = (Calendar.getInstance()).getTime();

        long tiempo = now.getTime() - inicio.getTime();

        int minutes = (int) (tiempo / (60 * 1000));
        int restante = (int) (tiempo % (60 * 1000));

        int segundos = (int) (restante / 1000);

        int mili = (int) (((int) tiempo % 1000) / 100);

        String a = minutes + ":" + segundos + ":" + mili;
        paint.setTextSize(25);
        canvas.drawText("TIME " + a, Constantes.ANCHO_PANTALLA - 170, 45, paint);
    }

    public void addBonus(long a) {
        puntuacionActual += a;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public long getPuntuacion() {
        return puntuacionActual;
    }

    public void setPuntuacion(long puntuacion) {
        this.puntuacionActual = puntuacion;
    }

    public long getPuntuacionActual() {
        return puntuacionActual;
    }

    public void setPuntuacionActual(long puntuacionActual) {
        this.puntuacionActual = puntuacionActual;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Set<Long> getHistoricoPuntuacionesLong() {
        return HistoricoPuntuacionesLong;
    }

    public void setHistoricoPuntuacionesLong(Set<Long> HistoricoPuntuacionesLong) {
        this.HistoricoPuntuacionesLong = HistoricoPuntuacionesLong;
    }

    public boolean isNuevoRecord() {
        return nuevoRecord;
    }

    public void setNuevoRecord(boolean nuevoRecord) {
        this.nuevoRecord = nuevoRecord;
    }

}
