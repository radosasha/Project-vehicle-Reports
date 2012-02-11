package alexaccandr.vehicle.gui.statereport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import alexaccandr.vehicle.gui.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MakePhotos extends Activity {
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
		setContentView(R.layout.make_photos);
		// ссылка на контекст
		context = this;
		wheelPosition = new Intent(context, WheelPosition.class);

		// получаем ссылку на заголовок
		head = (TextView) findViewById(R.makephoto.head);
		// присваиваем название		
		//head.setText(getIntent().getExtras().getString("head"));
		head.setText("Крыло заднее правое");
		// инициализация списка
		
		
		
		
		ListView lv = (ListView) findViewById(R.makephoto.list);
		String[] from = new String[] { "rowid0", "rowid1", "rowid2", "rowid3" };
		int[] to = new int[] { R.makephoto.errortext, R.makephoto.photo1, R.makephoto.photo2 ,R.makephoto.invis};
		// тестовые наборы
		fillMaps = new ArrayList<HashMap<String, Object>>();				
		HashMap<String, Object> textData = new HashMap<String, Object>();
		textData.put("rowid0", "Не ОК");
		textData.put("rowid1", R.drawable.add_new_not_choosed);
		textData.put("rowid2", R.drawable.transperent);	
		textData.put("rowid3", "Не ОК");
		
		fillMaps.add(textData);	
		
		// установим список на первый уровень иерархии
		adapter = new SimpleAdapter(this, fillMaps, R.layout.list_make_photos,
				from, to);
		lv.setAdapter(adapter);

		// обновить изменения в списке
		adapter.notifyDataSetChanged();

		// установить слушателя на элемент списка
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			
			}
		});
	}
	
	public void gogo(View v) {
		LinearLayout ll = (LinearLayout) v.getParent();
		TextView tv = (TextView)ll.findViewById(R.makephoto.invis);
		Log.e("text",tv.getText().toString());
		Log.e("Image ID",v.getId()+"");
		if(!(tv.getText().toString()).equals("Не ОК"))return;
		final Dialog dialog = new Dialog(MakePhotos.this);
		dialog.setContentView(R.layout.new_error);
		dialog.setTitle("Неисправность");
		dialog.setCancelable(true);
		final EditText et = (EditText) dialog.findViewById(R.error.et);
		Button btOkStop = (Button) dialog.findViewById(R.error.ok);
		btOkStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String, Object> textData = new HashMap<String, Object>();
				textData.put("rowid0", et.getText().toString());
				textData.put("rowid1", R.drawable.photo_not_choosed);
				textData.put("rowid2", R.drawable.trash_not_choosed);
				textData.put("rowid3", et.getText().toString());
				fillMaps.add(1, textData);
				Toast.makeText(context,
						"'Добавлено'" + et.getText().toString(),
						Toast.LENGTH_LONG).show();
				dialog.cancel();
			}
		});

		Button btCancelStop = (Button) dialog.findViewById(R.error.cancel);
		btCancelStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.show();
	}
	
}