package com.idi.Entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author ymeloa
 */
public class Escudo {

    int x;
    int y;
    Date ultimaAparicion;
    Date inicioActivacion;
    boolean cayendo;
    private Bitmap imagen;
    private Bitmap objetoImagen;

    Escudo() {
        ultimaAparicion = (Calendar.getInstance()).getTime();
        imagen = TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_JUGADOR_ESCUDO);
        objetoImagen = TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_JUGADOR_ESCUDO_OBJETO);
        reinicio();
    }

    private void reinicio() {
        Random rand = new Random();
        x = rand.nextInt((Constantes.ANCHO_PANTALLA - 0) + 1) + 0;   //aleatorio entre 0 y ancho de pantalla
        y = -imagen.getHeight();
        cayendo = false;
        inicioActivacion = null;
    }

    public void colisionConElJugador() {
        cayendo = false;
        inicioActivacion = (Calendar.getInstance()).getTime();
    }

    public void comoprobarSiIniciamosCaida() {
        Date now = (Calendar.getInstance()).getTime();
        if (now.getTime() - ultimaAparicion.getTime() > Constantes.TIEMPO_APARICION_ESCUDO) {
            cayendo = true;
            ultimaAparicion = (Calendar.getInstance()).getTime();
        }
    }

    public void comprabarSiSeAcabaElEscudo() {
        Date now = (Calendar.getInstance()).getTime();
        if (now.getTime() - inicioActivacion.getTime() > Constantes.TIEMPO_DURACION_ESCUDO) {
            reinicio();
        }
    }

    public void avanzarEscudo() {
        y += Constantes.VELOCIDAD_CAIDA_ESCUDO;
        if (y > Constantes.LARGO_PANTALLA) {
            reinicio();
        }
    }

    public void pintarObjeto(Canvas canvas, Paint paint) {
        canvas.drawBitmap(objetoImagen, x, y, paint);
    }

    public void pintarEscudoAlrededoJugador(Canvas canvas, Paint paint, float a, float b) {
        canvas.drawBitmap(imagen, a-10, b-7, paint);
    }

    public RectF getRectangle() {
        return new RectF(x, y, x + objetoImagen.getWidth(), y + objetoImagen.getHeight());
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

    public Date getUltimaAparicion() {
        return ultimaAparicion;
    }

    public void setUltimaAparicion(Date ultimaAparicion) {
        this.ultimaAparicion = ultimaAparicion;
    }

    public Date getInicioActivacion() {
        return inicioActivacion;
    }

    public void setInicioActivacion(Date inicio) {
        this.inicioActivacion = inicio;
    }

    public boolean isCayendo() {
        return cayendo;
    }

    public void setCayendo(boolean cayendo) {
        this.cayendo = cayendo;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Bitmap getObjetoImagen() {
        return objetoImagen;
    }

    public void setObjetoImagen(Bitmap objetoImagen) {
        this.objetoImagen = objetoImagen;
    }
}
