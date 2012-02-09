package alexaccandr.vehicle.gui.mainTabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alexaccandr.vehicle.gui.R;
import alexaccandr.vehicle.gui.addVehicleTabs.AddVehicle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class NewAMTab extends Activity {
	// Контекст страницы
	Context context;
	// "намерение" вызова страницы создания нового отчета
	Intent addReportIntent;
	// константы результата
	private final int RESULT_OK = 101;
	private final int RESULT_CANCEL = 102;	
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
		addReportIntent = new Intent(context,AddVehicle.class);
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
				AMElements aem = new AMElements(context);
				aem.onVehicleClicked(arg0, arg2);
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.menu.add_new:
			// Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show();
			// передать номер таба
			addReportIntent.putExtra("curtab", 0);
			startActivityForResult(addReportIntent, RESULT_OK);
			break;
		case R.menu.download:
			Toast.makeText(context, "Download clicked", Toast.LENGTH_SHORT).show();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/*
	 * @see установить слушателя на результат вызова страницы "добавить новый отчет"
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			Toast.makeText(context, "Добавлен новый отчет", Toast.LENGTH_LONG).show();
			break;
		case RESULT_CANCEL:
			Toast.makeText(context, "Создание отчета отменено", Toast.LENGTH_LONG).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
