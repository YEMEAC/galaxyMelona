package com.idi.Thread;

import com.idi.Entity.Escena;

public class ThreadColision extends Thread{

	private boolean run;
	Escena escena;

	public ThreadColision(Escena escena) {
		this.escena=escena;
		run = false;
	}

	@Override
	public void run() {
		while (run) {
			escena.colisiones();
		}
	}

	public void setRun(boolean b) {
		run = b;
	}
}
