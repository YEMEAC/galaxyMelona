package com.idi.Formaciones;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import com.idi.Enemigo.Cabo;
import com.idi.Enemigo.Coronel;
import com.idi.Enemigo.DisparoEnemigo;
import com.idi.Enemigo.Enemigo;
import com.idi.Enemigo.Sargento;
import com.idi.Enemigo.Teniente;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import com.idi.Entity.Jugador;
import com.idi.Entity.TexturasManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class A extends Formacion {

    //esto abra que cambiar par que sea una rraylist de texturas enfuncion deltipo de nave
    public A(Jugador jugador) {
        super(jugador);
        int x = 24;
        int y = 20;
        int filas=4; int columas=7;
        //formacion cuadrada
        ArrayList<Integer> coordenadasIniciales = new ArrayList<Integer>();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columas; j++) {
                coordenadasIniciales.add(i);
                coordenadasIniciales.add(j);
            }
        }

        construirFormacion(coordenadasIniciales, filas, columas);

    }

    @Override
    void construirFormacion(ArrayList<Integer> coordenadasIniciales, int numeroFilas, int numeroColumnas) {

        int escala = 1;
        int topBuffer = (int) ((Constantes.LARGO_PANTALLA / 15) * escala);
        int size = (int) ((Constantes.LARGO_PANTALLA / 30) * escala) + 10;
        int margen = (int) ((size / 5) * escala);

        int fila;
        int columna;
        int count = 0;
        //for (int fil = 0; fil < numeroFilas; fil++) {

        while (count < coordenadasIniciales.size()) {
            fila = coordenadasIniciales.get(count);
            columna = coordenadasIniciales.get(count+1);
          
            if (fila == 0) {
                if(columna%2==0)
                    enemigos.add(new Teniente((columna * size) + (columna * margen), (fila * size) + (fila * margen) + topBuffer));
                else
                     enemigos.add(new Coronel((columna * size) + (columna * margen), (fila * size) + (fila * margen) + topBuffer));
                count += 2;
            } else if (fila == 1) {
                enemigos.add(new Sargento((columna * size) + (columna * margen), (fila * size) + (fila * margen) + topBuffer));
                count += 2;
            } else {
                enemigos.add(new Cabo((columna * size) + (columna * margen), (fila * size) + (fila * margen) + topBuffer));
                count += 2;
            }

        }
        //}
    }

    public void moverBloque() {
        if (saltos == Constantes.SALTOS_BLOQUE_ENEMIGO) {
            desplazamiento *= -1;
            saltos = 0;
        } else {
            ++saltos;
        }

        for (int i = 0; i < enemigos.size(); ++i) {
            enemigos.get(i).setX(enemigos.get(i).getX() + desplazamiento);
        }

    }

    public void moverAtacantes(ArrayList<DisparoEnemigo> disparosEnemigos) {
        if (!atacantes.isEmpty()) {
            float Xjugador = jugador.getX();
            float Yjugador = jugador.getY();
            for (int i = 0; i < atacantes.size(); ++i) {
                atacantes.get(i).movimientoAtacante(Xjugador, Yjugador, disparosEnemigos);
            }

        } else {
            int min = 0;
            int max = enemigos.size() - 1;
            Random r = new Random();
            for (int i = 0; i < 3 && !enemigos.isEmpty(); ++i) {
                atacantes.add(enemigos.get(r.nextInt(max - min + 1) + min));
            }
            //quito los atacantes de la formacion normal para que no le afecte el desplazamiento general
            for (int i = 0; i < atacantes.size(); ++i) {
                enemigos.remove(atacantes.get(i));
            }
        }
    }

}
