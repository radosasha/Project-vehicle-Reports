package alexaccandr.vehicle.gui.statereport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alexaccandr.vehicle.gui.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SummerWinter extends Activity implements
AdapterView.OnItemSelectedListener {
	// Контекст страницы
	Context context;
	// переменные списка
	SimpleAdapter adapter = null;
	// текущий список
	List<HashMap<String, Object>> fillMaps = null;
	// Заголовок
	TextView head;
	// спинер "шины"
	Spinner bus;
	// "намерение" перейти на страницу "расположение колеса
	Intent wheelPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wheels_layout);
		// ссылка на контекст
		context = this;
		wheelPosition = new Intent(context, WheelPosition.class);

		// получаем ссылку на заголовок
		head = (TextView) findViewById(R.wheels.text);
		// присваиваем название
		head.setText("Сезонность шины");
		// выбор шины
		bus = (Spinner)findViewById(R.wheel.spinner);
		// инициализация спиннера
		//тестовые данные
		String[] items = { "bus1","bus2","bus3","bus4","bus5","bus6","bus7" };
		bus.setOnItemSelectedListener(this);

		ArrayAdapter aa = new ArrayAdapter(
				this,
				android.R.layout.simple_spinner_item, 
				items);

		aa.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);
		bus.setAdapter(aa);
		
		
	

		// инициализация списка
		ListView lv = (ListView) findViewById(R.wheels.list);
		String[] from = new String[] { "rowid0", "rowid1" };
		int[] to = new int[] { R.list_report_state.id1, R.list_report_state.id2 };
		// prepare the list of all records
		fillMaps = new ArrayList<HashMap<String, Object>>();
		// Разметка пункта "лето" в списке
		HashMap<String, Object> summer = new HashMap<String, Object>();
		summer.put("rowid0", "Лето");
		summer.put("rowid1", R.drawable.photo_not_choosed);

		// Разметка пункта "зима" в списке
		HashMap<String, Object> winter = new HashMap<String, Object>();
		winter.put("rowid0", "Зима");
		winter.put("rowid1", R.drawable.photo_not_choosed);
		

		// добавить оба пункта в список
		fillMaps.add(summer);
		fillMaps.add(winter);
		// установим список на первый уровень иерархии
		adapter = new SimpleAdapter(this, fillMaps, R.layout.list_report_state,
				from, to);
		lv.setAdapter(adapter);

		// обновить изменения в списке
		adapter.notifyDataSetChanged();

		// установить слушателя на элемент списка
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// выбран пункт
				switch (arg2) {
				// лето
				case 0:
					wheelPosition.putExtra("item", 0);
					startActivity(wheelPosition);
					break;
				// зима
				case 1:
					wheelPosition.putExtra("item", 1);
					startActivity(wheelPosition);
				}
			}
		});
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}