/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idi.Entity;

import java.util.List;
import android.graphics.Rect;
/**
 *
 * @author ymeloa
 */
public class Disparo {
	
    double velocidad;
    int x;
    int y;
    
    public Disparo(){
        x=y=0;
    }
    
    public Disparo(int x, int y) {
      this.x=x;
      this.y=y;
    }
    
    public void moveDisparoJugador(){
        this.y-=Constantes.VELOCIDAD_DISPARO_JUGADOR;
    } 
    
    public void moveDisparoEnemigo(){
        this.y+=Constantes.VELOCIDAD_DISPARO_ENEMIGO;
    }

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}


	/*public List<Enemigo> getEnemigos() {
		return enemigos;
	}

	public void setEnemigos(List<Enemigo> enemigos) {
		this.enemigos = enemigos;
	}*/

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public Rect getRectangle() {
		return new Rect(x, y, x + Constantes.TAMANO_LADO_NAVE_ENEMIGO, y + Constantes.TAMANO_LADO_NAVE_ENEMIGO);
	} 
}
