/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idi.Entity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 *
 * @author Jeison
 */
public class DisparoJugador extends Disparo {
    
    
    DisparoJugador(float x, float y){ 
        super(x,y,TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_DISPAROJUGADOR));
        
    }
    
}
