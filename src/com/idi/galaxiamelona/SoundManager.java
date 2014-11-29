package com.idi.galaxiamelona;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import com.idi.Entity.Constantes;
import java.util.ArrayList;

public class SoundManager {

    MediaPlayer principalAmbiente;
    private static ArrayList<MediaPlayer> disparoJugador;
    private static ArrayList<MediaPlayer> disparoEnemigo;
    private static ArrayList<MediaPlayer> explosion;

    private static AssetFileDescriptor contenedor;
    private static AssetManager asset;

    public SoundManager(AssetManager asset) {
        principalAmbiente = new MediaPlayer();
        this.asset = asset;
        try {
            contenedor = asset.openFd(Constantes.PATH_MUSICA_FONDO_ESCENA);
            principalAmbiente.setDataSource(contenedor.getFileDescriptor(), contenedor.getStartOffset(), contenedor.getLength());
            principalAmbiente.setLooping(true);
            contenedor.close();

            principalAmbiente.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //principalAmbiente.start();
    }

    public void pausarAmbiente() {
        principalAmbiente.pause();
    }

    public void detenerAmbiente() {
        principalAmbiente.stop();
    }

    public void iniciarAmbiente() {
        principalAmbiente.start();
    }

    public static void explocion() {
        if (explosion == null) {
            definirExplocion();
        }
        if (explosion != null) {
            boolean libreEncontrado = false;
            for (int i = 0; i < explosion.size() && !libreEncontrado; ++i) {
                if (!explosion.get(i).isPlaying()) {
                    //explosion.get(i).start();
                    libreEncontrado = true;
                }
            }
        }
    }

    public static void disparoJugador() {
        if (disparoJugador == null) {
            definirDisparoJugador();
        }

        if (disparoJugador != null) {
            boolean libreEncontrado = false;
            for (int i = 0; i < disparoJugador.size() && !libreEncontrado; ++i) {
                if (!disparoJugador.get(i).isPlaying()) {
                    //disparoJugador.get(i).start();
                    libreEncontrado = true;
                }
            }
        }
    }

    public static void disparoEnemigo() {
        if (disparoEnemigo == null) {
            definirDisparoEnemigo();
        }
        if (disparoEnemigo != null) {
            boolean libreEncontrado = false;
            for (int i = 0; i < disparoEnemigo.size() && !libreEncontrado; ++i) {
                if (!disparoEnemigo.get(i).isPlaying()) {
                    //disparoEnemigo.get(i).start();
                    libreEncontrado = true;
                }
            }
        }
    }

    private static void definirExplocion() {
        if (asset != null) {
            try {
                explosion = new ArrayList<MediaPlayer>();
                contenedor = asset.openFd(Constantes.PATH_SONIDO_EXPLOCION_1);
                for (int i = 0; i < Constantes.TAMAÑO_BUFFER_SONIDO_EXPLOCINES; ++i) {
                    explosion.add(new MediaPlayer());
                    explosion.get(i).setDataSource(contenedor.getFileDescriptor(), contenedor.getStartOffset(), contenedor.getLength());
                    explosion.get(i).prepare();
                }
                contenedor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void definirDisparoEnemigo() {
        if (asset != null) {
            try {
                disparoEnemigo = new ArrayList<MediaPlayer>();
                contenedor = asset.openFd(Constantes.PATH_SONIDO_DISPARO_ENEMIGO);
                for (int i = 0; i < Constantes.TAMAÑO_BUFFER_SONIDO_ENEMIGO; ++i) {
                    disparoEnemigo.add(new MediaPlayer());
                    disparoEnemigo.get(i).setDataSource(contenedor.getFileDescriptor(), contenedor.getStartOffset(), contenedor.getLength());
                    disparoEnemigo.get(i).prepare();
                }
                contenedor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void definirDisparoJugador() {
        if (asset != null) {
            try {
                disparoJugador = new ArrayList<MediaPlayer>();
                contenedor = asset.openFd(Constantes.PATH_SONIDO_DISPARO_JUGADOR);
                for (int i = 0; i < Constantes.TAMAÑO_BUFFER_SONIDO_DISPAROS; ++i) {
                    disparoJugador.add(new MediaPlayer());
                    disparoJugador.get(i).setDataSource(contenedor.getFileDescriptor(), contenedor.getStartOffset(), contenedor.getLength());
                    disparoJugador.get(i).prepare();
                }
                contenedor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
