/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idi.Entity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 *
 * @author ymeloa
 */
public abstract class Nave {
    
    protected float  x;
    protected float y;
    protected int vidas;
    protected int velocidad;
    protected int color = Color.BLACK;
    protected Bitmap imagen;
    //RectF dimension = new RectF();
    
    //enemigas basicas
    public Nave(float x, float y, int v, Bitmap imagen){
        velocidad=1;
        vidas=v;
        this.x=x;
        this.y=y;
        this.imagen=imagen;
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
	

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
    
    public abstract void avanzar();
    public abstract void retroceder();
    //public abstract Disparo dispara();
    public abstract RectF getRectangle();
}
