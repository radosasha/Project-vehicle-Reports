package alexaccandr.vehicle.gui.addVehicleTabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alexaccandr.vehicle.gui.R;
import alexaccandr.vehicle.gui.statereport.Complex;
import alexaccandr.vehicle.gui.statereport.Diagnostic;
import alexaccandr.vehicle.gui.statereport.Hierarchy1;
import alexaccandr.vehicle.gui.statereport.SummerWinter;
import alexaccandr.vehicle.gui.statereport.Options;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class StateReportTab extends Activity {
	// Контекст страницы
	Context context;

	// переменные списка
	SimpleAdapter adapter = null;
	// текущий список
	List<HashMap<String, Object>> fillMaps = null;
	// список элементов первого уровня иерархии
	List<HashMap<String, Object>> reportStateList = null;
	// список элементов второго уровня иерархии
	List<HashMap<String, Object>> externalInspectionList = null;
	// список элементов второго уровня иерархии
	List<HashMap<String, Object>> internalInspectionList = null;
	
	// константы всех уровней иерархии
	final int COMPLECT = 0;
	final int OPTIONS = 1;
	final int EXTERNAL_INSPECTION = 2;
	final int INTERNAL_INSPECTION = 3;
	final int WHEELS_STATE = 4;
	final int DIAGNOSTIC = 5;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_state_report_layout);
		// ссылка на контекст
		context = this;
		
		// инициализация списка
		ListView lv = (ListView) findViewById(R.state_report.list);
		String[] from = new String[] { "rowid0", "rowid1" };
		int[] to = new int[] { R.list_report_state.id1, R.list_report_state.id2 };
		// prepare the list of all records
		// установим список на первый уровень иерархии
		fillMaps = getFirstLevelList();
		adapter = new SimpleAdapter(this, fillMaps, R.layout.list_report_state, from,
				to);
		lv.setAdapter(adapter);
		
		// обновить изменения в списке
		adapter.notifyDataSetChanged();

		// установить слушателя на элемент списка
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				case COMPLECT:
					startActivity(new Intent(context,Complex.class));
					break;
				case OPTIONS:
					startActivity(new Intent(context,Options.class));
					break;
				case EXTERNAL_INSPECTION:
					startActivity(new Intent(context,Hierarchy1.class));
					break;
				case INTERNAL_INSPECTION:
					startActivity(new Intent(context,Hierarchy1.class));
					break;
				case DIAGNOSTIC:
					startActivity(new Intent(context,Diagnostic.class)); 
					break;
				case WHEELS_STATE:
					startActivity(new Intent(context,SummerWinter.class));
				}
			}
		});
	}
	
	// вернуть список элементов для первого уровня иерархии
	List<HashMap<String, Object>> getFirstLevelList() {
		// заполнение списка
		if (reportStateList != null)
			return reportStateList;
		// инициализация списка первого уровня
		reportStateList = new ArrayList<HashMap<String, Object>>();

		// комплексность
		HashMap<String, Object> complex = new HashMap<String, Object>();
		complex.put("rowid0", "Комплектность");
		complex.put("rowid1", R.drawable.photo_not_choosed);
		reportStateList.add(complex);

		// опции
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("rowid0", "Опции");
		options.put("rowid1", R.drawable.photo_not_choosed);
		reportStateList.add(options);

		// внешний осмотр
		HashMap<String, Object> externalInspection = new HashMap<String, Object>();
		externalInspection.put("rowid0", "Внешний осмотр");
		externalInspection.put("rowid1", R.drawable.photo_not_choosed);
		reportStateList.add(externalInspection);

		// внутренний осмотр
		HashMap<String, Object> internalInspection = new HashMap<String, Object>();
		internalInspection.put("rowid0", "Внутренний осмотр");
		internalInspection.put("rowid1", R.drawable.photo_not_choosed);
		reportStateList.add(internalInspection);

		// осмотр колес
		HashMap<String, Object> wheelsInspection = new HashMap<String, Object>();
		wheelsInspection.put("rowid0", "Осмотр колес");
		wheelsInspection.put("rowid1", R.drawable.photo_not_choosed);
		reportStateList.add(wheelsInspection);

		// диагностика
		HashMap<String, Object> diagnostic = new HashMap<String, Object>();
		diagnostic.put("rowid0", "Диагностика");
		diagnostic.put("rowid1", R.drawable.photo_not_choosed);
		reportStateList.add(diagnostic);
		return reportStateList;
	}


	/*
	 * тестовые наборы
	 * тестовые наборы
	 * тестовые наборы
	 * тестовые наборы
	 * тестовые наборы
	 * тестовые наборы
	 * тестовые наборы
	 */
	
	/*// вернуть список элементов для первого уровня иерархии
	List<HashMap<String, Object>> getExternalInspectionLevel() {
		// заполнение списка
		if (externalInspectionList != null)
			return externalInspectionList;
		// инициализация списка
		externalInspectionList = new ArrayList<HashMap<String, Object>>();
		// передняя сторона
		HashMap<String, Object> forwardSide = new HashMap<String, Object>();
		forwardSide.put("rowid0", "Передняя сторона");
		forwardSide.put("rowid1", R.drawable.photo_not_choosed);
		externalInspectionList.add(forwardSide);

		// передняя правая топрона
		HashMap<String, Object> rightForwardSide = new HashMap<String, Object>();
		rightForwardSide.put("rowid0", "Передняя правая сторона");
		rightForwardSide.put("rowid1", R.drawable.photo_not_choosed);
		externalInspectionList.add(rightForwardSide);

		// передняя левая сторона
		HashMap<String, Object> LeftForwardSide = new HashMap<String, Object>();
		LeftForwardSide.put("rowid0", "Передняя правая сторона");
		LeftForwardSide.put("rowid1", R.drawable.photo_not_choosed);
		externalInspectionList.add(LeftForwardSide);

		// внутренний осмотр
		HashMap<String, Object> internalInspection = new HashMap<String, Object>();
		internalInspection.put("rowid0", "Внутренний осмотр");
		internalInspection.put("rowid1", R.drawable.photo_not_choosed);
		externalInspectionList.add(internalInspection);

		// осмотр колес
		HashMap<String, Object> wheelsInspection = new HashMap<String, Object>();
		wheelsInspection.put("rowid0", "Осмотр колес");
		wheelsInspection.put("rowid1", R.drawable.photo_not_choosed);
		externalInspectionList.add(wheelsInspection);

		// диагностика
		HashMap<String, Object> diagnostic = new HashMap<String, Object>();
		diagnostic.put("rowid0", "Диагностика");
		diagnostic.put("rowid1", R.drawable.photo_not_choosed);
		externalInspectionList.add(diagnostic);
		return externalInspectionList;
	}

	
	// вернуть список элементов второго уровня иерархии
	List<HashMap<String, Object>> getInternalInspectionLevel() {
		// заполнение списка
		if (internalInspectionList != null)
			return internalInspectionList;
		internalInspectionList = new ArrayList<HashMap<String, Object>>();
		// комплексность
		HashMap<String, Object> complex = new HashMap<String, Object>();
		complex.put("rowid0", "Комплексность");
		complex.put("rowid1", R.drawable.photo_not_choosed);
		internalInspectionList.add(complex);

		// опции
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("rowid0", "Опции");
		options.put("rowid1", R.drawable.photo_not_choosed);
		internalInspectionList.add(options);

		// внешний осмотр
		HashMap<String, Object> externalInspection = new HashMap<String, Object>();
		externalInspection.put("rowid0", "Внешний осмотр");
		externalInspection.put("rowid1", R.drawable.photo_not_choosed);
		internalInspectionList.add(externalInspection);

		// внутренний осмотр
		HashMap<String, Object> internalInspection = new HashMap<String, Object>();
		internalInspection.put("rowid0", "Внутренний осмотр");
		internalInspection.put("rowid1", R.drawable.photo_not_choosed);
		internalInspectionList.add(internalInspection);

		// осмотр колес
		HashMap<String, Object> wheelsInspection = new HashMap<String, Object>();
		wheelsInspection.put("rowid0", "Осмотр колес");
		wheelsInspection.put("rowid1", R.drawable.photo_not_choosed);
		internalInspectionList.add(wheelsInspection);

		// диагностика
		HashMap<String, Object> diagnostic = new HashMap<String, Object>();
		diagnostic.put("rowid0", "Диагностика");
		diagnostic.put("rowid1", R.drawable.photo_not_choosed);
		internalInspectionList.add(diagnostic);
		return internalInspectionList;
	}*/
}
