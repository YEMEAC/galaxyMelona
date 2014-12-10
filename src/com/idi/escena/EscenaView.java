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
import com.idi.Thread.ThreadEscenaView;
import com.idi.galaxiamelona.EscenaActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EscenaView extends SurfaceView implements SurfaceHolder.Callback {

    private EscenaActivity parentView;
    private ThreadEscenaView paintThread;

    private Escena escena;
    private int origenX = 0;
    private int origenY = 0;

    private int jugadorSelecionado = 0;
    private int arrastro = 0;
    private int click = 0;
    private boolean gameOver;
    private Date inicioGameOver;

    private AssetManager asset;
    private static int pausar;

    public EscenaView(Context context, AssetManager asset, EscenaActivity a) {
        super(context);
        parentView = a;
        setFocusable(true); // make sure we get key events
        getHolder().addCallback(this);
        this.asset = asset;
        escena = new Escena(1, context, asset, this);
        pausar = 0;

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

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        if (!gameOver) {
            pintaPaneles(canvas);
            pintaColisiones(canvas);
            pintaEnemigos(paint, canvas);
            pintaDisparos(paint, canvas);
            pintaJugador(paint, canvas);
            accionesPausar();
            escena.comprobarFinDeJugador();
        } else {
            pintaFinPartida();
        }
    }

    private void accionesPausar() {
        if (escena.getTeclas().contains(KeyEvent.KEYCODE_P)) {
            if (pausar == 2) {
                pausar = 0;
            } else if (pausar == 0) { //presionado estado 1
                pausar = 1; //priosionado y soltamos la tecla
            }
        } else if (pausar == 1) { //volvemos a presionar para reiniciar
            pausar = 2;
        }
    }

    public void update() {
        if (!gameOver && pausar == 0) {
            getEscena().movimientoJugador();
            getEscena().avanzarDisparos();
            getEscena().colisiones();
            getEscena().moverFormacion();

            if (!this.isFocusable()) {
                setFocusable(true);
            }
        }
    }

    public void render(Canvas canvas) {
        draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (pausar == 0) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // hemos pulsado
                    origenX = 0;
                    if (escena.getJugador().getRectangle().contains(x, y)) {
                        jugadorSelecionado = 1;
                        origenX = x;
                        System.out.println("clickado");
                    }
                    arrastro = 0;
                    click = 1;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (jugadorSelecionado == 1) {
                        escena.getJugador().moverArrastrando(x - origenX);
                        origenX = x;
                    }
                    arrastro = 1;
                    click = 0;
                    break;
                case MotionEvent.ACTION_UP:
                    if (click == 1 && arrastro == 0) {
                        escena.jugadorDispara();
                    }
                    jugadorSelecionado = 0;
                    arrastro = 0;
                    click = 0;
                    origenX = 0;
                    break;
            }
        }

        return true;
    }

    private void pintaPaneles(Canvas canvas) {
        escena.getEstadistica().pintaPuntuacion(canvas);
        escena.getJugador().pintaVidas(canvas);
        escena.getEstadistica().pintaCronometro(canvas);
    }

    private void pintaFinPartida() {
        Date now = (Calendar.getInstance()).getTime();
        if (now.getTime() - inicioGameOver.getTime() > Constantes.TIEMPO_PANTALLA_FIN_JUEGO) {
            escena.getSoundManager().finDeJuego();
            this.parentView.finalizarEscena();
        } else {
            if (escena.getEstadistica().isNuevoRecord()) {
                recordsSuperado();
            } else {
                gameOver();
            }
        }
    }

    private void recordsSuperado() {
        Paint paint = new Paint();
        paint.setTextSize(40);
        long c;
        int count = 1;
        int separacion = 40;

        paint.setColor(Color.BLUE);
        String a = "Nuevo Record! " + escena.getEstadistica().getPuntuacion() + "\n";
        this.paintThread.getCanvas().drawText(a, Constantes.ANCHO_PANTALLA / 3, (Constantes.LARGO_PANTALLA / 3), paint);
        paint.setColor(Color.RED);
        //para obtener la lista en orden decreciente
        ArrayList<Long> auxHistorico = new ArrayList<Long>();
        auxHistorico.addAll(escena.getEstadistica().getHistoricoPuntuacionesLong());
        Collections.reverse(auxHistorico);
        Iterator<Long> it = auxHistorico.iterator();

        while (it.hasNext()) {
            c = it.next();
            if (c == escena.getEstadistica().getPuntuacion()) {
                paint.setColor(Color.BLUE);
                a = count + " " + c;
                this.paintThread.getCanvas().drawText(a, (float) (Constantes.ANCHO_PANTALLA / 2), (Constantes.LARGO_PANTALLA / 3) + count * separacion, paint);
                paint.setColor(Color.RED);
            } else {
                a = count + " " + c;
                this.paintThread.getCanvas().drawText(a, (float) (Constantes.ANCHO_PANTALLA / 2), (Constantes.LARGO_PANTALLA / 3) + count * separacion, paint);
            }
            ++count;
        }

    }

    public void gameOver() {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        String a = "GAME OVER \n";

        paint.setTextSize(50);
        this.paintThread.getCanvas().drawText(a, Constantes.ANCHO_PANTALLA / 2 - 100, Constantes.LARGO_PANTALLA / 2, paint);
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
            if (atacante.getY() > 0 && atacante.getY() < Constantes.LARGO_PANTALLA) {
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

    public EscenaActivity getParentView() {
        return parentView;
    }

    public void setParentView(EscenaActivity parentView) {
        this.parentView = parentView;
    }

    public int getJugadorSelecionado() {
        return jugadorSelecionado;
    }

    public void setJugadorSelecionado(int jugadorSelecionado) {
        this.jugadorSelecionado = jugadorSelecionado;
    }

    public int getArrastro() {
        return arrastro;
    }

    public void setArrastro(int arrastro) {
        this.arrastro = arrastro;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Date getInicioGameOver() {
        return inicioGameOver;
    }

    public void setInicioGameOver(Date inicioGameOver) {
        this.inicioGameOver = inicioGameOver;
    }

    public int getPausar() {
        return pausar;
    }

    public void setPausar(int pausar) {
        this.pausar = pausar;
    }

    public static void setPausarStatic(int i) {
        pausar = i;
    }

}
