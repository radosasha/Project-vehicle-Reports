package alexaccandr.vehicle.gui.addVehicleTabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alexaccandr.vehicle.gui.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PhotoTab extends Activity {
	// Контекст страницы
	/*Context context;

	// переменные списка
	SimpleAdapter adapter = null;
	List<HashMap<String, Object>> fillMaps = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_new_am_layout);
		// ссылка на контекст
		context = this;

		// инициализация списка
		ListView lv = (ListView) findViewById(R.new_am.list);
		String[] from = new String[] { "rowid0", "rowid1", "rowid2" };
		int[] to = new int[] { R.list_new_am.id1, R.list_new_am.id2,
				R.list_new_am.id3 };
		// prepare the list of all records
		fillMaps = new ArrayList<HashMap<String, Object>>();
		adapter = new SimpleAdapter(this, fillMaps, R.layout.list_new_am, from,
				to);
		lv.setAdapter(adapter);
		// тестовые наборы
		for (int i = 0; i < 15; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rowid0", "08973");
			map.put("rowid1", "Audi R8 Coupe 4.2");
			map.put("rowid2", "VIN: 089156733YT12EWQT");
			fillMaps.add(map);
		}
		adapter.notifyDataSetChanged();

		// установить слушателя на элемент списка
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// вывести очередной список для выбора действий
			//	AMElements aem = new AMElements(context);
				aem.onVehicleClicked(arg0, arg2);
			}
		});
	}
	

		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}*/
}
