/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idi.Entity;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.List;

/**
 *
 * @author ymeloa
 */
public abstract class Disparo {

    protected double velocidad;
    protected float x;
    protected float y;
    protected Bitmap imagen;

    public Disparo() {
        x = y = 0;
    }

    public Disparo(float x, float y, Bitmap imagen) {
        this.x = x;
        this.y = y;
        this.imagen=imagen;
    }
    
    public abstract void mover();
    
    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
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

    public RectF getRectangle() {
        return new RectF(x, y, x + imagen.getWidth(), y + imagen.getHeight());
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
