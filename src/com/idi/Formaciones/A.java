package com.idi.Formaciones;

import java.util.ArrayList;
import java.util.Random;

import com.idi.Enemigo.Cabo;
import com.idi.Enemigo.Enemigo;
import com.idi.Entity.Constantes;
import com.idi.Entity.Disparo;
import com.idi.Entity.Jugador;

public class A extends Formacion {

	public A(Jugador jugador) {
		super(jugador);
		int x = 24;
		int y = 20;

		int n = Constantes.NUMERO_NAVES_NIVEL_UNO;
		int elementosPorFila = 8;
		int filas = n / elementosPorFila;

		for (int i = 0; i < filas; ++i) {
			for (int j = 0; j < elementosPorFila; ++j) {
				enemigos.add(new Cabo(x, y));
				x += Constantes.DISTANCIA_HORIZONTAL_ENEMIGOS;
			}
			y += Constantes.DISTANCIA_VERTICAL_ENEMIGOS;
			x = Constantes.DISTANCIA_HORIZONTAL_ENEMIGOS;
		}
	}

	public void moverBloque() {
		if (saltos == Constantes.SALTOS_BLOQUE_ENEMIGO) {
			desplazamiento *= -1;
			saltos = 0;
		}

		for (int i = 0; i < enemigos.size(); ++i) 
				enemigos.get(i).setX(enemigos.get(i).getX() + desplazamiento);

		++saltos;
	}

	public void moverAtacantes(ArrayList<Disparo> disparosEnemigos) {
		if (!atacantes.isEmpty()) {

			int aux = 1;
			if (saltos % 2 == 0)
				aux *= -1;
			
			int Xjugador = jugador.getX();
			int Yjugador = jugador.getY();
			for (int i = 0; i < atacantes.size(); ++i) {
				int Xatacante=atacantes.get(i).getX();
				int Yatacante=atacantes.get(i).getY();
				
				int distanciaRespectoJugador=(int) Math.ceil(Math.sqrt((Xjugador-Xatacante)*(Xjugador-Xatacante) + (Yjugador-Yatacante)*(Yjugador-Yatacante)));
				
				Xatacante+=aux*distanciaRespectoJugador/(Constantes.ATACANTES_DESPLAZAMIENTO_HORIZONTAL*0.6);
				Yatacante+=distanciaRespectoJugador/(Constantes.ATACANTES_DESPLAZAMIENTO_VERTICAL*0.8);
				
				atacantes.get(i).setX(Xatacante);
				atacantes.get(i).setY(Yatacante);
				
				disparosEnemigos.add(atacantes.get(i).dispara());
				
			}

		} else {
			int min = 0;
			int max = enemigos.size() - 1;
			Random r = new Random();
			for (int i = 0; i < 3 && !enemigos.isEmpty(); ++i)
				atacantes.add(enemigos.get(r.nextInt(max - min + 1) + min));
			//quito los atacantes de la formacion normal para que no le afecte el desplazamiento general
			for (int i = 0; i < atacantes.size(); ++i)
				enemigos.remove(atacantes.get(i));
		}
	}

}
