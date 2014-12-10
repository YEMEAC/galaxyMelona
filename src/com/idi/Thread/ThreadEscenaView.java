package com.idi.Thread;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import com.idi.Entity.Constantes;
import com.idi.Entity.TexturasManager;
import com.idi.escena.EscenaView;

/**
 * @author Yeison Melo
 *
 */
public class ThreadEscenaView extends Thread {

    // fps deseados
    private final static int MAX_FPS = 48;
    // numero maximo de frames que nos podemos saltar
    private final static int MAX_FRAME_SKIPS = 5;
    // periodo/tiempo de 1 frame
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    // surface
    private SurfaceHolder surfaceHolder;
    // vista que captura inputs y dibuja en el surface
    private EscenaView escenaView;

    // flag de iniciado
    private boolean running;

    Canvas canvas;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void pausar() {

    }

    public ThreadEscenaView(SurfaceHolder surfaceHolder, EscenaView escenaView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.escenaView = escenaView;
    }

    @Override
    public void run() {

        long tiempoInicio;		// tiempo de inicio
        long tiempoDiferencia;		// tiempo que tarda un ciclo en ejecutare
        int tiempoSleep;		// ms para poner a domir (<0 estamos por detras)
        int framesSaltados;	// numero de frames que se estan saltandod 

        tiempoSleep = 0;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        Bitmap a = TexturasManager.loadBitmap(Constantes.PATH_FONDO);
        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                if (canvas != null && a != null) {
                    canvas.drawBitmap(a, 0, 0, paint);
                }

                synchronized (surfaceHolder) {
                    tiempoInicio = System.currentTimeMillis();
                    framesSaltados = 0;	// resetear frames saltados
                    // actualizar estado del juego
                    if (canvas != null && escenaView != null) //por alguna razon cuando se va al bacground  y vuevel el canvas se queda a null
                    {
                        this.escenaView.update();
                    }
                    // dibujar el estado en el canvas
                    if (canvas != null && escenaView != null) {
                        this.escenaView.render(canvas);
                    }
                    // calculate how long did the cycle take
                    tiempoDiferencia = System.currentTimeMillis() - tiempoInicio;
                    // calculate sleep time
                    tiempoSleep = (int) (FRAME_PERIOD - tiempoDiferencia);

                    if (tiempoSleep > 0) {
                        // si tiempoSleep > 0  bien
                        try {
                            // mandamos el thread a dormir por un perioro cordo
                            Thread.sleep(tiempoSleep);
                        } catch (InterruptedException e) {
                        }
                    }

                    while (tiempoSleep < 0 && framesSaltados < MAX_FRAME_SKIPS) {
                        // necesitamos capturarlo
                        if (canvas != null && escenaView != null) {
                            this.escenaView.update(); // actualiar sin dibujar
                        }
                        tiempoSleep += FRAME_PERIOD;	// añadir periodo de frame para ver en el siguiente
                        framesSaltados++;
                    }
                }
            } finally {
                //en caso de excepcion no dejamos al surface en un estado inconsistenteS
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }	// fin
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

}
