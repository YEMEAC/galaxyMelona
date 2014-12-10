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

    /*hay que tener en cuenta que la posicion que se guardo es el punto x,y
    lado izquierdo superior de la punta del cuadrado por lo tanto
    si vamos por  la derecha hay que sumar el witdh de la imagen porque el punto
    x,y sera el ultimo que saldra del marco en cambio por la izquierda (<0) sera
    el primero
    */
    public void moverArrastrando(int xx) {
        if (x + xx + imagen.getWidth() > Constantes.ANCHO_PANTALLA)
            x=0;
        else if (x + xx < 0)     
            x=Constantes.ANCHO_PANTALLA-imagen.getWidth();
        else
            x += xx;
    }
    
    /*el -10 + 10 es para que deje que la nave se acerque tocalmente al borde
    sino saltaria antes de tocarlo por la velocidad*/
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

    @Override
    public DisparoJugador dispara() {
        Date actual = (Calendar.getInstance()).getTime();
        if (ultimoDisparo == null || actual.getTime() - ultimoDisparo.getTime() >= Constantes.DELAY_CAPTAR_DISPARO_JUGADOR) {
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

    public void aumentarVidas(){
        if(vidas<4)
            ++vidas;
    }
    
    public void tocado() {
        if(vidas>=1)
            this.vidas -= 1;
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
