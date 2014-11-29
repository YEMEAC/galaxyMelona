package com.idi.Formaciones;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.Random;

import com.idi.Enemigo.Cabo;
import com.idi.Enemigo.Enemigo;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import com.idi.Entity.Jugador;
import com.idi.Entity.TexturasManager;

public class A extends Formacion {

    //esto abra que cambiar par que sea una rraylist de texturas enfuncion deltipo de nave
    public A(Jugador jugador) {
        super(jugador);
        int x = 24;
        int y = 20;
        
        //formacion cuadrada
       ArrayList<Integer> coordenadasIniciales = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) { 
            for (int j = 0; j < 10; j++) { 
                coordenadasIniciales.add(i);
                coordenadasIniciales.add(j);
            }
        }

        construirFormacion(coordenadasIniciales,4,7, 300 * 5, TexturasManager.getTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CABO));

        /*int n = Constantes.NUMERO_NAVES_NIVEL_UNO;
        int elementosPorFila = 8;
        int filas = n / elementosPorFila;

        for (int i = 0; i < filas; ++i) {
            for (int j = 0; j < elementosPorFila; ++j) {
                enemigos.add(new Cabo(x, y));
                x += Constantes.DISTANCIA_HORIZONTAL_ENEMIGOS;
            }
            y += Constantes.DISTANCIA_VERTICAL_ENEMIGOS;
            x = Constantes.DISTANCIA_HORIZONTAL_ENEMIGOS;
        }*/
    }

    public void moverBloque() {
        if (saltos == Constantes.SALTOS_BLOQUE_ENEMIGO) {
            desplazamiento *= -1;
            saltos = 0;
        }

        for (int i = 0; i < enemigos.size(); ++i) {
            enemigos.get(i).setX(enemigos.get(i).getX() + desplazamiento);
        }

        ++saltos;
    }

    public void moverAtacantes(ArrayList<Disparo> disparosEnemigos) {
        if (!atacantes.isEmpty()) {

            int aux = 1;
            if (saltos % 2 == 0) {
                aux *= -1;
            }

            float Xjugador = jugador.getX();
            float Yjugador = jugador.getY();
            for (int i = 0; i < atacantes.size(); ++i) {
                float Xatacante = atacantes.get(i).getX();
                float Yatacante = atacantes.get(i).getY();

                float tx=Xjugador-Xatacante;
                float ty=Yjugador-Yatacante;
                float dist = (float)Math.sqrt(tx*tx  + ty*ty);
            
                float velx=(tx/dist)*2;
                float vely=(ty/dist)*2;
                
                if(dist>1){
                    atacantes.get(i).setX(velx);
                    atacantes.get(i).setY(vely);
                }
                  
                
               /* Xatacante += aux * distanciaRespectoJugador / (Constantes.ATACANTES_DESPLAZAMIENTO_HORIZONTAL * 0.6);
                Yatacante += distanciaRespectoJugador / (Constantes.ATACANTES_DESPLAZAMIENTO_VERTICAL * 0.8);

                               //Xatacante=(int)((Xatacante - Xjugador)/0.5)*Constantes.ATACANTES_DESPLAZAMIENTO_HORIZONTAL;
                // Yatacante=aux*((int)((Yjugador-Yatacante)/0.5)*Constantes.ATACANTES_DESPLAZAMIENTO_VERTICAL);
                atacantes.get(i).setX(Xatacante);
                atacantes.get(i).setY(Yatacante);*/
                

                disparosEnemigos.add(atacantes.get(i).dispara());

            }

        } else {
            int min = 0;
            int max = enemigos.size() - 1;
            Random r = new Random();
            for (int i = 0; i < 3 && !enemigos.isEmpty(); ++i) {
                atacantes.add(enemigos.get(r.nextInt(max - min + 1) + min));
            }
            //quito los atacantes de la formacion normal para que no le afecte el desplazamiento general
            for (int i = 0; i < atacantes.size(); ++i) {
                enemigos.remove(atacantes.get(i));
            }
        }
    }

}
