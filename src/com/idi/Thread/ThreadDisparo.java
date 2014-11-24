package com.idi.Thread;

import com.idi.Entity.Escena;

public class ThreadDisparo extends Thread {

	private boolean run;
	Escena escena;
	public ThreadDisparo(Escena escena) {
		this.escena=escena;
		run = false;
	}

	@Override
	public void run() {
		while (run) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			escena.avanzarDisparosJugador();
		}
	}

	public void setRun(boolean b) {
		run = b;
	}
}
