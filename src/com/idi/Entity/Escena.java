package com.idi.Entity;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import com.idi.Enemigo.DisparoEnemigo;
import com.idi.Enemigo.Enemigo;
import com.idi.Formaciones.A;
import com.idi.Formaciones.Formacion;
import com.idi.escena.EscenaView;
import com.idi.galaxiamelona.SoundManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ymeloa
 */
public class Escena {

    private EscenaView parent;
    private Estadistica estadistica;
    private Jugador jugador;
    private Formacion formacion;
    private Set<Integer> teclas;

    private ArrayList<DisparoJugador> disparosJugador = new ArrayList<DisparoJugador>();
    private ArrayList<DisparoEnemigo> disparosEnemigos = new ArrayList<DisparoEnemigo>();
    private ArrayList<Colision> colisiones = new ArrayList<Colision>();

    private SoundManager musica;

    private final AssetManager asset;
    private TexturasManager texturasManager;

    public Escena(int nivel, Context context, AssetManager asset, EscenaView v) {
        teclas = new HashSet<Integer>();
        this.parent = v;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Constantes.ANCHO_PANTALLA = metrics.widthPixels;
        Constantes.LARGO_PANTALLA = metrics.heightPixels;
        Constantes.POSICION_INICIAL_JUGADOR_X = metrics.widthPixels / 2;
        Constantes.POSICION_INICIAL_JUGADOR_Y = metrics.heightPixels - 90;

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
        colisionesDisparosEnemigoYAtacantes();
    }

    public void comprobarFinDeJugador() {
        if ((jugador.getVidas() < 1 || (formacion.getAtacantes().isEmpty() && formacion.getEnemigos().isEmpty()))
                && colisiones.isEmpty()) { // colisiones empty para que deje reproducir la explocion final del jugador
            //si pongo mas formaciones hay que poner otro flag de no quedan formaciones
            estadistica.actualizarGuardarPuntucion(parent.getContext());
            parent.setGameOver(true);
            parent.setInicioGameOver((Calendar.getInstance()).getTime());
        }
    }

    public void colisionesDisparosJugador() {

        Iterator<Enemigo> itEnemigos;
        Iterator<DisparoJugador> itDisparosJugador = disparosJugador.iterator();
        Disparo disparo = null;
        Enemigo enemigo = null;
        int tocado;
        while (itDisparosJugador.hasNext()) {
            tocado = 0;
            disparo = itDisparosJugador.next();
            itEnemigos = formacion.getEnemigos().iterator();  //ojo aqui hay que reiniciar este iterador por cada disparo para que vea todas las naves!!
            while (itEnemigos.hasNext() && tocado == 0) {
                enemigo = itEnemigos.next();
                if (enemigo.getRectangle().intersect(disparo.getRectangle())) {
                    tocado = 1;
                    estadistica.addBonus(enemigo.getBonus());
                    colisiones.add(crearColision(enemigo.getX(), enemigo.getY()));
                    musica.explocion();
                    itDisparosJugador.remove();
                    itEnemigos.remove();
                }
            }
        }
        //colissiones con atacantes 
        itDisparosJugador = disparosJugador.iterator();
        while (itDisparosJugador.hasNext()) {
            tocado = 0;
            disparo = itDisparosJugador.next();
            itEnemigos = formacion.getAtacantes().iterator();
            while (itEnemigos.hasNext() && tocado == 0) {
                enemigo = itEnemigos.next();
                if (enemigo.getRectangle().intersect(disparo.getRectangle())) {
                    itDisparosJugador.remove();
                    tocado = 1;
                    estadistica.addBonus(enemigo.getBonus());
                    colisiones.add(crearColision(enemigo.getX(), enemigo.getY()));
                    musica.explocion();
                    itEnemigos.remove();
                }
            }
        }

        //FALTA ELIMINAR COLIISIONES DEIFINITIVAMENTE
        Iterator<Colision> itColisiones = colisiones.iterator();
        Colision colision;
        while (itColisiones.hasNext()) {
            colision = itColisiones.next();
            if (colision.getFotograma() >= Constantes.FOTOGRAMAS_COLISION) {
                itColisiones.remove();
            }
        }
    }

