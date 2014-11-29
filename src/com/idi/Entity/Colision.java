package com.idi.Entity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Colision {

    float x;
    float y;
    private Date ultimaActualizacionFotograma;
    private int fotograma = 0;
    ArrayList<Bitmap> animacion;
    
    public Colision(float a, float b, AssetManager asset, ArrayList<Bitmap> AnimacionColision) {
        x = a - 15;
        y = b - 15;
        animacion = AnimacionColision;
    }

    public RectF getRectangle() {
        return new RectF(x, y, x + Constantes.TAMANO_LADO_EXPLOCION_ENEMIGO, y
                + Constantes.TAMANO_LADO_EXPLOCION_ENEMIGO);
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

    public void pintaSpriteColision(Canvas canvas) {

        Date actual = (Calendar.getInstance()).getTime();
        if (animacion.size() > 0 && fotograma < 4) {
            canvas.drawBitmap(animacion.get(fotograma), x, y, new Paint());

            if (ultimaActualizacionFotograma != null && actual.getTime() - ultimaActualizacionFotograma.getTime() > Constantes.VELOCIDAD_FOTOGRAMA_EXPLOCION_ENEMIGO) {
                ++fotograma;
            }
            ultimaActualizacionFotograma = actual;

        }
    }

}
