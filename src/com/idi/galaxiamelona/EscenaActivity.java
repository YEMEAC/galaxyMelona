package com.idi.galaxiamelona;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.idi.escena.EscenaView;

public class EscenaActivity extends Activity implements OnKeyListener {

    MenuActivity parenActivity;
    EscenaView escenaView;
    AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        assetManager = getAssets();
        setConviewAccion();
    }

    public void setConviewAccion() {
        escenaView = new EscenaView(this, assetManager, this);
        setContentView(escenaView);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return true;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // super.onKeyUp(keyCode, event);
        escenaView.getEscena().getTeclas().remove(keyCode);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //super.onKeyDown(keyCode, event);
        escenaView.getEscena().getTeclas().add(keyCode);
        return true;
    }

    public void finalizarEscena() {
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EscenaView.setPausarStatic(2);
        SoundManager.pausarAmbiente();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onKey(escenaView, RESULT_OK, null);
        this.onKeyDown(RESULT_OK, null);
        this.onKeyUp(RESULT_OK, null);
        EscenaView.setPausarStatic(0);
        SoundManager.reiniciarAmbiente();
    }

}
