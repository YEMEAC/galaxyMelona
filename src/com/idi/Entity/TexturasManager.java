package com.idi.Entity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.Rect;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/*
 Esta clase carga todas las texturas solo una vez para mejorar eficiencia de velocidad y espacio
 apartir de aqui todos los objetos recogen su textura de aqui, tiene muchos metodos estaticos
 asi que antes de nada hay que asegurarse de que la clase estiene una instancia, para que las propiedad
 que tambien son estaticas y que por lo tanto seran compatidas por todas las instancias y llamadas
 ya que en el constructor se definen estas propiedades, y que todas las texturas que se consultaran estan cargadas
 */
public class TexturasManager {

    private static TexturasManager bitmapManager;
    private static HashMap<String, Bitmap> texturas = new HashMap<String, Bitmap>();

    private static ArrayList<Bitmap> AnimacionColision1; //así no tengo que cargar la animacion en cad colision OPTIMIZACION
    private static ArrayList<Bitmap> AnimacionColision2;
    private static AssetManager am;

    public TexturasManager(AssetManager a) {
        am = a;
        Bitmap aux;

        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVES_DISPAROS, new Rect(49, 140, 49 + 21, 140 + 27), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_JUGADOR, aux);

        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVES_DISPAROS, new Rect(52, 203, 52 + 15, 203 + 19), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CABO, aux);

        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVES_DISPAROS, new Rect(217, 113, 217 + 11, 113 + 11), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_DISPAROJUGADOR, aux);

        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVES_DISPAROS, new Rect(217, 113, 217 + 11, 113 + 11), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_DISPAROENEMIGO, aux);

        AnimacionColision1 = crearAnimacionExplocionEnemigo(Constantes.PATH_ANIMACION_EXPLOCION_ENEMIO_1);
        AnimacionColision2 = crearAnimacionExplocionEnemigo(Constantes.PATH_ANIMACION_EXPLOCION_ENEMIO_2);

    }

    public static Bitmap getTextura(String s) {
        return texturas.get(s);
    }

    public void setAssetManager(AssetManager a) {
        am = a;
    }

    void anadirTextura(String jugador, Bitmap bitmap) {
        texturas.put(jugador, bitmap);
    }

    public static Bitmap getBitmapRect(String path, Rect place) {
        Bitmap bmp = loadBitmap(path);
        return Bitmap.createBitmap(bmp, place.left, place.top, place.width(), place.height());
    }

    public static Bitmap getBitmapScalado(String path, Rect place, int scale) {
        Bitmap bmp = loadBitmap(path);
        bmp = Bitmap.createBitmap(bmp, place.left, place.top, place.width(), place.height());
        return Bitmap.createScaledBitmap(bmp, place.width() * scale, place.height() * scale, true);
    }

    @SuppressWarnings("deprecation")
    public static Bitmap loadBitmap(String path) {
        Bitmap bmp = null;
        try {
            InputStream is = am.open(path);
            bmp = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }

    //@SuppressWarnings ("deprecation")
    public static ArrayList<Bitmap> crearAnimacionExplocionEnemigo(String path) {
        ArrayList<Bitmap> movie = new ArrayList<Bitmap>();
        try {
            int scale = 2;

            InputStream is = am.open(path);
            int witdh = 64;
            int height = 64;
            int x = 0;
            int y = 0;
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    Bitmap aux = Bitmap.createBitmap(loadBitmap(path), x, y, witdh, height);
                    aux = Bitmap.createScaledBitmap(aux, witdh / scale, height / scale, true);
                    movie.add(aux);
                    x += witdh;
                }
                y += height;
                x = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movie;
    }

    public static ArrayList<Bitmap> getAnimacionExplocionEnemigo(float x) {
        if (x % 2 != 0) {
            return AnimacionColision1;
        } else {
            return AnimacionColision2;
        }
    }
}
