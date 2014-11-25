package com.idi.escena;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.idi.Enemigo.Enemigo;
import com.idi.Entity.Constantes;
import com.idi.Entity.Escena;
import com.idi.Formaciones.Formacion;
import com.idi.Thread.ThreadColision;
import com.idi.Thread.ThreadDisparo;
import com.idi.Thread.ThreadEscenaView;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class EscenaView extends SurfaceView implements SurfaceHolder.Callback {

	private ThreadEscenaView paintThread;
	// private ThreadDisparo disparoThread;
	// private ThreadColision colisionThread;

	private Escena escena;
	private int origenX = 0;
	private int origenY = 0;

	private int jugadorSelecionado = 0;
	private int arrastro = 0;
	private int click = 0;

	private AssetManager asset;

	public EscenaView(Context context, AssetManager asset) {
		super(context);
		getHolder().addCallback(this);
		this.asset=asset;
		escena = new Escena(1, context,asset);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
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
				// disparoThread.join();
				// /colisionThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
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
				System.out.println("muevete coño " + origenX + " " + origenY);

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
				try {
					Thread.sleep(Constantes.DELAY_CAPTAR_DISPARO);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				escena.addDisparo(escena.getJugador().disparaJugador());
			}
			jugadorSelecionado = 0;
			arrastro = 0;
			click = 0;
			origenY = 0;
			origenX = 0;
			break;
		}

		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.GREEN);
		Paint paint = new Paint();
		pintaColisiones(paint, canvas);
		pingaEnemigos(paint, canvas);
		pintaDisparos(paint, canvas);
		pintaJugador(paint, canvas);
	}

	private void pintaColisiones(Paint paint, Canvas canvas) {
		escena.getColisiones();
		for (int i = 0; i < escena.getColisiones().size(); ++i) {
			paint.setColor(escena.getColisiones().get(i).getColor());
			canvas.drawRect(escena.getColisiones().get(i).getRectangle(), paint);
		}
	}

	private void pingaEnemigos(Paint paint, Canvas canvas) {
	
		Formacion formacion = escena.getFormacion();
		for (int i = 0; i < formacion.getEnemigos().size(); ++i) {
			Enemigo enemigo = formacion.getEnemigos().get(i);
			paint.setColor(enemigo.getColor()); 
			canvas.drawRect(enemigo.getRectangle(), paint);
		}
		
		for (int i = 0; i < formacion.getAtacantes().size(); ++i) {
			Enemigo atacante = formacion.getAtacantes().get(i);
			paint.setColor(atacante.getColor()); 
			canvas.drawRect(atacante.getRectangle(), paint);
		}
	}

	private void pintaJugador(Paint paint, Canvas canvas) {
		paint.setColor(Color.BLACK);
		
		canvas.drawBitmap(escena.getJugador().getSprite().getSprite(),5,5, paint);
	}

	private void pintaDisparos(Paint paint, Canvas canvas) {
		paint.setColor(Color.BLUE);
		for (int i = 0; i < escena.getDisparosJugador().size(); ++i) {
			canvas.drawRect(escena.getDisparosJugador().get(i).getRectangle(), paint);
		}
		
		paint.setColor(Color.BLUE);
		for (int i = 0; i < escena.getDisparosEnemigos().size(); ++i) {
			canvas.drawRect(escena.getDisparosEnemigos().get(i).getRectangle(), paint);
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
		this.asset=assetManager;
		
	}
}