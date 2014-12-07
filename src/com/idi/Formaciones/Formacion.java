package com.idi.Formaciones;

import android.graphics.Bitmap;
import com.idi.Enemigo.Cabo;
import com.idi.Enemigo.DisparoEnemigo;
import com.idi.Enemigo.Enemigo;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import com.idi.Entity.Jugador;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public abstract class Formacion {

    protected Date ultimoMovimientoBloque;
    protected Date ultimoMovimientoAtacantes;
    protected int saltos = 0;
    protected int desplazamiento = Constantes.LOGINTUD_SALTO_BLOQUE_ENEMIGO;
    protected ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();
    protected ArrayList<Enemigo> atacantes = new ArrayList<Enemigo>();
    Jugador jugador;
  
    abstract void construirFormacion(ArrayList<Integer> coordenadasIniciales, int numeroFilas, int numeroColumnas);
    
    public Formacion(Jugador jugador) {
        this.jugador = jugador;
        ultimoMovimientoBloque = (Calendar.getInstance()).getTime();
        ultimoMovimientoAtacantes = (Calendar.getInstance()).getTime();
    }

    public void moverFormacion(ArrayList<DisparoEnemigo> disparosEnemigos) {

         moverBloque();
         moverAtacantes(disparosEnemigos);
 
    }

    public abstract void moverBloque();

    public abstract void moverAtacantes(ArrayList<DisparoEnemigo> disparosEnemigos);

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
