package com.idi.Thread;

import com.idi.escena.EscenaView;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ThreadEscenaView extends Thread {

	private SurfaceHolder sh;
	private EscenaView view;
	private boolean run;

	public ThreadEscenaView(SurfaceHolder sh, EscenaView view) {
		this.sh = sh;
		this.view = view;
		run = false;
	}

	public void setRunning(boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		Canvas canvas;
		while (run) {
			canvas = null;
			try {
				canvas = sh.lockCanvas(null);
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