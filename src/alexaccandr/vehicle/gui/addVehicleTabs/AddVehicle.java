package alexaccandr.vehicle.gui.addVehicleTabs;

import alexaccandr.vehicle.gui.R;
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

public class AddVehicle extends TabActivity {
	/** Called when the activity is first created. */
	Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_vehicle);
		context = this;
		/* TabHost will have Tabs */
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		/*
		 * TabSpec used to create a new tab. By using TabSpec only we can able
		 * to setContent to the tab. By using TabSpec setIndicator() we can set
		 * name to tab.
		 */

		// добавить уникальные имена таба. для доступа outside
		TabSpec TabData = tabHost.newTabSpec("tid1");
		TabSpec TabStateReport = tabHost.newTabSpec("tid2");
		TabSpec TabPhoto = tabHost.newTabSpec("tid3");

		// назначить разметку для кнопок (Tabs)
		TabData.setIndicator("Данные",getResources().getDrawable(R.drawable.data_icon)).setContent(
				new Intent(this, DataTab.class));
		TabStateReport.setIndicator("Отчет о состоянии",getResources().getDrawable(R.drawable.state_icon)).setContent(  
				new Intent(this, StateReportTab.class));
		TabPhoto.setIndicator("Фото",getResources().getDrawable(R.drawable.photo_icon)).setContent(
				new Intent(this, PhotoTab.class)); 

		/* Добавить Табы в TabHost для отображения. */
		tabHost.addTab(TabData); 
		tabHost.addTab(TabStateReport);
		tabHost.addTab(TabPhoto);
		
		// установить текущий таб
		tabHost.setCurrentTab(getIntent().getExtras().getInt("curtab"));

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
		alertbox.setTitle("Отменить добавление отчета");
		//alertbox.setMessage("Вы уверены что хотите выйти ?");
		alertbox.setPositiveButton("Да",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				});

		alertbox.setNeutralButton("Нет",
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