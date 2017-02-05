package com.idi.Formaciones;

import com.idi.Enemigo.Cabo;
import com.idi.Enemigo.Coronel;
import com.idi.Enemigo.DisparoEnemigo;
import com.idi.Enemigo.Sargento;
import com.idi.Enemigo.Teniente;
import com.idi.Entity.Constantes;
import com.idi.Entity.Jugador;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

public class A extends Formacion {

    //esto abra que cambiar par que sea una rraylist de texturas enfuncion deltipo de nave
    public A(Jugador jugador, int a) {
        super(jugador);
        int x = 24;
        int y = 20;
        int filas = 4;
        int columas = 7;
        //formacion cuadrada

        if (a == 0) {
            construirFormacion(cuadrada(), filas, columas);
        } else if (a == 1) {
            construirFormacion(estrella(), filas, columas);
        } else if (a == 2) {
            construirFormacion(rombo(), filas, columas);
        } else if (a == 3) {
            construirFormacion(diamante(), filas, columas);
        }else if (a==4){
        	construirFormacion(triangulo(), filas, columas);
        } else {
            construirFormacion(aleatorio(), filas, columas);
        }
        OrdenAtacantes();

    }
    
    private ArrayList<Integer> triangulo() {
        ArrayList<Integer> coordenadasIniciales = new ArrayList<Integer>();
        int n=8;
        for(int i=0;i<n;i++)
        {
        	
        		for(int k=0;k<2*i-1;k++)
        			{
        			 coordenadasIniciales.add(i);
                     coordenadasIniciales.add(k);
                                   // star
        				}

        	}
        return coordenadasIniciales;
    }

    private ArrayList<Integer> aleatorio() {
        ArrayList<Integer> coordenadasIniciales = new ArrayList<Integer>();
        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < 9; j += 9) {
                coordenadasIniciales.add(i);
                coordenadasIniciales.add(j);
            }
        }

        for (int i = 0; i < 10; i += 9) {
            coordenadasIniciales.add(i);
            coordenadasIniciales.add(i);
            coordenadasIniciales.add(i);
            coordenadasIniciales.add(10 - 1);
        }

        return coordenadasIniciales;
    }

    private ArrayList<Integer> diamante() {
        ArrayList<Integer> coordenadasIniciales = new ArrayList<Integer>();
        int n=8;
        for(int i=0;i<n;i++)
        {
           for(int j=0;j<=i;j++)
           {
        	   coordenadasIniciales.add(i);
               coordenadasIniciales.add(j);
           }
        }
        return coordenadasIniciales;
    }

    private ArrayList<Integer> estrella() {
        ArrayList<Integer> coordenadasIniciales = new ArrayList<Integer>();
        for (int i = 0; i < 10; i += 9) {
            coordenadasIniciales.add(i);
            coordenadasIniciales.add(i);
            coordenadasIniciales.add(i);
            coordenadasIniciales.add(10 - 1);
        }

        for (int i = 0; i < 10; i++) {
            coordenadasIniciales.add(i);
            coordenadasIniciales.add(4);
            coordenadasIniciales.add(4);
            coordenadasIniciales.add(i);
        }
        return coordenadasIniciales;
    }

    

    private ArrayList<Integer> cuadrada() {
        int filas = 4;
        int columas = 7;
        ArrayList<Integer> coordenadasIniciales = new ArrayList<Integer>();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columas; j++) {
                coordenadasIniciales.add(i);
                coordenadasIniciales.add(j);
            }
        }
        return coordenadasIniciales;
    }

    private ArrayList<Integer> rombo() {
        ArrayList<Integer> coordenadasIniciales = new ArrayList<Integer>();
        int n = 6;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                coordenadasIniciales.add(j);
                coordenadasIniciales.add(i);
            }
        }

        return coordenadasIniciales;
    }

    //obtener un orden aleatoria para decir los atacantes, tantos numeros/posiciones como atacantes enemigos
    //disponibles para ser atacantes queden
    private void OrdenAtacantes() {
        ordenAtacantes = new ArrayList<Integer>();
        for (int i = 0; i < getEnemigos().size(); ++i) {
            ordenAtacantes.add(i);
        }

        Collections.shuffle(ordenAtacantes);
    }

    @Override
    void construirFormacion(ArrayList<Integer> coordenadasIniciales, int numeroFilas, int numeroColumnas) {

        int escala = 1;
        int topBuffer = (int) ((Constantes.LARGO_PANTALLA / 15) * escala);
        int size = (int) ((Constantes.LARGO_PANTALLA / 30) * escala) + 10;
        int margen = (int) ((size / 5) * escala);

        int fila;
        int columna;
        int count = 0;

        /*  while (count < 2) {
         fila = coordenadasIniciales.get(count);
         columna = coordenadasIniciales.get(count + 1);*/
        while (count < coordenadasIniciales.size()) {
            fila = coordenadasIniciales.get(count);
            columna = coordenadasIniciales.get(count + 1);

            if (fila <3) {
                if (columna % 2 == 0) {
                    enemigos.add(new Teniente((columna * size) + (columna * margen), (fila * size) + (fila * margen) + topBuffer));
                } else {
                    enemigos.add(new Coronel((columna * size) + (columna * margen), (fila * size) + (fila * margen) + topBuffer));
                }
                count += 2;
            } else if (fila <6) {
                enemigos.add(new Sargento((columna * size) + (columna * margen), (fila * size) + (fila * margen) + topBuffer));
                count += 2;
            } else {
                enemigos.add(new Cabo((columna * size) + (columna * margen), (fila * size) + (fila * margen) + topBuffer));
                count += 2;
            }
        }
        // }
    }

    public void moverBloque() {
        if (saltos == Constantes.SALTOS_BLOQUE_ENEMIGO) {
            desplazamiento *= -1;
            saltos = 0;
        } else {
            ++saltos;
        }

        for (int i = 0; i < enemigos.size(); ++i) {
            enemigos.get(i).setX(enemigos.get(i).getX() + desplazamiento);
        }

    }

    public void moverAtacantes(ArrayList<DisparoEnemigo> disparosEnemigos) {
        if (!atacantes.isEmpty()) {
            float Xjugador = jugador.getX();
            float Yjugador = jugador.getY();
            Date actual = (Calendar.getInstance()).getTime();
            for (int i = 0; i < atacantes.size(); ++i) {
                atacantes.get(i).movimientoAtacante(Xjugador, Yjugador, disparosEnemigos);

                if (atacantes.get(i).getUltimoDisparo() == null
                        || actual.getTime() - atacantes.get(i).getUltimoDisparo().getTime() > atacantes.get(i).getDelayDisparo()) {
                    disparosEnemigos.add((DisparoEnemigo) atacantes.get(i).dispara());
                    atacantes.get(i).setUltimoDisparo(actual);
                }

            }

        } else {
            //refres de los atacantes disponibles para no salir del rango por enemigos eliminados
            OrdenAtacantes();
            Iterator<Integer> it = ordenAtacantes.iterator();
            int count = 0;
            int aux;
            while (it.hasNext() && count < 3) {
                aux = it.next();
                atacantes.add(enemigos.get(aux));
                it.remove();
                ++count;
            }

            for (int i = 0; i < atacantes.size(); ++i) {
                enemigos.remove(atacantes.get(i));
            }
        }
    }

}
