
package com.idi.Enemigo;

import android.graphics.Bitmap;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;

/**
 *
 * @author Jeison
 */
public class DisparoEnemigo extends Disparo {

    public DisparoEnemigo(float x, float y, Bitmap imagen) {
       super(x,y,imagen);
    }
    
    @Override
    public void mover() {
        this.y += Constantes.VELOCIDAD_DISPARO_ENEMIGO;
    }
       
}
