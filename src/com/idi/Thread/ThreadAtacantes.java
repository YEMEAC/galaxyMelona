package com.idi.Thread;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import com.idi.Entity.Constantes;
import com.idi.Entity.Escena;
import com.idi.Entity.TexturasManager;
import com.idi.escena.EscenaView;

public class ThreadAtacantes extends Thread {

    private boolean run;
    private SurfaceHolder sh;
    private EscenaView view;
    private AssetManager asset;

    public ThreadAtacantes(SurfaceHolder sh, EscenaView view) {
        this.sh = sh;
        this.view = view;
        run = false;
        this.asset = view.getAsset();
    }

    @Override
    public void run() {
        Canvas canvas;
        Paint paint = new Paint();
        while (run) {
            canvas = null;
            try {
                canvas = sh.lockCanvas(null);
                synchronized (sh) {
                   //view.getEscena().getFormacion().moverAtacantes(view.getEscena().getDisparosEnemigos());
                }
            } finally {
                if (canvas != null) {
                    sh.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setRunning(boolean run) {
        this.run = run;
    }
}
