/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idi.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.idi.Enemigo.Enemigo;
import com.idi.Formaciones.A;
import com.idi.Formaciones.Formacion;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.RectF;

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

	public Escena(int nivel, Context context, AssetManager asset) {
		estadistica = new Estadistica(nivel, context);

		jugador = new Jugador(Constantes.POSICION_INICIAL_JUGADOR_X,
				Constantes.POSICION_INICIAL_JUGADOR_Y,
				Constantes.VIDAS_JUGADOR, Constantes.VELOCIDA_INICIAL_JUGADOR,asset);

		if (nivel == 1)
			formacion = new A(jugador);
	}

	public void colisiones() {
		colisionesDisparosJugador();
		colisionesDisparosEnemigo();
	}

	public void colisionesDisparosJugador() {

		Iterator<Enemigo> itEnemigos =formacion.getEnemigos().iterator();
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
					colisiones.add(new Colision(enemigo.getX(),enemigo.getY()));
					itDisparosJugador.remove();
					itEnemigos.remove();
				}
			}
		}
		//colissiones con atacantes
		itEnemigos=formacion.getAtacantes().iterator();
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
					colisiones.add(new Colision(enemigo.getX(),enemigo.getY()));
					itDisparosJugador.remove();
					itEnemigos.remove();
				}
			}
		}

		// avanzar colisiones antiguas, detectar colisiones en 3 estado
		Iterator<Colision> itColisiones = colisiones.iterator();
		Colision colision = null;
		while (itColisiones.hasNext()) {
			colision = itColisiones.next();
			System.out.println("colision avanza");
			int aux = colision.avanzarColision();
			if (aux == 1)
				itColisiones.remove();
		}
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
		for (int i = 0; i < disparosJugador.size(); ++i)
			disparosJugador.get(i).moveDisparoJugador();

		Iterator<Disparo> it = disparosJugador.iterator();
		Disparo disparo = null;
		while (it.hasNext()) {
			disparo = it.next();
			if (disparo.getY() < 0 || disparo.getY() > 480)
				it.remove();
		}
	}

	public void avanzarDisparosEnemigos() {
		for (int i = 0; i < disparosEnemigos.size(); ++i)
			disparosEnemigos.get(i).moveDisparoEnemigo();

		Iterator<Disparo> it = disparosEnemigos.iterator();
		Disparo disparo = null;
		while (it.hasNext()) {
			disparo = it.next();
			if (disparo.getY() < 0 || disparo.getY() > 480)
				it.remove();
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
		disparosJugador.add(d);
	}

	public ArrayList<Colision> getColisiones() {
		return colisiones;
	}

	public void setColisiones(ArrayList<Colision> colisiones) {
		this.colisiones = colisiones;
	}

}
