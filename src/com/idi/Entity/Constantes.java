package com.idi.Entity;

public final class Constantes {

    public static final int TAMANO_LADO_NAVE_JUGADOR = 20;
    public static final int TAMANO_LADO_NAVE_ENEMIGO = 10;

    public static final int POSICION_INICIAL_JUGADOR_X = 140;
    public static final int POSICION_INICIAL_JUGADOR_Y = 370;
    public static final int VIDAS_JUGADOR = 4;
    public static final int VELOCIDA_INICIAL_JUGADOR = 1;
    public static final int VELOCIDAD_DISPARO_JUGADOR = 10;

    public static final int BONUS_CABO = 100;
    public static final long DELAY_CAPTAR_DISPARO = 230;
    //SI QUIERO PONER MENOS DELAY TENGO QUE ACORTAR EL SONIDO DE DISPARO 
    public static final int NUMERO_NAVES_NIVEL_UNO = 24;
    public static final int NUMERO_VIDAS_CABO = 1;

    public static final int ATACANTES_DESPLAZAMIENTO_VERTICAL = 2;
    public static final int ATACANTES_DESPLAZAMIENTO_HORIZONTAL = 2;
    public static final int VELOCIDAD_DISPARO_ENEMIGO = 3;
    public static final int TAMANO_LADO_EXPLOCION_ENEMIGO = 10;

    public static final int SALTOS_BLOQUE_ENEMIGO = 4;
    public static final int LOGINTUD_SALTO_BLOQUE_ENEMIGO = -20;
    public static final int DISTANCIA_HORIZONTAL_ENEMIGOS = 25;
    public static final int DISTANCIA_VERTICAL_ENEMIGOS = 25;
    public static final long DELAY_DESPLAZAMIENTO_MOVIMIENTO_ATACANTES = 1500;
    public static final long DELAY_DESPLAZAMIENTO_MOVIMIENTO_FORMACION = 1000;

    public static final int TAMANO_EXPLOCION_ENEMIGO = 1;
    public static long VELOCIDAD_FOTOGRAMA_EXPLOCION_ENEMIGO = 10;

    public static final String PATH_SPRITE_NAVES_DISPAROS = "imagenes/ship.png";
    public static final String PATH_ANIMACION_EXPLOCION_ENEMIO_1 = "imagenes/explosion.png";
    public static final String PATH_ANIMACION_EXPLOCION_ENEMIO_2 = "imagenes/explosion2.png";
    public static String PATH_FONDO = "imagenes/fondo.png";
    public static final String PATH_SONIDO_EXPLOCION_1 = "sonidos/explosion.wav";
    public static String PATH_SONIDO_DISPARO_JUGADOR = "sonidos/shoot.wav";
    public static String PATH_SONIDO_DISPARO_ENEMIGO = "sonidos/laserJugador.wav";
    public static String PATH_MUSICA_FONDO_ESCENA = "musica/background.mp3";
    public static int TAMAÑO_BUFFER_SONIDO_DISPAROS = 2;
    public static int TAMAÑO_BUFFER_SONIDO_EXPLOCINES = 2;
    public static int TAMAÑO_BUFFER_SONIDO_ENEMIGO = 3;

    //STRINGS PARA LOCALIZAR UNA TEXTURA EN EL TEXTURASMANAGER
    public static String TEXTURAS_TEXTURA_JUGADOR = "jugador";
    public static String TEXTURAS_TEXTURA_DISPAROJUGADOR = "disparoJugador";
    public static String TEXTURAS_TEXTURA_DISPAROENEMIGO = "disparoEnemigo";
    public static String TEXTURAS_TEXTURA_ENEMIGO_CABO = "cabo";
    
    public static int LARGO_PANTALLA=-1;
    public static int ANCHO_PANTALLA=-1;

}
