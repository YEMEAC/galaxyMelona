package com.idi.Enemigo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import com.idi.Entity.TexturasManager;

import com.idi.Entity.Constantes;

public class Cabo extends Enemigo{

	public Cabo(float x, float y) {
		super(x, y, Constantes.NUMERO_VIDAS_CABO,TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CABO));
		tipo = EnemigoTipo.CABO;
		bonus = Constantes.BONUS_CABO;
            
	}
}
