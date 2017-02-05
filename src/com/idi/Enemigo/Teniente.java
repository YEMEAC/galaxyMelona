package com.idi.Enemigo;


import com.idi.Entity.TexturasManager;

import com.idi.Entity.Constantes;
import java.util.ArrayList;

public class Teniente extends Enemigo {

    protected boolean heAlcanzadoLaAlturaDelJugador;

    public Teniente(float x, float y) {
        super(x, y, Constantes.NUMERO_VIDAS_TENIENTE,
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_TENIENTE_1),
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_TENIENTE_2));
        tipo = EnemigoTipo.TENIENTE;
        bonus = Constantes.BONUS_TENIENTE;

    }

    @Override
    public void movimientoAtacante(float targetX, float targetY, ArrayList<DisparoEnemigo> disparosEnemigos) {
        float tx = targetX - x;
        float ty = targetY - y;
        float dist = (float) Math.sqrt(tx * tx + ty * ty);

        float velX;
        float velY;
        float thrust = Constantes.VELOCIDAD_ENEMIGO_TENIENTE_1;
        float thrust2 = Constantes.VELOCIDAD_ENEMIGO_TENIENTE_2;

        if (dist > 80 && !heAlcanzadoLaAlturaDelJugador && Constantes.LARGO_PANTALLA-y>130) {
            velX = (tx / dist) * thrust2;
            velY = (ty / dist) * thrust2;
        } else {
            heAlcanzadoLaAlturaDelJugador=true;
            velX = 0;
            velY = thrust2;
        }

        if (y + velY > Constantes.LARGO_PANTALLA) {
            velY = 0;
            y = 0;
            velX = 0;
            heAlcanzadoLaAlturaDelJugador=false;
        }
        
        if(velY<0){
            velY*=-1;
            
        }

        x += velX;
        y += velY;
    }

    public DisparoEnemigo dispara() {
        float px = x + (imagen1.getWidth() / 2);
        float py = y + Constantes.VELOCIDAD_DISPARO_ENEMIGO;
        DisparoEnemigo d = new DisparoEnemigo(px, py, TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_DISPARO_ENEMIGO_TENIENTE));
        return d;
    }
    
    @Override
    public long getDelayDisparo() {
        return Constantes.DELAY_CAPTAR_TENIENTE;
    }
}
