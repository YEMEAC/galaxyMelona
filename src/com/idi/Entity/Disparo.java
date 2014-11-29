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

    double velocidad;
    float x;
    float y;
    protected Bitmap imagen;

    public Disparo() {
        x = y = 0;
    }

    public Disparo(float x, float y, Bitmap imagen) {
        this.x = x;
        this.y = y;
        this.imagen=imagen;
    }

    public void moveDisparoJugador() {
        this.y -= Constantes.VELOCIDAD_DISPARO_JUGADOR;
    }

    public void moveDisparoEnemigo() {
        this.y += Constantes.VELOCIDAD_DISPARO_ENEMIGO;
    }

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
        return new RectF(x, y, x + Constantes.TAMANO_LADO_NAVE_ENEMIGO, y + Constantes.TAMANO_LADO_NAVE_ENEMIGO);
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

}
