/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idi.Entity;

import static android.R.attr.path;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.DisplayMetrics;
import com.idi.Enemigo.Enemigo;
import com.idi.Formaciones.A;
import com.idi.Formaciones.Formacion;
import com.idi.galaxiamelona.SoundManager;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ymeloa
 */
public class Escena {

    Estadistica estadistica;
    Jugador jugador;
    Formacion formacion;

    ArrayList<Disparo> disparosJugador = new ArrayList<Disparo>();
    ArrayList<Disparo> disparosEnemigos = new ArrayList<Disparo>();
    ArrayList<Colision> colisiones = new ArrayList<Colision>();

    SoundManager musica;

    private final AssetManager asset;
    TexturasManager texturasManager;

    public Escena(int nivel, Context context, AssetManager asset) {
        
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Constantes.ANCHO_PANTALLA = metrics.widthPixels;
        Constantes.LARGO_PANTALLA = metrics.heightPixels;
        
        texturasManager = new TexturasManager(asset);
        this.asset = asset;

        musica = new SoundManager(asset);
        estadistica = new Estadistica(nivel, context);

        jugador = new Jugador(Constantes.POSICION_INICIAL_JUGADOR_X, Constantes.POSICION_INICIAL_JUGADOR_Y,
                Constantes.VIDAS_JUGADOR, texturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_JUGADOR));

        if (nivel == 1) {
            formacion = new A(jugador);
        }
    }

    public void colisiones() {
        colisionesDisparosJugador();
        colisionesDisparosEnemigo();
    }

    public void colisionesDisparosJugador() {

        Iterator<Enemigo> itEnemigos = formacion.getEnemigos().iterator();
        Iterator<Disparo> itDisparosJugador = disparosJugador.iterator();
        Disparo disparo = null;
        Enemigo enemigo = null;
        int tocado;
        while (itDisparosJugador.hasNext()) {
            tocado = 0;
            disparo = itDisparosJugador.next();
            while (itEnemigos.hasNext() && tocado == 0) {
                enemigo = itEnemigos.next();
                if (enemigo.getRectangle().intersect(disparo.getRectangle())) {
                    tocado = 1;
                    System.out.println("colision, enemigo muerto -enemigo -disparojugador +colision");
                    estadistica.addBonus(enemigo.getBonus());
                    colisiones.add(crearColision(enemigo.getX(), enemigo.getY()));
                    musica.explocion();
                    itDisparosJugador.remove();
                    itEnemigos.remove();
                }
            }
        }
        //colissiones con atacantes
        itEnemigos = formacion.getAtacantes().iterator();
        itDisparosJugador = disparosJugador.iterator();
        while (itDisparosJugador.hasNext()) {
            tocado = 0;
            disparo = itDisparosJugador.next();
            while (itEnemigos.hasNext() && tocado == 0) {
                enemigo = itEnemigos.next();
                if (enemigo.getRectangle().intersect(disparo.getRectangle())) {
                    tocado = 1;
                    System.out.println("colision, atacante muerto -enemigo -disparojugador +colision");
                    estadistica.addBonus(enemigo.getBonus());
                    colisiones.add(crearColision(enemigo.getX(), enemigo.getY()));
                    musica.explocion();
                    itDisparosJugador.remove();
                    itEnemigos.remove();
                }
            }
        }

        //FALTA ELIMINAR COLIISIONES DEIFINITIVAMENTE
        // avanzar colisiones antiguas, detectar colisiones en 3 estado
		/*Iterator<Colision> itColisiones = colisiones.iterator();
         Colision colision = null;
         while (itColisiones.hasNext()) {
         colision = itColisiones.next();
         System.out.println("colision avanza");
         int aux = colision.avanzarColision();
         //if (aux == 1)
         //itColisiones.remove();
         }*/
    }

    public void colisionesDisparosEnemigo() {
        int tocado = 0;
        Iterator<Disparo> it = disparosEnemigos.iterator();
        Disparo disparo = null;
        while (it.hasNext() && tocado == 0) {
            disparo = it.next();
            if (jugador.getRectangle().intersect(disparo.getRectangle())) {
                System.out.println("ME HAN DADO!");
                jugador.tocado();
                tocado = 1;
                it.remove();
            }
        }
    }

    public void avanzarDisparos() {
        avanzarDisparosJugador();
        avanzarDisparosEnemigos();
    }

    public void moverFormacion() {
        formacion.mover(disparosEnemigos);
    }

    public void avanzarDisparosJugador() {
        for (int i = 0; i < disparosJugador.size(); ++i) {
            disparosJugador.get(i).moveDisparoJugador();
        }

        Iterator<Disparo> it = disparosJugador.iterator();
        Disparo disparo = null;
        while (it.hasNext()) {
            disparo = it.next();
            if (disparo.getY() < 0 || disparo.getY() > 480) {
                it.remove();
            }
        }
    }

    public void avanzarDisparosEnemigos() {
        for (int i = 0; i < disparosEnemigos.size(); ++i) {
            disparosEnemigos.get(i).moveDisparoEnemigo();
        }

        Iterator<Disparo> it = disparosEnemigos.iterator();
        Disparo disparo = null;
        while (it.hasNext()) {
            disparo = it.next();
            if (disparo.getY() < 0 || disparo.getY() > 480) {
                it.remove();
            }
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Formacion getFormacion() {
        return formacion;
    }

    public void setFormacion(Formacion formacion) {
        this.formacion = formacion;
    }

    public List<Disparo> getDisparosJugador() {
        return disparosJugador;
    }

    public void setDisparosJugador(ArrayList<Disparo> disparos) {
        this.disparosJugador = disparos;
    }

    public ArrayList<Disparo> getDisparosEnemigos() {
        return disparosEnemigos;
    }

    public void setDisparosEnemigos(ArrayList<Disparo> disparosEnemigos) {
        this.disparosEnemigos = disparosEnemigos;
    }

    public void addDisparo(Disparo d) {
        if (d != null) {
            disparosJugador.add(d);
        }
    }

    public ArrayList<Colision> getColisiones() {
        return colisiones;
    }

    public void setColisiones(ArrayList<Colision> colisiones) {
        this.colisiones = colisiones;
    }

    private Colision crearColision(float x, float y) {
        return new Colision(x, y, asset, TexturasManager.getAnimacionExplocionEnemigo(x));
    }

    public void jugadorDispara() {
        addDisparo(jugador.disparaJugador());
    }

}
