/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idi.Entity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * 
 * @author ymeloa
 */
public class Jugador extends Nave {

	Sprite sprite; 
	
	public Jugador(int x, int y, int vidas, int velocidad, AssetManager asset) {
		super(x, y, vidas, velocidad);
		color = Color.WHITE;
		//Bitmap bMap = BitmapFactory.decodeFile("bitmap/ship.png");
		sprite = new Sprite(
				BitmapManager.getInstance().loadBitmap("bitmap/ship.png",asset)
				, new Rect(49, 140, 49+21, 140+27), 3);
	}

	public void move(int xx, int yy, int w, int h) {

		if (this.y + yy + Constantes.TAMANO_LADO_NAVE_JUGADOR <= h && this.y + yy > 0)
			this.y += yy;
		if (this.x + xx + Constantes.TAMANO_LADO_NAVE_JUGADOR <= w
				&& this.x + xx > 0)
			this.x += xx;

	}

	// el movimientod el disparo tiene que ser asia arriba
	
	public Disparo disparaJugador() {
		Disparo d = new Disparo(x, y - Constantes.VELOCIDAD_DISPARO_JUGADOR);
		return d;
	}

	@Override
	public void avanzar() {
		x -= 1 * velocidad;
	}

	@Override
	public void retroceder() {
		y += 1 * velocidad;
	}

	public boolean clicado(int x, int y) {
		if (y >= this.y && y <= this.y + 20 && x >= this.x && x <= this.x + 20)
			return true;

		return false;
	}

	// por ahora redeifnida solo para jugador enemigo usa la de nave analizar...
	@Override
	public Rect getRectangle() {
		return new Rect(x, y, x + Constantes.TAMANO_LADO_NAVE_JUGADOR, y
				+ Constantes.TAMANO_LADO_NAVE_JUGADOR);
	}

	public void tocado() {
		this.vidas-=1;
		
	}
	

	public Sprite getSprite() {
		return sprite;
	}

}
