package com.idi.Entity;

import android.content.Context;
import android.widget.Chronometer;

public class Estadistica {
	

	int nivel;
	int puntuacion;
	private Chronometer cronometro;
	
	public Estadistica(int nivel2, Context context) {
		nivel=nivel2;
		puntuacion=0;
		cronometro=new Chronometer(context);
	}
	
	public void empezarCronometro(){
		cronometro.start();
	}
	
	public void pararCronometro(){
		cronometro.stop();
	}

	public void addBonus(int a){
		puntuacion+=a;
	}
}
