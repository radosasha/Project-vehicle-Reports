package alexaccandr.vehicle.gui.mainTabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alexaccandr.vehicle.gui.R;
import alexaccandr.vehicle.gui.addVehicleTabs.AddVehicle;
import alexaccandr.vehicle.photo.PhotoEditor;
import alexaccandr.vehicle.tools.ApplicationMemory;
import alexaccandr.vehicle.tools.FileSystem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AllAMTab extends Activity {
	// Контекст страницы
	Activity context;
	// "намерение" вызова страницы создания нового отчета
	Intent addReportIntent;
	// переменные списка
	SimpleAdapter adapter = null;
	List<HashMap<String, Object>> fillMaps = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_all_am_layout);
		// ссылка на контекст
		context = this;
		addReportIntent = new Intent(context,AddVehicle.class);

		// инициализация списка
		ListView lv = (ListView) findViewById(R.all_am.list);
		String[] from = new String[] { "rowid0", "rowid1", "rowid2", "rowid3",
				"rowid4" };
		int[] to = new int[] { R.list_all_am.id1, R.list_all_am.id2,
				R.list_all_am.id3, R.list_all_am.id4, R.list_all_am.id5,

		};
		// prepare the list of all records
		fillMaps = new ArrayList<HashMap<String, Object>>();
		adapter = new SimpleAdapter(this, fillMaps, R.layout.list_all_am, from,
				to);
		lv.setAdapter(adapter);
		
		// тестовые наборы для списка
		for (int i = 0; i < 15; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rowid0", "08973");
			map.put("rowid1", "Audi R8 Coupe 4.2");
			map.put("rowid2", "089156733YT12EWQT"+i);
			map.put("rowid3", R.drawable.state_report_not_choosed);
			map.put("rowid4", R.drawable.photo_not_choosed);
			FileSystem.createDirectory("089156733YT12EWQT" + i);
			fillMaps.add(map);
		}
		// обновить изменения в списке
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// чистим память 
				if(PhotoEditor.allPhotosStructure != null){
					for(int i=0;i<PhotoEditor.allPhotosStructure.size();i++){
						ApplicationMemory.eraseBitmapBuffer((Bitmap) PhotoEditor.allPhotosStructure.get(i).get(2));
					}
				}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
