package com.idi.Enemigo;


import com.idi.Entity.TexturasManager;

import com.idi.Entity.Constantes;
import java.util.ArrayList;

public class Sargento extends Enemigo {

    int nsaltos;

    public Sargento(float x, float y) {
        super(x, y, Constantes.NUMERO_VIDAS_CABO,
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_SARGENTO_1),
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_SARGENTO_2));
        tipo = EnemigoTipo.SARGENTO;
        bonus = Constantes.BONUS_SARGENTO;
        nsaltos = 0;

    }

    public void movimientoAtacante(float targetX, float targetY, ArrayList<DisparoEnemigo> disparosEnemigos) {

        float velX;
        float velY;
        float thrust = Constantes.VELOCIDAD_ENEMIGO_SARGENTO_1;

        if (nsaltos == 0) {
            float tx = Constantes.ANCHO_PANTALLA - x;
            float ty = (Constantes.LARGO_PANTALLA / 3) - y;
            float dist = (float) Math.sqrt(tx * tx + ty * ty);
            velX = (tx / dist) * thrust + 4;
            velY = (ty / dist) * thrust;
            if (y >= 428) {
                ++nsaltos;
            }
        } else if (nsaltos == 1) {
            float tx = -60 - x;
            float ty = ((Constantes.LARGO_PANTALLA / 3) * 2) - y;
            float dist = (float) Math.sqrt(tx * tx + ty * ty);
            velX = (tx / dist) * thrust + 4;
            velY = (ty / dist) * thrust;
            if (y >= 428 * 2) {
                ++nsaltos;
            }
        } else {
            float tx = 730 - x;
            float ty = 428 * 3 - y;
            float dist = (float) Math.sqrt(tx * tx + ty * ty);
            velX = (tx / dist) * thrust + 4;
            velY = (ty / dist) * thrust;
        }

        if (y + velY > Constantes.LARGO_PANTALLA) {
            velY = 0;
            y = 0;
            velX = 0;
            x = 0;
            nsaltos = 0;
        }

        if (velY < 0) {
            velY *= -1;

        }

        x += velX;
        y += velY;

    }

    public DisparoEnemigo dispara() {
        float px = x + (imagen1.getWidth() / 2);
        float py = y + Constantes.VELOCIDAD_DISPARO_ENEMIGO;
        DisparoEnemigo d = new DisparoEnemigo(px, py, TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_DISPARO_ENEMIGO_SARGENTO));
        return d;
    }

    @Override
    public long getDelayDisparo() {
        return Constantes.DELAY_CAPTAR_SARGENTO;
    }
}
