package alexaccandr.vehicle.gui.statereport;

import alexaccandr.vehicle.gui.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Options extends Activity{
	CheckBox ch1;
	CheckBox ch2;
	Activity context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.options);
		context = this;
		ch1 = (CheckBox)findViewById(R.options.ch1);
		ch2 = (CheckBox)findViewById(R.options.ch2);
		// тестовый слушатель		
		ch2.setOnClickListener(chListener);
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
			
			final CharSequence[] items = {"ничего","Климат 1-зонный","Климат 2-зонный","Климат 3-зонный"};
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
