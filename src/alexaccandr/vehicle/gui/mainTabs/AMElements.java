package alexaccandr.vehicle.gui.mainTabs;
import java.util.ArrayList;
import java.util.Map;

import alexaccandr.vehicle.camera.TakeAPhoto;
import alexaccandr.vehicle.gui.R;
import alexaccandr.vehicle.gui.addVehicleTabs.AddVehicle;
import alexaccandr.vehicle.gui.addVehicleTabs.StateReportTab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Класс содержит общие элементы пользовательского интерфейса
 * для станиц "Все a/м" и "Новые a/м"
 */
public class AMElements extends Activity{
	// контекст вызывающего класса
	Activity context;
	// намерение "добавить фото
	Intent photos;
	// намерение "добавить отчет о состоянии"
	Intent stateRep;
	
	public AMElements(Activity context) {
		this.context = context;		
	}
	
	// вывод списка действий по нажатию на автомобиль
	void onVehicleClicked(AdapterView<?> arg0, int arg2){
		// наименование всех действий
		final CharSequence[] items = {"Добавить отчет о состоянии", "Добавить фотографии", "Дублировать атвомобиль"};
		// инициализация списка
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// собрать заголовок
		Map<String, String> selection = (Map<String, String>) arg0.getItemAtPosition(arg2);
		// папка данной машины
		final String autoFolder = selection.get("rowid2");
		builder.setTitle(selection.get("rowid0")+" "+selection.get("rowid1")+" "+selection.get("rowid2"));
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	switch(item){
		    	// добавить отчет о состоянии
		    	case 0:		    		
		    		stateRep = new Intent(context, AddVehicle.class);
		    		// передать номер таба
		    		stateRep.putExtra("curtab", 1);
		    		stateRep.putExtra("dir", "/sdcard/CarMobile/Photo/"+autoFolder+"/");
		    		context.startActivityForResult(stateRep,0);
		    		break;
		    	// добавить фотографии
		    	case 1:		    		
		    		photos = new Intent(context, TakeAPhoto.class);
		    		photos.putExtra("cmnd", 0);
		    		photos.putExtra("dir", "/sdcard/CarMobile/Photo/"+autoFolder+"/");
		    		context.startActivity(photos);
		    		break;
		    	// дублировать автомобиль
		    	case 2:
		    		Toast.makeText(context, "Дублировать автомобиль", Toast.LENGTH_SHORT).show();
		    	}
		    }
		});
		builder.show();
	}
	
	void onDownloadClick(){
		 final ListView lv;
		 final EditText et;
		 final String listview_array[] = {"Единая Россия", "ООО \"Рога и Копыта\"", "Рольф", "\"ростикс KFC\"", "Другое","Без названия" };
		 final ArrayList<String> array_sort = new ArrayList<String>();
		// создаем диалоговое окно
			final Dialog dialog = new Dialog(context);
			// разметка окна
			dialog.setContentView(R.layout.search_dialog);
			// заголовок
			dialog.setTitle("Загрузка автомобилей");
			dialog.setCancelable(true);
			// текстовое окно для ввода названия неисравности
			//final EditText et = (EditText) dialog.findViewById(R.error.et);
			lv = (ListView)dialog. findViewById(R.search.list);
			et = (EditText)dialog. findViewById(R.search.edittext);
			setAdapter(listview_array, lv);
			et.addTextChangedListener(new TextWatcher() {
				public void afterTextChanged(Editable s) {
					// Abstract Method of TextWatcher Interface.
				}

				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// Abstract Method of TextWatcher Interface.
				}

				public void onTextChanged(CharSequence s, int start, int before,
						int count) {
					 final int textlength = et.getText().length();
					array_sort.clear();
					for (int i = 0; i < listview_array.length; i++) {
						if (textlength <= listview_array[i].length()) {
							if (et.getText()
									.toString()
									.equalsIgnoreCase(
											(String) listview_array[i].subSequence(
													0, textlength))) {
								array_sort.add(listview_array[i]);
							}
						}
					}
					
					setAdapter(array_sort, lv);
				}
				
			});
			dialog.show();
	
	}
	
	void setAdapter(String [] array_sort, ListView listView){
		listView.setAdapter(new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1,  array_sort){
	        @Override
	        public View getView(int position, View convertView,
	                ViewGroup parent) {
	            View view =super.getView(position, convertView, parent);
	            TextView textView=(TextView) view.findViewById(android.R.id.text1);
	            /*YOUR CHOICE OF COLOR*/
	            textView.setTextColor(Color.BLACK);
	            return view;
	        }
	    });
	}
	
	private void setAdapter(ArrayList<String> array_sort,
			ListView listView) {
		listView.setAdapter(new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1,  array_sort){
	        @Override
	        public View getView(int position, View convertView,
	                ViewGroup parent) {
	            View view =super.getView(position, convertView, parent);
	            TextView textView=(TextView) view.findViewById(android.R.id.text1);
	            /*YOUR CHOICE OF COLOR*/
	            textView.setTextColor(Color.BLACK);
	            return view;
	        }
	    });
	}
}
