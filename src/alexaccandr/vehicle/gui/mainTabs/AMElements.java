package alexaccandr.vehicle.gui.mainTabs;
import java.util.Map;

import alexaccandr.vehicle.camera.TakeAPhoto;
import alexaccandr.vehicle.gui.addVehicleTabs.AddVehicle;
import alexaccandr.vehicle.gui.addVehicleTabs.StateReportTab;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Toast;

/*
 * Класс содержит общие элементы пользовательского интерфейса
 * для станиц "Все a/м" и "Новые a/м"
 */
public class AMElements extends Activity{
	// контекст вызывающего класса
	Context context;
	// намерение "добавить фото
	Intent photos;
	// намерение "добавить отчет о состоянии"
	Intent stateRep;
	
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
		    	switch(item){
		    	// добавить отчет о состоянии
		    	case 0:		    		
		    		stateRep = new Intent(context, AddVehicle.class);
		    		// передать номер таба
		    		stateRep.putExtra("curtab", 1);
		    		context.startActivity(stateRep);
		    		break;
		    	// добавить фотографии
		    	case 1:
		    		photos = new Intent(context, TakeAPhoto.class);
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
}
