/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idi.Entity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import com.idi.galaxiamelona.SoundManager;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ymeloa
 */
public class Jugador extends Nave {

    protected Date ultimoDisparo;

    public Jugador(int x, int y, int vidas, Bitmap imagen) {
        super(x, y, vidas, imagen);
    }

    public void move(int xx, int yy, int w, int h) {

        if (this.y + yy + Constantes.TAMANO_LADO_NAVE_JUGADOR <= h && this.y + yy > 0) {
            this.y += yy;
        }
        if (this.x + xx + Constantes.TAMANO_LADO_NAVE_JUGADOR <= w
                && this.x + xx > 0) {
            this.x += xx;
        }

    }

    public Disparo disparaJugador() {
        Date actual = (Calendar.getInstance()).getTime();
        if (ultimoDisparo == null || actual.getTime() - ultimoDisparo.getTime() >= Constantes.DELAY_CAPTAR_DISPARO) {
            Disparo d = new DisparoJugador(x, y - Constantes.VELOCIDAD_DISPARO_JUGADOR);
            SoundManager.disparoJugador();
            ultimoDisparo=(Calendar.getInstance()).getTime();
            return d;
        }
        return null;
    }

    @Override
    public void avanzar() {
        x -= 1 * velocidad;
    }

    @Override
    public void retroceder() {
        y += 1 * velocidad;
    }

    public boolean clicado(int x, int y) {
        if (y >= this.y && y <= this.y + 20 && x >= this.x && x <= this.x + 20) {
            return true;
        }

        return false;
    }

    // por ahora redeifnida solo para jugador enemigo usa la de nave analizar...
    @Override
    public RectF getRectangle() {
        return new RectF(x, y, x + Constantes.TAMANO_LADO_NAVE_JUGADOR, y
                + Constantes.TAMANO_LADO_NAVE_JUGADOR);
    }

    public void tocado() {
        this.vidas -= 1;

    }
}
