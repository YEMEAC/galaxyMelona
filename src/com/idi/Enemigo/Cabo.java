package com.idi.Enemigo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import com.idi.Entity.TexturasManager;

import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import java.util.ArrayList;
import java.util.Random;

public class Cabo extends Enemigo {

    int direccionMovimiento;

    public Cabo(float x, float y) {
        super(x, y, Constantes.NUMERO_VIDAS_CABO,
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CABO_1),
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CABO_2));
        tipo = EnemigoTipo.CABO;
        bonus = Constantes.BONUS_CABO;
        Random r = new Random();
        int min = 0;
        int max = 1;
        direccionMovimiento = r.nextInt(max - min + 1) + min;

    }

    @Override
    public void movimientoAtacante(float targetX, float targetY, ArrayList<DisparoEnemigo> disparosEnemigos) {

        float tx = targetX - x;
        float ty = targetY - y;
        float dist = (float) Math.sqrt(tx * tx + ty * ty);

        float velX;
        float velY;
        float thrust = Constantes.VELOCIDAD_ENEMIGO_CABO_1;
        float thrust2 = Constantes.VELOCIDAD_ENEMIGO_CABO_2;

        if (direccionMovimiento == 0) {
            tx = Constantes.ANCHO_PANTALLA - x;
            ty = (Constantes.LARGO_PANTALLA / 2) - y;
            dist = (float) Math.sqrt(tx * tx + ty * ty);
            velX = (tx / dist) * thrust + 5;
            velY = (ty / dist) * thrust;
            if (x >= Constantes.ANCHO_PANTALLA) {
                direccionMovimiento = 3;
            }
        } else if (direccionMovimiento == 1) {
            tx = -30 - x;
            ty = (Constantes.LARGO_PANTALLA / 2) - y;
            dist = (float) Math.sqrt(tx * tx + ty * ty);
            velX = (tx / dist) * thrust + 5;
            velY = (ty / dist) * thrust;
            if (x <= -30) {
                direccionMovimiento = 4;
            }
        } else {
            if (direccionMovimiento == 3) {
                tx = 0 - x;
            } else if (direccionMovimiento == 4) {
                tx = Constantes.ANCHO_PANTALLA - x;
            }

            ty = targetY - y;
            dist = (float) Math.sqrt(tx * tx + ty * ty);
            velX = (tx / dist) * thrust2;
            velY = (ty / dist) * thrust2;
        }

        if (y + velY > Constantes.LARGO_PANTALLA) {
            velY = 0;
            y = 0;
            velX = 0;
            Random r = new Random();
            int min = 0;
            int max = 1;
            direccionMovimiento = r.nextInt(max - min + 1) + min;
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
        DisparoEnemigo d = new DisparoEnemigo(px, py, TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_DISPARO_ENEMIGO_CABO));
        contadorDisparos++;
        return d;
    }

    @Override
    public long getDelayDisparo() {
        return Constantes.DELAY_CAPTAR_CABO;
    }

}
