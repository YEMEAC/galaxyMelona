package com.idi.Enemigo;

import com.idi.Entity.TexturasManager;

import com.idi.Entity.Constantes;
import java.util.ArrayList;

public class Coronel extends Enemigo {


    public Coronel(float x, float y) {
        super(x, y, Constantes.NUMERO_VIDAS_CORONEL,
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CORONEL_1),
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CORONEL_2));
        tipo = EnemigoTipo.CORONEL;
        bonus = Constantes.BONUS_CORONEL;

    }

    @Override
    public void movimientoAtacante(float targetX, float targetY, ArrayList<DisparoEnemigo> disparosEnemigos) {
        float tx = targetX - x;
        float ty = targetY - y;
        float dist = (float) Math.sqrt(tx * tx + ty * ty);

        float velX;
        float velY;
        float thrust = Constantes.VELOCIDAD_ENEMIGO_CORONEL_1;
        float thrust2 = Constantes.VELOCIDAD_ENEMIGO_CORONEL_2;

        if (dist > 200) {
            tx = targetX - x;
            ty = targetY - y;
            dist = (float) Math.sqrt(tx * tx + ty * ty);
            velX = (tx / dist) * thrust;
            velY = (ty / dist) * thrust;

        } else {
            tx = (targetX + 100) - x;
            ty = (targetY - 100) - y;
            dist = (float) Math.sqrt(tx * tx + ty * ty);
            velX = (tx / dist) * thrust;
            velY = (ty / dist) * thrust;
        }
        if (y + velY > Constantes.LARGO_PANTALLA) {
            velY = 0;
            y = 0;
            velX = 0;
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
        DisparoEnemigo d = new DisparoEnemigo(px, py, TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_DISPARO_ENEMIGO_CORONEL));
        return d;
    }

    @Override
    public long getDelayDisparo() {
        return Constantes.DELAY_CAPTAR_CORONEL;
    }
}
