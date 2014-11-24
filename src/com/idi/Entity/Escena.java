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

	public Escena(int nivel, Context context) {
		estadistica = new Estadistica(nivel, context);

		jugador = new Jugador(Constantes.POSICION_INICIAL_JUGADOR_X,
				Constantes.POSICION_INICIAL_JUGADOR_Y,
				Constantes.VIDAS_JUGADOR, Constantes.VELOCIDA_INICIAL_JUGADOR);
		
		if (nivel == 1)
			formacion = new A(jugador);
	}
	
	public void colisiones(){
		colisionesDisparosJugador();
		colisionesDisparosEnemigo();
	}

	public void colisionesDisparosJugador() {
		List<Enemigo> enemigos = formacion.getEnemigos();
		ArrayList<Disparo> borrarDisparo = new ArrayList<Disparo>();
		ArrayList<Enemigo> borrarEnemigo = new ArrayList<Enemigo>();

		for (int i = 0; i < disparosJugador.size(); ++i) {
			int tocado=0;
			for (int j = 0; j < enemigos.size() && tocado==0; ++j) {
				if (enemigos.get(j).getRectangle()
						.intersect(disparosJugador.get(i).getRectangle())) {
					
					tocado=0;
					System.out.println("colisio");
					estadistica.addBonus(enemigos.get(j).getBonus());
					borrarDisparo.add(disparosJugador.get(i));
					borrarEnemigo.add(enemigos.get(j));
				}
			}
		}

		//avanzar colisiones antiguas, detectar colisiones en 3 estado
		ArrayList<Colision> borrarColision = new ArrayList<Colision>();
		for (int i=0; i < colisiones.size() ; ++i) {
			System.out.println("colision avanza");
			int aux=colisiones.get(i).avanzarColision();
			if(aux==1)
				borrarColision.add(colisiones.get(i));
		}
		//destruir colisiones en teceres estado
		for (int i =0; i < borrarColision.size() ; ++i) {
			colisiones.remove(borrarColision.get(i));
		}

		//enemigos y disparosJugador a borrar valensiempre igual 1 a 1
		for (int i = 0; i < borrarEnemigo.size(); ++i) {
			System.out.println("colision: borro enemigo/atacante + disparo+ añado colision");
			disparosJugador.remove(borrarDisparo.get(i));
			enemigos.remove(borrarEnemigo.get(i));
			formacion.getAtacantes().remove(borrarEnemigo.get(i));

			// pongo una colision donde estaba la nave enemiga
			colisiones.add(new Colision(borrarEnemigo.get(i).x, borrarEnemigo.get(i).y));
		}
	}
	
	public void colisionesDisparosEnemigo (){
		int tocado=0;
		Disparo d=null;
		for (int i = 0; i < disparosEnemigos.size() && tocado==0; ++i)
			if (jugador.getRectangle().intersect(disparosEnemigos.get(i).getRectangle())) {
				System.out.println("ME HAN DADO!");
				tocado=0;
				d=disparosEnemigos.get(i);
			}	
		if(d!=null)
			disparosEnemigos.remove(d);
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

		if (disparosJugador.size() > 0
				&& (disparosJugador.get(0).getY() < 0 || disparosJugador.get(0).getY() > 480)) {
			System.out.println("hay que borrar el disparo mas antiguo ");
			disparosJugador.remove(0);
		}
	}
	
	public void avanzarDisparosEnemigos() {
		for (int i = 0; i < disparosEnemigos.size(); ++i) 
			disparosEnemigos.get(i).moveDisparoEnemigo();
		
		
		Iterator<Disparo> it = disparosEnemigos.iterator();
		Disparo disparo = null;
	      while(it.hasNext()) {
	    	   disparo=it.next();
	    	  if (disparo.getY() < 0 || disparo.getY() > 480)
					it.remove();
	      }
		
		/*ArrayList<Disparo> disparosEnemigosBorrar = new ArrayList<Disparo>();
		for (int i = 0; i < disparosEnemigos.size(); ++i) 
			if (disparosEnemigos.size() > 0 && (disparosEnemigos.get(0).getY() < 0 || disparosEnemigos.get(0).getY() > 480))
				disparosEnemigosBorrar.add(disparosEnemigos.get(i));
				
		for (int i = 0; i < disparosEnemigosBorrar.size(); ++i) 	{
			System.out.println("hay que borrar un disparo enemigo ");
			disparosEnemigos.remove(disparosEnemigosBorrar.get(i));
		}*/
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
