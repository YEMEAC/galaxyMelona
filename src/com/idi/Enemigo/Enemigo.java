/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idi.Enemigo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import com.idi.Entity.Nave;
/**
 * 
 * @author ymeloa
 */
public abstract class Enemigo extends Nave {

	EnemigoTipo tipo;
	int bonus;

	// enemigas basicas
	public Enemigo(float x, float y, int v, Bitmap imagen) {
		super(x, y, v,imagen);
	}

	//@Override
	public Disparo dispara() {
		Disparo d = new DisparoEnemigo(x, y + Constantes.VELOCIDAD_DISPARO_ENEMIGO);
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
	public RectF getRectangle() {
		return new RectF(x, y, x + Constantes.TAMANO_LADO_NAVE_ENEMIGO, y + Constantes.TAMANO_LADO_NAVE_ENEMIGO);
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
