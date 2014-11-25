package com.idi.galaxiamelona;

import com.idi.escena.EscenaView;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class EscenaActivity extends Activity {
	EscenaView escena;
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

		/*
		 * setContentView(R.layout.activity_escena); escena = new
		 * EscenaView(this); RelativeLayout myLayout = new RelativeLayout(this);
		 * myLayout.setLayoutParams(new
		 * RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
		 * LayoutParams.MATCH_PARENT));
		 * 
		 * 
		 * 
		 * myLayout.addView( new EscenaView(this));
		 */
	}
}
