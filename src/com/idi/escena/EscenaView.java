package com.idi.escena;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnKeyListener;
import com.idi.Enemigo.Enemigo;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import com.idi.Entity.Escena;
import com.idi.Formaciones.Formacion;
import com.idi.Thread.ThreadAtacantes;
import com.idi.Thread.ThreadDisparo;
import com.idi.Thread.ThreadEscenaView;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EscenaView extends SurfaceView implements SurfaceHolder.Callback, OnKeyListener {

    private ThreadEscenaView paintThread;
    private ThreadAtacantes threadAtacantes;
    // private ThreadAtacantes colisionThread;

    private Escena escena;
    private int origenX = 0;
    private int origenY = 0;

    private int jugadorSelecionado = 0;
    private int arrastro = 0;
    private int click = 0;

    private AssetManager asset;

    public EscenaView(Context context, AssetManager asset) {
        super(context);
        setFocusable(true); // make sure we get key events
        getHolder().addCallback(this);
        this.asset = asset;
        escena = new Escena(1, context, asset);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        paintThread = new ThreadEscenaView(getHolder(), this);
        paintThread.setRunning(true);
        paintThread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;
        paintThread.setRunning(false);
        while (retry) {
            try {
                paintThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_D) {
            escena.getJugador().moverDerecha();
        }

        if (keyCode == KeyEvent.KEYCODE_A) {
            escena.getJugador().MoverIzquierda();
        }

        if (keyCode == KeyEvent.KEYCODE_W) {
            escena.jugadorDispara();
        }
        return true;
    }

    public void update() {
        getEscena().avanzarDisparos();
        getEscena().colisiones();
        getEscena().moverFormacion();
    }

    public void render(Canvas canvas) {
        draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*int x = (int) event.getX();
         int y = (int) event.getY();

         switch (event.getAction()) {
         case MotionEvent.ACTION_DOWN:
         // hemos pulsado
         origenY = 0;
         origenX = 0;
         if (escena.getJugador().clicado(x, y)) {
         jugadorSelecionado = 1;
         origenX = x;
         origenY = y;
         System.out.println("clickado");
         }
         arrastro = 0;
         click = 1;
         break;
         case MotionEvent.ACTION_MOVE:
         if (jugadorSelecionado == 1) {
         escena.getJugador().move(x - origenX, y - origenY, getWidth(),
         getHeight());
         System.out.println("muevete conyo " + origenX + " " + origenY);

         origenY = y;
         origenX = x;
         }

         arrastro = 1;
         click = 0;
         // jugadorSelecionado = 0;
         break;
         case MotionEvent.ACTION_UP:
         if (click == 1 && arrastro == 0) {
         System.out.println("disparo");
         escena.jugadorDispara();
         }
         jugadorSelecionado = 0;
         arrastro = 0;
         click = 0;
         origenY = 0;
         origenX = 0;
         break;
         }
         */
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        pintaPaneles(canvas);
        pintaColisiones(canvas);
        pintaEnemigos(paint, canvas);
        pintaDisparos(paint, canvas);
        pintaJugador(paint, canvas);
    }
  
    private void pintaPaneles(Canvas canvas) {
       escena.getEstadistica().pintaPuntuacion(canvas);
       escena.getJugador().pintaVidas(canvas);
       escena.getEstadistica().pintaCronometro(canvas);
    }

    private void pintaColisiones(Canvas canvas) {
        for (int i = 0; i < escena.getColisiones().size(); ++i) {
            escena.getColisiones().get(i).pintaSpriteColision(canvas);
        }
    }

    private void pintaEnemigos(Paint paint, Canvas canvas) {

        Formacion formacion = escena.getFormacion();
        for (int i = 0; i < formacion.getEnemigos().size(); ++i) {
            Enemigo enemigo = formacion.getEnemigos().get(i);
            canvas.drawBitmap(enemigo.getImagenAnimacionBloque(), enemigo.getX(), enemigo.getY(), paint);
        }

        Iterator<Enemigo> it = formacion.getAtacantes().iterator();
        Enemigo atacante = null;
        while (it.hasNext()) {
            atacante = it.next();
            //para que no intente pintar ese segundo que estan empezando a volver a bajra y estan fuera
            if (atacante.getY() > 0 && atacante.getY() < Constantes.LARGO_PANTALLA ) {
                canvas.drawBitmap(atacante.getImagenAnimacionBloque(), atacante.getX(), atacante.getY(), paint);
            }
        }
    }

    private void pintaJugador(Paint paint, Canvas canvas) {
        canvas.drawBitmap(escena.getJugador().getImagen(), escena.getJugador().getX(), escena.getJugador().getY(), paint);
    }

    private void pintaDisparos(Paint paint, Canvas canvas) {
        for (int i = 0; i < escena.getDisparosJugador().size(); ++i) {
            Disparo disparo = escena.getDisparosJugador().get(i);
            canvas.drawBitmap(disparo.getImagen(), disparo.getX(), disparo.getY(), paint);
        }

        for (int i = 0; i < escena.getDisparosEnemigos().size(); ++i) {
            Disparo disparo = escena.getDisparosEnemigos().get(i);
            canvas.drawBitmap(disparo.getImagen(), disparo.getX(), disparo.getY(), paint);
        }
    }

    public ThreadEscenaView getPaintThread() {
        return paintThread;
    }

    public void setPaintThread(ThreadEscenaView paintThread) {
        this.paintThread = paintThread;
    }

    public Escena getEscena() {
        return escena;
    }

    public void setEscena(Escena escena) {
        this.escena = escena;
    }

    public int getOrigenX() {
        return origenX;
    }

    public void setOrigenX(int origenX) {
        this.origenX = origenX;
    }

    public int getOrigenY() {
        return origenY;
    }

    public void setOrigenY(int origenY) {
        this.origenY = origenY;
    }

    public void setAsset(AssetManager assetManager) {
        this.asset = assetManager;

    }

    public AssetManager getAsset() {
        return asset;
    }
    
}
