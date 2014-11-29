package com.idi.Thread;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import com.idi.Entity.TexturasManager;
import com.idi.Entity.Constantes;
import com.idi.escena.EscenaView;

public class ThreadEscenaView extends Thread {

	private SurfaceHolder sh;
	private EscenaView view;
	private boolean run;
        private AssetManager asset;

	public ThreadEscenaView(SurfaceHolder sh, EscenaView view) {
		this.sh = sh;
		this.view = view;
		run = false;
                this.asset=view.getAsset();
	}

	public void setRunning(boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		Canvas canvas;
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
               
               Bitmap a = TexturasManager.loadBitmap(Constantes.PATH_FONDO);
		  
		while (run) {
			canvas = null;
			try {
				canvas = sh.lockCanvas(null);
                                canvas.drawBitmap(a, 0, 0, paint);	
				synchronized (sh) {
					view.getEscena().avanzarDisparos();
					view.getEscena().colisiones();
					view.getEscena().moverFormacion();
					view.draw(canvas);
				}
			} finally {
				if (canvas != null)
					sh.unlockCanvasAndPost(canvas);
			}
		}
	}
}