    public void colisionesDisparosEnemigoYAtacantes() {
        int tocado = 0; //solo me puede tocar una vez por comprobacion
        Iterator<DisparoEnemigo> it = disparosEnemigos.iterator();
        Disparo disparo;
        while (it.hasNext() && tocado == 0) {
            disparo = it.next();
            if (jugador.getRectangle().intersect(disparo.getRectangle())) {
                jugador.tocado();
                tocado = 1;
                it.remove();
                colisiones.add(crearColision(disparo.getX(), disparo.getY()));
                musica.explocion();
            }
        }

        Iterator<Enemigo> it2 = formacion.getAtacantes().iterator();
        Enemigo atacante;
        while (it2.hasNext() && tocado == 0) {
            atacante = it2.next();
            if (jugador.getRectangle().intersect(atacante.getRectangle())) {
                jugador.tocado();
                tocado = 1;
                it2.remove();
                colisiones.add(crearColision(atacante.getX(), atacante.getY()));
                musica.explocion();
            }
        }
    }

    public void avanzarDisparos() {
        avanzarDisparosJugador();
        avanzarDisparosEnemigos();
    }

    public void moverFormacion() {
        formacion.moverFormacion(disparosEnemigos);
    }

    public void avanzarDisparosJugador() {
        for (int i = 0; i < disparosJugador.size(); ++i) {
            disparosJugador.get(i).mover();
        }

        Iterator<DisparoJugador> it = disparosJugador.iterator();
        Disparo disparo;
        while (it.hasNext()) {
            disparo = it.next();
            if (disparo.getY() < 0 || disparo.getY() > Constantes.LARGO_PANTALLA) {
                it.remove();
            }
        }
    }

    public void avanzarDisparosEnemigos() {
        for (int i = 0; i < disparosEnemigos.size(); ++i) {
            disparosEnemigos.get(i).mover();
        }

        Iterator<DisparoEnemigo> it = disparosEnemigos.iterator();
        Disparo disparo;
        while (it.hasNext()) {
            disparo = it.next();
            if (disparo.getY() < 0 || disparo.getY() > Constantes.LARGO_PANTALLA) {
                it.remove();
            }
        }
    }
    
    public void movimientoJugador() {
        if (teclas.contains(KeyEvent.KEYCODE_D)) { //A
            getJugador().moverDerecha();
        }

        if (teclas.contains(KeyEvent.KEYCODE_A)) { //D
            getJugador().MoverIzquierda();
        }

        if (teclas.contains(KeyEvent.KEYCODE_W)) { // W
            jugadorDispara();
        }


        if (teclas.contains(KeyEvent.KEYCODE_ESCAPE) || teclas.contains(KeyEvent.KEYCODE_BACK)) { // W
            parent.getParentView().finalizarEscena();
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

    public List<DisparoJugador> getDisparosJugador() {
        return disparosJugador;
    }

    public void setDisparosJugador(ArrayList<DisparoJugador> disparos) {
        this.disparosJugador = disparos;
    }

    public ArrayList<DisparoEnemigo> getDisparosEnemigos() {
        return disparosEnemigos;
    }

    public void setDisparosEnemigos(ArrayList<DisparoEnemigo> disparosEnemigos) {
        this.disparosEnemigos = disparosEnemigos;
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

    public void addDisparo(DisparoJugador d) {
        if (d != null) {
            disparosJugador.add(d);
        }
    }

    public void jugadorDispara() {
        addDisparo(jugador.dispara());
    }

    public Estadistica getEstadistica() {
        return estadistica;
    }

    public void setEstadistica(Estadistica estadistica) {
        this.estadistica = estadistica;
    }

    public SoundManager getSoundManager() {
        return musica;
    }

    public void setSoundManager(SoundManager musica) {
        this.musica = musica;
    }

    public TexturasManager getTexturasManager() {
        return texturasManager;
    }

    public void setTexturasManager(TexturasManager texturasManager) {
        this.texturasManager = texturasManager;
    }

    public Set<Integer> getTeclas() {
        return teclas;
    }

    public void setTeclas(Set<Integer> teclas) {
        this.teclas = teclas;
    }

  

    
}
