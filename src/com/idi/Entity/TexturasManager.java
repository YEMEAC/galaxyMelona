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

    private static HashMap<String, Bitmap> texturas;

    private static ArrayList<Bitmap> AnimacionColision1; //así no tengo que cargar la animacion en cad colision OPTIMIZACION
    private static ArrayList<Bitmap> AnimacionColision2;
    private static AssetManager am;

    public TexturasManager(AssetManager a) {
        am = a;
        texturas = new HashMap<String, Bitmap>();
        Bitmap aux;

        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVE_JUGADOR, new Rect(0, 0, 60, 63), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_JUGADOR, aux);     
        aux = getBitmapScalado(Constantes.PATH_SPRITE_DISPARO_JUGADOR, new Rect(0, 0, 11, 29),1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_DISPAROJUGADOR, aux);
         aux = getBitmapScalado(Constantes.PATH_SPRITE_JUGADOR_VIDAS, new Rect(0, 0, 25, 26),1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_JUGADOR_VIDAS, aux);
        
        
        //CABO
        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVE_CABO_1, new Rect(0, 0, 48, 37), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CABO_1, aux);
        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVE_CABO_2, new Rect(0, 0, 48, 37), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CABO_2, aux);
        aux = getBitmapScalado(Constantes.PATH_SPRITE_DISPARO_CABO, new Rect(0, 0, 3, 29), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_DISPARO_ENEMIGO_CABO, aux);
        
        //SARGENTO
        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVE_SARGENTO_1, new Rect(0, 0, 48, 37), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_SARGENTO_1, aux);
        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVE_SARGENTO_2, new Rect(0, 0, 48, 37), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_SARGENTO_2, aux);
        aux = getBitmapScalado(Constantes.PATH_SPRITE_DISPARO_SARGENTO, new Rect(0, 0, 3, 29), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_DISPARO_ENEMIGO_SARGENTO, aux);
        
        
        //CORONEL
        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVE_CORONEL_1, new Rect(0, 0, 48, 37), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CORONEL_1, aux);
        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVE_CORONEL_2, new Rect(0, 0, 48, 37), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_CORONEL_2, aux);
        aux = getBitmapScalado(Constantes.PATH_SPRITE_DISPARO_CORONEL, new Rect(0, 0, 3, 29), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_DISPARO_ENEMIGO_CORONEL, aux);
        
        
        //TENIENTE
        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVE_TENIENTE_1, new Rect(0, 0, 48, 37), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_TENIENTE_1, aux);
        aux = getBitmapScalado(Constantes.PATH_SPRITE_NAVE_TENIENTE_2, new Rect(0, 0, 48, 37), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_ENEMIGO_TENIENTE_2, aux);
        aux = getBitmapScalado(Constantes.PATH_SPRITE_DISPARO_TENIENTE, new Rect(0, 0, 3, 29), 1);
        anadirTextura(Constantes.TEXTURAS_TEXTURA_DISPARO_ENEMIGO_TENIENTE, aux);
        

        AnimacionColision1 = crearAnimacionExplocionEnemigo(Constantes.PATH_ANIMACION_EXPLOCION_ENEMIO_1);
        AnimacionColision2 = crearAnimacionExplocionEnemigo(Constantes.PATH_ANIMACION_EXPLOCION_ENEMIO_2);

    }

    public static Bitmap getTextura(String s) {
        return texturas.get(s);
    }

    public void setAssetManager(AssetManager a) {
        am = a;
    }

    static void anadirTextura(String jugador, Bitmap bitmap) {
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
            int scale = 1;

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
