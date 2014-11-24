package com.idi.Enemigo;

import android.graphics.Color;

import com.idi.Entity.Constantes;

public class Cabo extends Enemigo{

	public Cabo(int x, int y) {
		super(x, y, Constantes.NUMERO_VIDAS_CABO);
		tipo = EnemigoTipo.CABO;
		bonus = Constantes.BONUS_CABO;
		color = Color.MAGENTA;
	}
}
