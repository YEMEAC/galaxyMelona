/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idi.Entity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    protected Bitmap imagen;

    public Jugador(int x, int y, int vidas, Bitmap imagen) {
        super(x, y, vidas);
        this.imagen = imagen;
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

    @Override
    public DisparoJugador dispara() {
        Date actual = (Calendar.getInstance()).getTime();
        if (ultimoDisparo == null || actual.getTime() - ultimoDisparo.getTime() >= Constantes.DELAY_CAPTAR_DISPARO) {
            float px = (x + (imagen.getWidth() / 2));
            float py = (y - Constantes.DISPARO_JUGADOR_AUMENTO_Y_INICIAL);
            DisparoJugador d = new DisparoJugador(px, py);
            SoundManager.disparoJugador();
            ultimoDisparo = (Calendar.getInstance()).getTime();
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
        return y >= this.y && y <= this.y + 20 && x >= this.x && x <= this.x + 20;
    }

    // por ahora redeifnida solo para jugador enemigo usa la de nave analizar...
    @Override
    public RectF getRectangle() {
        return new RectF(x, y, x + imagen.getWidth(), y + imagen.getHeight());
    }

    public void tocado() {
        this.vidas -= 1;

    }

    public void moverDerecha() {
        if ((x + imagen.getWidth() - 10) + velocidad >= Constantes.ANCHO_PANTALLA) {
            x = 0;
        } else {
            x = x + velocidad;
        }

    }

    public void MoverIzquierda() {
        if (x - velocidad + 10 <= 0) {
            x = Constantes.ANCHO_PANTALLA - imagen.getWidth();
        } else {
            x = x - velocidad;
        }
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public void pintaVidas(Canvas canvas) {
        int separacion = 50;
        int xinicial = 20;
        int yinicial = 20;
        Bitmap imagen = TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_JUGADOR_VIDAS);
        for (int i = 0; i < getVidas(); ++i) {
            canvas.drawBitmap(imagen, xinicial + (i * imagen.getWidth()) + separacion, yinicial, new Paint());
        }
    }

}
