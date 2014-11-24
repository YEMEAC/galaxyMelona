/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idi.Enemigo;

import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import com.idi.Entity.Nave;

import android.graphics.Color;
import android.graphics.Rect;
/**
 * 
 * @author ymeloa
 */
public abstract class Enemigo extends Nave {

	EnemigoTipo tipo;
	int bonus;

	// enemigas basicas
	public Enemigo(int x, int y, int v) {
		super(x, y, v);
	}

	// el movimientod el disparo tiene que ser asia abajo
	//@Override
	public Disparo dispara() {
		Disparo d = new Disparo(x, y + Constantes.VELOCIDAD_DISPARO_ENEMIGO);
		return d;
	}

	@Override
	public void avanzar() {
		y += 1 * velocidad;
	}

	@Override
	public void retroceder() {
		y -= 1 * velocidad;
	}

	// por ahora redeifnida solo para jugador enemigo usa la de nave analizar...
	@Override
	public Rect getRectangle() {
		return new Rect(x, y, x + Constantes.TAMANO_LADO_NAVE_ENEMIGO, y + Constantes.TAMANO_LADO_NAVE_ENEMIGO);
	}

	public EnemigoTipo getTipo() {
		return tipo;
	}

	public void setTipo(EnemigoTipo tipo) {
		this.tipo = tipo;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

}
