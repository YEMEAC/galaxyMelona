package com.idi.galaxiamelona;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {
	int a;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
                //assuming your Layout is named linearlayout1:
                //LinearLayout ll = (LinearLayout) findViewById(R.layout.activity_main);
                //ll.setBackgroundResource(R.drawable.fondo.gif);
		//ocultarBarras();
		anadirListeners();	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}
	
	 
	 public void ocultarBarras(){
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		                     WindowManager.LayoutParams.FLAG_FULLSCREEN);
	 }
	 
	 public void empiezaJuego(){
		 Intent juego = new Intent(this, EscenaActivity.class);
		 this.startActivity(juego);
	 }

	private void anadirListeners() {

		TextView jugar = (TextView) findViewById(R.id.boton_jugar);
		jugar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), R.string.jugar,Toast.LENGTH_SHORT).show();			
				empiezaJuego();
			}
		});
		
		TextView opciones = (TextView) findViewById(R.id.boton_opciones);
		opciones.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), R.string.opciones,
						Toast.LENGTH_SHORT).show();
			}
		});
		
		TextView salir = (TextView) findViewById(R.id.boton_salir);
		salir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}
}
