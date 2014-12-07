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

public class EscenaActivity extends Activity{
	EscenaView escenaView;
	AssetManager assetManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context=getApplicationContext();
		assetManager = getAssets();
		setConviewAccion();
	}

	public void setConviewAccion() {
		setContentView(new EscenaView(this,assetManager));
	}

}
