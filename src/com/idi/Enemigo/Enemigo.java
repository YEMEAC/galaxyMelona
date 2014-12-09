/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idi.Enemigo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import com.idi.Entity.Nave;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ymeloa
 */
public abstract class Enemigo extends Nave {

    EnemigoTipo tipo;
    long bonus;
    boolean ultimaImagenUtilizada;
    //ambas imagenes tiene las mismas dimensiones
    protected Bitmap imagen1;
    protected Bitmap imagen2;
    protected Date ultimoDisparo;

    // enemigas basicas
    public Enemigo(float x, float y, int v, Bitmap a, Bitmap b) {
        super(x, y, v);
        imagen1 = a; 
        imagen2 = b;
        ultimaImagenUtilizada = true;
    }
    
     public abstract void movimientoAtacante(float Xjugador, float Yjugador, ArrayList<DisparoEnemigo> disparosEnemigos);
     public abstract long getDelayDisparo();

    public Bitmap getImagenAnimacionBloque() {
        if (ultimaImagenUtilizada) {
            ultimaImagenUtilizada = false;
            return imagen1;
        } else {
            ultimaImagenUtilizada = true;
            return imagen2;
        }
    }

    @Override
    public void avanzar() {
          if(y+velocidad>=Constantes.LARGO_PANTALLA)
            y=0;
        else
          y=y+velocidad; 
    }

    @Override
    public void retroceder() {
         if(y-velocidad<=0)
            y=Constantes.LARGO_PANTALLA;
        else
          y=y-velocidad; 
    }

    // por ahora redeifnida solo para jugador enemigo usa la de nave analizar...
    @Override
    public RectF getRectangle() {
       return new RectF(x, y, x + imagen1.getWidth(), y + imagen1.getHeight());
    }

    public EnemigoTipo getTipo() {
        return tipo;
    }

    public void setTipo(EnemigoTipo tipo) {
        this.tipo = tipo;
    }

    public long getBonus() {
        return bonus;
    }

    public void setBonus(long bonus) {
        this.bonus = bonus;
    }

    public Bitmap getImagen1() {
        return imagen1;
    }

    public void setImagen1(Bitmap imagen) {
        this.imagen1 = imagen;
    }

    public Bitmap getImagen2() {
        return imagen2;
    }

    public void setImagen2(Bitmap imagen) {
        this.imagen2 = imagen;
    }
}
