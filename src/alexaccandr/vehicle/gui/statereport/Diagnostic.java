package alexaccandr.vehicle.gui.statereport;

import alexaccandr.vehicle.gui.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;

public class Diagnostic extends Activity{
	CheckBox ch1;
	CheckBox ch2;
	CheckBox ch3;
	CheckBox ch4;
	CheckBox ch5;
	CheckBox ch6;
	CheckBox ch7;
	CheckBox ch8;
	CheckBox ch9;
	CheckBox ch10;
	Activity context;
	TextView vv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
				setContentView(R.layout.diagnostic);
				context = this;
				ch1 = (CheckBox)findViewById(R.diagnostic.ch1);
				ch2 = (CheckBox)findViewById(R.diagnostic.ch2);
				ch3 = (CheckBox)findViewById(R.diagnostic.ch3);
				ch4 = (CheckBox)findViewById(R.diagnostic.ch4);
				ch5 = (CheckBox)findViewById(R.diagnostic.ch5);
				ch6 = (CheckBox)findViewById(R.diagnostic.ch6);
				ch7 = (CheckBox)findViewById(R.diagnostic.ch7);
				ch8 = (CheckBox)findViewById(R.diagnostic.ch8);
				ch9 = (CheckBox)findViewById(R.diagnostic.ch9);
				ch10 = (CheckBox)findViewById(R.diagnostic.ch10);
				// тестовый слушатель
				ch1.setOnClickListener(chListener);
				ch2.setOnClickListener(chListener);
				ch3.setOnClickListener(chListener);
				ch4.setOnClickListener(chListener);
				ch5.setOnClickListener(chListener);
				ch6.setOnClickListener(chListener);
				ch7.setOnClickListener(chListener);
				ch8.setOnClickListener(chListener);
				ch9.setOnClickListener(chListener);
				ch10.setOnClickListener(chListener);
				
	}
	
	/*
	 * тестовые данные
	 */
	View.OnClickListener chListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			final CompoundButton view = (CompoundButton) v;
			// инвертируем нажатие
			if(view.isChecked())view.setChecked(false);
			else view.setChecked(true);
			
			final CharSequence[] items = {"ничего","item 1","item 2","item 3","item 4","item 5"};
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Выберите пункт");
			builder.setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	switch(item){
			    	case 0:
						view.setChecked(false);
						dialog.cancel();
						break;
					default:
						view.setChecked(true);
						dialog.cancel();
					}
			    	
			    }
			});
			builder.create();
			builder.show();				
			
		}
	};
}
