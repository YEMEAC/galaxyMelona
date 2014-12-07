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

public class Cabo extends Enemigo {

    protected Bitmap imagenSegundaria;

    public Cabo(float x, float y) {
        super(x, y, Constantes.NUMERO_VIDAS_CABO, 
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CABO_1),
                TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CABO_2));
        tipo = EnemigoTipo.CABO;
        bonus = Constantes.BONUS_CABO;

    }

        @Override
        public void movimientoAtacante(float Xjugador, float Yjugador, ArrayList<DisparoEnemigo> disparosEnemigos) {
        float Xatacante = x;
        float Yatacante = y;

        float tx = Xjugador - Xatacante;
        float ty = Yjugador - Yatacante;
        float dist = (float) Math.sqrt(tx * tx + ty * ty);

        float velx;
        float vely;
        float thrust = Constantes.VELOCIDAD_ENEMIGO_CABO_1;
        float thrust2 = Constantes.VELOCIDAD_ENEMIGO_CABO_2;

        if (dist > 400) {
            if (contadorDisparos == 0) {
                disparosEnemigos.add(this.dispara());
            }

            velx = Xatacante + (tx / dist) * thrust + 5;
            vely = Yatacante + (ty / dist) * thrust;
        } else if (dist > 200) {
            if (contadorDisparos == 1) {
                disparosEnemigos.add(this.dispara());
            }

            velx = Xatacante + (tx / dist) * thrust2;
            vely = Yatacante + (ty / dist) * thrust2;
        } else {
            velx = Xatacante;
            vely = Yatacante + thrust2;
        }
        
        if(vely>Constantes.LARGO_PANTALLA){
            vely=0;
            contadorDisparos=0; //afinar para dispare cuando haya bajado un poco
        }

        this.setX(velx);
        this.setY(vely);
    }
        
    public DisparoEnemigo dispara() {
        float px = x + (imagen1.getWidth() / 2);
        float py = y + Constantes.VELOCIDAD_DISPARO_ENEMIGO;
        DisparoEnemigo d = new DisparoEnemigo(px, py,TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_DISPARO_ENEMIGO_CABO));
        contadorDisparos++;
        return d;
    }

}
