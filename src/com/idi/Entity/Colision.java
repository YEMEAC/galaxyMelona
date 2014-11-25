package com.idi.Entity;

import java.util.Calendar;
import java.util.Date;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;

public class Colision {

	int x;
	int y;
	int estado;
	Date duracion;
	int color;

	public Colision(int a, int b) {
		x = a;
		y = b;
		estado = 0;
		duracion = (Calendar.getInstance()).getTime();
		color = Color.BLACK;
	}

	public int avanzarColision() {
		Date actual = (Calendar.getInstance()).getTime();
		long mili = actual.getTime() - duracion.getTime();
		if (mili > 500 && mili <=1000)
			color = Color.WHITE;
		else if (mili > 1000 && mili <=2500)
			color = Color.YELLOW;
		else if (mili>2500)
			return 1;
		System.out.println("mili vale " +mili);
		return 0;
	}

	public Rect getRectangle() {
		return new Rect(x, y, x + Constantes.TAMANO_LADO_EXPLOCION_ENEMIGO, y
				+ Constantes.TAMANO_LADO_EXPLOCION_ENEMIGO);
	}

	public int getColor() {
		
		return color;
	}

}
