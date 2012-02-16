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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WheelPosition extends Activity {
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
		wheelPosition = new Intent(context, WheelsState.class);

		// получаем ссылку на заголовок
		head = (TextView) findViewById(R.wheels.text);
		// устанавливаем заголовок
		head.setText(getIntent().getExtras().getString("head"));
		// инициализация списка
		ListView lv = (ListView) findViewById(R.wheels.list);
		String[] from = new String[] { "rowid0", "rowid1" };
		int[] to = new int[] { R.list_report_state.id1, R.list_report_state.id2 };
		// prepare the list of all records
		fillMaps = new ArrayList<HashMap<String, Object>>();
		// Разметка пункта "переднее левое полесо"
				HashMap<String, Object> wheelFR = new HashMap<String, Object>();
				wheelFR.put("rowid0", "Переднее левое полесо");
				wheelFR.put("rowid1", R.drawable.photo_not_choosed);

				// Разметка пункта "переднее правое колесо"
				HashMap<String, Object> wheelFL = new HashMap<String, Object>();
				wheelFL.put("rowid0", "Переднее правое колесо");
				wheelFL.put("rowid1", R.drawable.photo_not_choosed);

				// Разметка пункта "заднее левое колесо" в списке
				HashMap<String, Object> wheelBL = new HashMap<String, Object>();
				wheelBL.put("rowid0", "Заднее левое колесо");
				wheelBL.put("rowid1", R.drawable.photo_not_choosed);

				// Разметка пункта "заднее правое колесо" в списке
				HashMap<String, Object> wheelBR = new HashMap<String, Object>();
				wheelBR.put("rowid0", "Заднее правое колесо");
				wheelBR.put("rowid1", R.drawable.photo_not_choosed);

				// добавить всех пунктов в список
				fillMaps.add(wheelFL);
				fillMaps.add(wheelFR);
				fillMaps.add(wheelBL);
				fillMaps.add(wheelBR);
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
				// отправить заголовок с название выбранного пункта
				// собрать заголовок
					wheelPosition.putExtra("head",((Map<String, String>) arg0.getItemAtPosition(arg2)).get("rowid0"));			
				startActivity(wheelPosition);
			}
		});
	}
}