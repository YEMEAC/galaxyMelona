/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idi.Enemigo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import com.idi.Entity.TexturasManager;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;

/**
 *
 * @author Jeison
 */
public class DisparoEnemigo extends Disparo {

    public DisparoEnemigo(float x, float y) {
       super(x,y,TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_DISPAROENEMIGO));
    }
    
}
