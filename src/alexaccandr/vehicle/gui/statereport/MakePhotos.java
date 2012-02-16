package alexaccandr.vehicle.gui.statereport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import alexaccandr.vehicle.camera.TakeAPhoto;
import alexaccandr.vehicle.gui.R;
import alexaccandr.vehicle.photo.PhotoEditor;
import alexaccandr.vehicle.tools.ApplicationMemory;
import alexaccandr.vehicle.tools.FileSystem;
import alexaccandr.vehicle.tools.Image;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
	// Картинки
	public static ImageView imageFirst;
	public static ImageView imageSecond;
	public static Bitmap bt1 = null;
	public static Bitmap bt2 = null;
	// "намерение" перейти на страницу "пункты"
	Intent wheelPosition;
	
	// последняя выбранная неисправность
	public String lastFault = null;
	
	// полоса прогресса
	private ProgressDialog pd;

	// список фотографий в папке
	String[] photosList = null;
	// директория папки с фотографиями
	String directory = null;
	
	// 
	final int SET_CHANGES = 100;
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
		
		// получаем ссылки на картинки
		imageFirst = (ImageView)findViewById(R.image.first);
		imageSecond = (ImageView)findViewById(R.image.second);
		
		// инициализация списка		
		ListView lv = (ListView) findViewById(R.makephoto.list);
		String[] from = new String[] { "rowid0", "rowid1", "rowid2" };
		int[] to = new int[] { R.makephoto.errortext, R.makephoto.photo1, R.makephoto.photo2 };
		// тестовые наборы
		fillMaps = new ArrayList<HashMap<String, Object>>();				
		HashMap<String, Object> textData = new HashMap<String, Object>();
		textData.put("rowid0", "Не ОК");
		textData.put("rowid1", R.drawable.add_new_not_choosed);
		textData.put("rowid2", R.drawable.transperent);	
		fillMaps.add(textData);	
		
		// установим список на первый уровень иерархии
		adapter = new SimpleAdapter(this, fillMaps, R.layout.list_make_photos,
				from, to);
		lv.setAdapter(adapter);

		// обновить изменения в списке
		adapter.notifyDataSetChanged();

		// установить слушателя на элемент списка
		// отобразить фотографии по данной неисправности, если таковы были сделаны
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.gc();
				// извлекаем название неисправности
				Map<String, String> selection = (Map<String, String>) arg0.getItemAtPosition(arg2);
				String fault = selection.get("rowid0");
				// если был выбран первый пункт, ничего не делать
				if(fault.equals("Не ОК")) return;
				// получаем список всех фотографий
				directory = "/sdcard/CarMobile/Faults/"+fault;
				File dir = new File(directory);
				photosList = dir.list();
				// предворительно очищаем буфер приложения
				lastFault = fault;
				refreshImages();
			}
		});
	}
	
	/*
	 * очищаем от предыдущих картинок
	 * загружаем закртинки по указанному разделу
	 */
	void refreshImages(){
		ApplicationMemory.eraseBitmapBuffer(bt1);
		ApplicationMemory.eraseBitmapBuffer(bt2);
		System.gc();
		// чистим картинки
		clearImages();				
		// запускаем полосу прогресса
		pd = ProgressDialog.show(context, "Загружаем фото",
				"Загрузка из памяти телефона...", true, false);
		Thread thread = new Thread(null, runProgress);
		thread.start();
	}
	
	/*
	 * подгрузка картинок из памяти телефона или SD карты
	 */
	void loadImages(){
			switch (photosList.length) {
			case 0:
				
				break;
			case 1:
				bt1 = Image.decodeFile(new File(directory, photosList[0]));
				//imageFirst.setImageBitmap(bt1);
				break;
			case 2:	
				bt1 = Image.decodeFile(new File(directory, photosList[0]));
				bt2 = Image.decodeFile(new File(directory, photosList[1]));				
				//imageFirst.setImageBitmap(bt1);
				//imageSecond.setImageBitmap(bt2);
			}
	}

	private Runnable runProgress = new Runnable() {
		@Override
		public void run() {
			Message msg = new Message();
			Bundle bd = new Bundle();
			try {
				// зугрузить фото из памяти телефона
				loadImages();
				// отправить сообщение об удачной загрузке фото
				bd.putInt("msg", 0);
				msg.setData(bd);
				reportHandler.sendMessage(msg);

			} catch (Exception e) {
				// вывести сообщене об ошибке при загрузке
				e.printStackTrace();
				bd.putInt("msg", 1);
				msg.setData(bd);
				reportHandler.sendMessage(msg);
			}
		}
	};
	
	private Handler reportHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int command = msg.getData().getInt("msg");
			switch (command) {
			case 0:
				switch(photosList.length){
				// в
				case 0:
					Toast.makeText(context, "Фотографии отсутсвуют",
							Toast.LENGTH_SHORT).show();
					break;
				case 1:
					imageFirst.setImageBitmap(bt1);
					break;
				case 2:
					imageFirst.setImageBitmap(bt1);
					imageSecond.setImageBitmap(bt2);
				}
				// do nothing
				break;
			case 1:
				Toast.makeText(context,
						"Ошибка. Недостаточно памяти для отображения.",
						Toast.LENGTH_SHORT).show();
			}
			pd.dismiss();
		}
	};
	
	

	// убрать картинки с экрана
	void clearImages() {
		if (imageFirst != null)
			imageFirst.setImageBitmap(null);
		if (imageSecond != null)
			imageSecond.setImageBitmap(null);
	}
	

	/*
	 * слушатель на нажатей иконку "сделать фото"
	 */
	public void makePhoto(View v) {
		// получим ссылку на родительскую разметку (поднимемся на 3 уровня)
		LinearLayout parentLayout = (LinearLayout) v.getParent().getParent()
				.getParent();
		// извлекаем текст из поля "неисправность"
		TextView tv = (TextView) parentLayout
				.findViewById(R.makephoto.errortext);
		String fault = tv.getText().toString();
		Log.e("text", fault);

		// если нажата кнопка "добавить неисправность"
		if (fault.equals("Не ОК")) {
			// создаем диалоговое окно
			final Dialog dialog = new Dialog(MakePhotos.this);
			// разметка окна
			dialog.setContentView(R.layout.new_error);
			// заголовок
			dialog.setTitle("Неисправность");
			dialog.setCancelable(true);
			// текстовое окно для ввода названия неисравности
			final EditText et = (EditText) dialog.findViewById(R.error.et);
			// кнопка подтверждения добавления
			Button btOkStop = (Button) dialog.findViewById(R.error.ok);
			// слушатель на кнопку
			btOkStop.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// извлекаем имя введенной неисправности
					String faultName = et.getText().toString();
					// проверяем. существует ли неисправность с таким именем
					if(isItemExist(faultName) == 0 ){
						dialog.cancel();
						Toast.makeText(context, "Неисправность '"+faultName+"' уже существует в списке", Toast.LENGTH_LONG).show();
						return;
					}
					// пробуем создать директорию по названию неисправности
					File directory =new File("/sdcard/CarMobile/Faults/"+faultName.replace("/", ""));
					if(!directory.exists()){
						if(!directory.mkdirs()){
							Toast.makeText(context, "Неудачное название. Попробуйте исключить любые символы из названия", Toast.LENGTH_LONG).show();
							return;
						}
					}
					// заполнить разметку нового пункта в списке неисправностей
					HashMap<String, Object> textData = new HashMap<String, Object>();
					textData.put("rowid0", et.getText().toString());
					textData.put("rowid1", R.drawable.photo_not_choosed);
					textData.put("rowid2", R.drawable.trash_not_choosed);
					// добавляем в список
					fillMaps.add(1, textData);
					adapter.notifyDataSetChanged();
					// уведомить пользователя о добавлении нового пункта
					Toast.makeText(context,	"Добавлен пункт: '" + et.getText().toString()+"'",Toast.LENGTH_LONG).show();
					dialog.cancel();
				}
			});

			// кнопка "отмена"
			Button btCancelStop = (Button) dialog.findViewById(R.error.cancel);
			btCancelStop.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// выключаем диалоговое окно
					dialog.cancel();
				}
			});
			dialog.show();
		}
		// иначе, если была нажата кнопка "добавить новое фото"
		else{
			// проверяем можно ли добавить новое фото
			//(максимальное количество фотографий - 2 штуки)
			File saveDir = new File("/sdcard/CarMobile/Faults/"+fault); 
			/*
			// в дальнейшем директории будут содержать более сложный адрес,
			// включая название осмотра, сторону , название конкретного объекта и т.д.
			if (!saveDir.exists()) {
				// директория отсутвтует, включаем камеру
				startCamera("/sdcard/CarMobile/Faults/"+fault+"/");
			}
			//иначе, проверяем сколько сделано фото
			else{*/
			
				//если сделано более 2х фото, запрещаем съемку
			String list[] = saveDir.list();
				if(list.length >= 2){
					Toast.makeText(context, "Достигнут предел фотографий(2 фото) для данной неисправности", Toast.LENGTH_LONG).show();
					return;
				}
				// включаем камеру
				startCamera("/sdcard/CarMobile/Faults/"+fault+"/");
			//}
		}
	}
	
	private void startCamera(String directory) {
		Intent camera = new Intent(context,TakeAPhoto.class);
		// номер команды
		camera.putExtra("cmnd", 1);
		// директория для сохранения фото
		camera.putExtra("dir", directory);
		startActivity(camera);
	}

	/*
	 * метод проверяет, содержит ли список пункт с введенным именем
	 * @return true, если в списке неисправностей уже есть такое имя, иначе
	 * false
	 */
	int isItemExist(String name) {
		int count = 0;
		for (HashMap<String, Object> item : fillMaps) {
			if (item.get("rowid0").equals(name))
				return count;
			count++;
		}
		return count;
	}
	
	/*@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("Destroy","remove views");
		unbindDrawables(findViewById(R.makephotos.rootview));
	    System.gc();
	}
	
	private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
        view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
        ((ViewGroup) view).removeAllViews();
        }
    }*/
	
	/*
	 * слушатель на нажатей иконку "сделать фото"
	 */
	public void removePhoto(View v) {

		LinearLayout parentLayout = (LinearLayout) v.getParent().getParent()
				.getParent();
		// извлекаем текст из поля "неисправность"
		TextView tv = (TextView) parentLayout
				.findViewById(R.makephoto.errortext);
		final String fault = tv.getText().toString();
		Log.e("text", fault);
		if(fault.equals("Не ОК"))return;

		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
		alertbox.setTitle("Удалить");
		alertbox.setMessage("Вы уверены что хотите удалить неисправность '"
				+ fault + "' ?");
		alertbox.setPositiveButton("Да", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				int numb = isItemExist(fault);
				if (numb == 0) {
					Toast.makeText(context, "Ошибка при удалении из списка",
							Toast.LENGTH_LONG).show();
					return;
				}
				fillMaps.remove(numb);
				adapter.notifyDataSetChanged();
				if (fault.equals(lastFault)) {
					clearImages();
				}
				try {
					FileSystem.deleteFolder(new File(
							"/sdcard/CarMobile/Faults/" + fault + ""));
				} catch (Exception e) {
					Toast.makeText(context, "Ошибка при удалении файлов",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		alertbox.setNeutralButton("Отмена",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
		alertbox.show();
	}
	
	
	public void onImageClick(View v){
		Intent ite = new Intent(context,PhotoEditor.class);
		ite.putExtra("cmnd", 1);
		ite.putExtra("dir", directory+"/");
		ite.putExtra("header",lastFault);
		startActivityForResult(ite, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case SET_CHANGES:
			refreshImages();
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}