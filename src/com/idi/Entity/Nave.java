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
import java.util.Date;

/**
 *
 * @author ymeloa
 */
public abstract class Nave {
    
    protected float  x;
    protected float y;
    protected int vidas;
    protected int velocidad=Constantes.VELOCIDA_INICIAL_JUGADOR;
  
    protected Date ultimoDisparo;
    
    //enemigas basicas
    public Nave(float x, float y, int v){
        vidas=v;
        this.x=x;
        this.y=y;
    }
    
    public int getVidas() {
        return vidas;
    }    
    
    
    public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
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

    public Date getUltimoDisparo() {
        return ultimoDisparo;
    }

    public void setUltimoDisparo(Date ultimoDisparo) {
        this.ultimoDisparo = ultimoDisparo;
    }
    
    public void aumentarVidas(){
        ++vidas;
    }
    
    public abstract void avanzar();
    public abstract void retroceder();
    public abstract Disparo dispara();
    public abstract RectF getRectangle();
}
