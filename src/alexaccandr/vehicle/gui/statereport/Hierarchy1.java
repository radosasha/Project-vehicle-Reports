package alexaccandr.vehicle.gui.statereport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alexaccandr.vehicle.gui.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Hierarchy1 extends Activity {
	// Контекст страницы
	Context context;
	// переменные списка
	SimpleAdapter adapter = null;
	// текущий список
	List<HashMap<String, Object>> fillMaps = null;
	// Заголовок
	TextView head;
	// "намерение" перейти на страницу "пункты"
	Intent wheelPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wheels_position_layout);
		// ссылка на контекст
		context = this;
		wheelPosition = new Intent(context, WheelPosition.class);

		// получаем ссылку на заголовок
		head = (TextView) findViewById(R.wheels.text);
		// присваиваем название
		head.setText("Осмотр. Иерархия 1.");
		// инициализация списка
		ListView lv = (ListView) findViewById(R.wheels.list);
		String[] from = new String[] { "rowid0", "rowid1" };
		int[] to = new int[] { R.list_report_state.id1, R.list_report_state.id2 };
		// тестовые наборы
		fillMaps = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {			
			HashMap<String, Object> textData = new HashMap<String, Object>();
			textData.put("rowid0", "пункт " + i);
			textData.put("rowid1", R.drawable.photo_not_choosed);
			fillMaps.add(textData);
		}
		
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
				Intent h1 = new Intent(context,Hierarchy2.class);
				h1.putExtra("dir", getIntent().getExtras().getString("dir")+((Map<String, String>) arg0.getItemAtPosition(arg2)).get("rowid0")+"/");
				startActivity(h1);
				//startActivity(new Intent(context,Hierarchy2.class));
			}
		});
	}
}