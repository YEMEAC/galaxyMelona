
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

    @Override
    public void mover() {
        this.y -= Constantes.VELOCIDAD_DISPARO_JUGADOR;
    }
    
}
