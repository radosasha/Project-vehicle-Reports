package alexaccandr.vehicle.gui.statereport;

import java.util.HashMap;
import java.util.List;

import alexaccandr.vehicle.gui.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class Complex extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.complex);
		
	}
}