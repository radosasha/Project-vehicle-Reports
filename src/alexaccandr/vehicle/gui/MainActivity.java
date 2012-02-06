package alexaccandr.vehicle.gui;

import alexaccandr.vehicle.gui.mainTabs.AllAMTab;
import alexaccandr.vehicle.gui.mainTabs.AuthorizationTab;
import alexaccandr.vehicle.gui.mainTabs.NewAMTab;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/* TabHost will have Tabs */
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		/*
		 * TabSpec used to create a new tab. By using TabSpec only we can able
		 * to setContent to the tab. By using TabSpec setIndicator() we can set
		 * name to tab.
		 */

		/* tid1 is firstTabSpec Id. Its used to access outside. */
		TabSpec TabAllAM = tabHost.newTabSpec("tid1");
		TabSpec TabNewAM = tabHost.newTabSpec("tid2");
		TabSpec TabAuth = tabHost.newTabSpec("tid3");

		/* TabSpec setIndicator() is used to set name for the tab. */
		/* TabSpec setContent() is used to set content for a particular tab. */
		TabAllAM.setIndicator("Все а/м",getResources().getDrawable(R.drawable.all_am_icon)).setContent(
				new Intent(this, AllAMTab.class));
		TabNewAM.setIndicator("Новые а/м",getResources().getDrawable(R.drawable.new_am_icon)).setContent(  
				new Intent(this, NewAMTab.class));
		TabAuth.setIndicator("Аккаунт",getResources().getDrawable(R.drawable.account_icon)).setContent(
				new Intent(this, AuthorizationTab.class)); 

		/* Add tabSpec to the TabHost to display. */
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
	
	void toast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}