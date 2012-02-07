package alexaccandr.vehicle.gui.mainTabs;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.AdapterView;
import android.widget.Toast;

/*
 * Класс содержит общие элементы пользовательского интерфейса
 * для станиц "Все a/м" и "Новые a/м"
 */
public class AMElements {
	Context context;
	public AMElements(Context context) {
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
		builder.setTitle(selection.get("rowid0")+" "+selection.get("rowid1")+" "+selection.get("rowid2"));
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	
		    	// FIX
		        Toast.makeText(context, items[item], Toast.LENGTH_SHORT).show();
		    }
		});
		builder.show();
	}
}
