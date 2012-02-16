package alexaccandr.vehicle.gui.statereport;

import alexaccandr.vehicle.gui.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

public class WheelsState extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wheels_state);
		setTitle(getIntent().getExtras().getString("head"));		
	}
}
