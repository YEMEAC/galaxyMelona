package com.idi.Formaciones;

import android.graphics.Bitmap;
import com.idi.Enemigo.Cabo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.idi.Enemigo.Enemigo;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import com.idi.Entity.Jugador;

public abstract class Formacion {

    protected Date ultimoMovimientoBloque;
    protected Date ultimoMovimientoAtacantes;
    protected int saltos = Constantes.SALTOS_BLOQUE_ENEMIGO;
    protected int desplazamiento = Constantes.LOGINTUD_SALTO_BLOQUE_ENEMIGO;
    protected ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();
    protected ArrayList<Enemigo> atacantes = new ArrayList<Enemigo>();
    Jugador jugador;

    protected void construirFormacion(Bitmap imagen){
        
    }
            
    protected  void construirFormacion(ArrayList<Integer> coordenadasIniciales, int numeroFilas, int numeroColumnas,int salud, Bitmap textura) {

        int escala=1;
        int topBuffer = (int) ((Constantes.LARGO_PANTALLA / 15) * escala);
        int size = (int) ((Constantes.LARGO_PANTALLA / 30) * escala)+10;
        int margen = (int) ((size / 5) * escala);
        
        int fila;
        int columna;
        for (int fil = 0; fil < numeroFilas; fil++) {
            fila=coordenadasIniciales.get(fil);
            for (int col = 0; col < numeroColumnas; col++) {
                columna=coordenadasIniciales.get(col);
                enemigos.add(new Cabo((col * size) + (col * margen), (fil * size) + (fil * margen) + topBuffer));    
            }
        }
    }

    public Formacion(Jugador jugador) {
        this.jugador = jugador;
        ultimoMovimientoBloque = (Calendar.getInstance()).getTime();
        ultimoMovimientoAtacantes = (Calendar.getInstance()).getTime();
    }

    public void mover(ArrayList<Disparo> disparosEnemigos) {

        Date actual = (Calendar.getInstance()).getTime();
        if (actual.getTime() - ultimoMovimientoBloque.getTime() >= Constantes.DELAY_DESPLAZAMIENTO_MOVIMIENTO_FORMACION) {
            moverBloque();
            ultimoMovimientoBloque = (Calendar.getInstance()).getTime();
        }

        if (actual.getTime() - ultimoMovimientoAtacantes.getTime() >= Constantes.DELAY_DESPLAZAMIENTO_MOVIMIENTO_ATACANTES) {
            moverAtacantes(disparosEnemigos);
            ultimoMovimientoAtacantes = (Calendar.getInstance()).getTime();
        }
    }

    public abstract void moverBloque();

    public abstract void moverAtacantes(ArrayList<Disparo> disparosEnemigos);

    public int getSaltos() {
        return saltos;
    }

    public void setSaltos(int saltos) {
        this.saltos = saltos;
    }

    public int getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(int desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void setEnemigos(ArrayList<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

    public ArrayList<Enemigo> getAtacantes() {
        return atacantes;
    }

    public void setAtacantes(ArrayList<Enemigo> atacantes) {
        this.atacantes = atacantes;
    }

}
