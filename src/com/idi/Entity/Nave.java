/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idi.Entity;

import android.graphics.Color;
import android.graphics.Rect;

/**
 *
 * @author ymeloa
 */
public abstract class Nave {
    
	protected int x;
    protected int y;
    protected int vidas;
    protected int velocidad;
    protected int color = Color.BLACK;
    //RectF dimension = new RectF();
    
    //enemigas basicas
    public Nave(int x, int y, int v){
        velocidad=1;
        vidas=v;
        this.x=x;
        this.y=y;
    }
    
    //jugador
    public Nave (int x, int y, int vidas, int velocidad){
        this.vidas=vidas;
        this.velocidad=velocidad;
        this.x=x;
        this.y=y;
    }
    
    public int getVidas() {
        return vidas;
    }    
    
    public void moverDerecha(){
        x+=1*velocidad;
        
    }
    
    public void MoverIzqueirda(){
        x-=1*velocidad;
    }
    
    public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
	

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

	public abstract void avanzar();
    public abstract void retroceder();
    //public abstract Disparo dispara();
    public abstract Rect getRectangle();
}
