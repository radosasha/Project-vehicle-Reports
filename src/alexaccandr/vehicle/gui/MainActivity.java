package alexaccandr.vehicle.gui;

import alexaccandr.vehicle.gui.mainTabs.AllAMTab;
import alexaccandr.vehicle.gui.mainTabs.AuthorizationTab;
import alexaccandr.vehicle.gui.mainTabs.NewAMTab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	/** Called when the activity is first created. */
	Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		/* TabHost will have Tabs */
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		/*
		 * TabSpec used to create a new tab. By using TabSpec only we can able
		 * to setContent to the tab. By using TabSpec setIndicator() we can set
		 * name to tab.
		 */

		// добавить уникальные имена таба. для доступа outside
		TabSpec TabAllAM = tabHost.newTabSpec("tid1");
		TabSpec TabNewAM = tabHost.newTabSpec("tid2");
		TabSpec TabAuth = tabHost.newTabSpec("tid3");

		// назначить разметку для кнопок (Tabs)
		TabAllAM.setIndicator("Все а/м",getResources().getDrawable(R.drawable.all_am_icon)).setContent(
				new Intent(this, AllAMTab.class));
		TabNewAM.setIndicator("Новые а/м",getResources().getDrawable(R.drawable.new_am_icon)).setContent(  
				new Intent(this, NewAMTab.class));
		TabAuth.setIndicator("Аккаунт",getResources().getDrawable(R.drawable.account_icon)).setContent(
				new Intent(this, AuthorizationTab.class)); 

		/* Добавить Табы в TabHost для отображения. */
		tabHost.addTab(TabAllAM); 
		tabHost.addTab(TabNewAM);
		tabHost.addTab(TabAuth);
		tabHost.setCurrentTab(2);
		

		LinearLayout addNewVehicle = (LinearLayout)findViewById(R.id.add_new_vehicle);		
		// слушатель на кнопку "Добавить новый"
		addNewVehicle.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// FIX
				toast("New Vehicle Clicked");
			}
		});
	}
	
	// установить слушателя на кнопку "back"
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				// вывод диалогового окна
				mess(); 
				return true;
			} else if (event.getAction() == KeyEvent.ACTION_UP) {
				if (event.isTracking() && !event.isCanceled()) {
					return true;
				}
			}
			return super.dispatchKeyEvent(event);
		} else {
			return super.dispatchKeyEvent(event);
		}
	}

	/*
	 * notify user he want to quit app
	 */
	void mess() {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
		alertbox.setTitle("Выход из приложения");
		alertbox.setMessage("Вы уверены что хотите выйти ?");
		alertbox.setPositiveButton("Выйти",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						System.exit(0);
					}
				});

		alertbox.setNeutralButton("Отмена",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
		alertbox.show();
	}

	void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	

	
}