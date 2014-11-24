package com.idi.galaxiamelona;

import com.idi.escena.EscenaView;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class EscenaActivity extends Activity {
	EscenaView escena;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setConviewAccion();
	}

	public void setConviewAccion() {
		setContentView(new EscenaView(this));

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
