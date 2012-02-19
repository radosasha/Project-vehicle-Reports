package alexaccandr.vehicle.photo;
import java.io.File;
import java.util.Currency;
import java.util.LinkedList;
import java.util.Map;

import alexaccandr.vehicle.camera.TakeAPhoto;
import alexaccandr.vehicle.gui.R;
import alexaccandr.vehicle.gui.addVehicleTabs.AddVehicle;
import alexaccandr.vehicle.tools.ApplicationMemory;
import alexaccandr.vehicle.tools.FileSystem;
import alexaccandr.vehicle.tools.Image;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater.Filter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;

public class PhotoEditor extends Activity  implements OnClickListener{
	private GestureDetector gesturedetector = null;
	private ViewSwitcher switcher;
	View.OnTouchListener gestureListener;
	ImageView im1 ;
	ImageView im2 ;
	TextView head ;
	int command;
	String photoFolder ;
	Context context;
	int cursorPosition = -1;
	public static LinkedList<LinkedList<Object>> allPhotosStructure;
	// полоса прогресса
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoeditor);
		context = this;
		im1 = (ImageView)findViewById(R.photoedit.photo1);
		im2 = (ImageView)findViewById(R.photoedit.photo2);
		head = (TextView)findViewById(R.photoedit.head);
		//
		gesturedetector = new GestureDetector(new MyGestureDetector());
		switcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
		switcher.setOnClickListener(PhotoEditor.this);
		switcher.setOnTouchListener(gestureListener);
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gesturedetector.onTouchEvent(event);
			}
		};	    
		
		// запускаем полосу прогресса
		pd = ProgressDialog.show(context, "Загружаем фото",
				"Загрузка из памяти телефона...", true, false);
		Thread thread = new Thread(null, runProgress);
		thread.start();
		
	}
	
	private Runnable runProgress = new Runnable() {
		@Override
		public void run() {
			// сообщение
			Message msg = new Message();
			Bundle bd  = new Bundle();
			try{
			// извлекаем команду из "намерения"
			// 0 - требуется загрузить "основные фотографии" для раздела "фото"
			// 1 - требуется загрузить фотографии из раздела "осмотр"
			command = getIntent().getExtras().getInt("cmnd");
			// извлекаем директорию
			photoFolder =  getIntent().getExtras().getString("dir");
			// получить список всех файлов по указанной директории
			String filesList [] = FileSystem.getFilesList(photoFolder);
			// загружаем фотки
			LinkedList<Bitmap> photos = downloadPhotos(filesList);
			// создать заголовки к файлам
			String headers[] = createHeaders(command, filesList);		
			// генерируем конечную структуру списка фотографий
			allPhotosStructure = getStruct(filesList,headers,photos);
			// формируем сообщение 
			
			if(allPhotosStructure.size() == 0){
				//Toast.makeText(context, "фотографии отуствуют", Toast.LENGTH_LONG).show();
				bd.putInt("cmnd", 0);
				//finish();
			}
			else bd.putInt("cmnd", 1);			
				//nextView();
			} catch (Exception e) {
				bd.putInt("cmnd", 2);
			}
			// отправить сообщение
			msg.setData(bd);
			reportHandler.sendMessage(msg);
		}
	};
	
	private Handler reportHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int command = msg.getData().getInt("cmnd");
			switch (command) {
			case 0:
				Toast.makeText(context, "фотографии отуствуют", Toast.LENGTH_LONG).show();
				break;
			case 1:
				nextView();
				break;
			case 2:
				Toast.makeText(context, "Ошибка при загрузке", Toast.LENGTH_LONG).show();
			}			
			pd.dismiss();
		}
	};
	

	// создает конечную структуру для создаржащую всю неоходимую информацию для редактирования фото
	// структуру одной записи { "имя файла", "заголовок фото", "декодированное фото"}
	private LinkedList<LinkedList<Object>> getStruct(String[] filesList, String[] headers, LinkedList<Bitmap> photos){
		LinkedList<LinkedList<Object>> finalStruct= new LinkedList<LinkedList<Object>>();
		if(filesList==null)return finalStruct;
		for(int i=0;i<filesList.length;i++){
			LinkedList<Object> record = new LinkedList<Object>();
			record.add(filesList[i]);
			record.add(headers[i].replace(".jpg", ""));
			record.add(photos.get(i));
			finalStruct.add(record);
		}
 		return finalStruct;
	}

	private String[] createHeaders(int command, String[] filesList) {
		switch(command){
		//"основные фотографии"
		case 0:
			/*// транспонируем расположение фоток в обратном порядке для более
			// удачного порядка следования файлов (фотки "без метки" в конец последовательности)
			// алгоритм пузырьковой сортировки
			for (int i = 1; i <filesList.length ; i++) {
				for (int y = 0; y < filesList.length-i; y++) {
					String common = filesList[y];
					filesList[y] = filesList[y + 1];
					filesList[y + 1] = common;
				}
			}*/
			return filesList;
		// "осмотр"
		case 1:
			// для фото из раздела "осмотр" последовательность не имеет значения
			return filesList;
		}
		return null;
	}

	// загрузка и декодирование фото
	/*
	 * @return null в случае неудачной загрузки, иначе, вернуть список всех фото
	 * FIX в случае ошибки при загрузке - почистить память если некоторые из фото удалось загрузить
	 */
	private LinkedList<Bitmap> downloadPhotos(String[] filesList) {
		LinkedList<Bitmap> photosList = new LinkedList<Bitmap>();
	// загрузить все файлы из списка
		if(filesList == null) return photosList;
	for(int i=0; i<filesList.length; i++){
		try{
			// загружаем и декодируем фото
			Bitmap photo = Image.decodeFile(new File(photoFolder+filesList[i]));
			photosList.add(photo);
			//  в случае неудачной загрузки почистить память от всех фото
			if(photo == null){
				Toast.makeText(context, "Ошибка при загрузке фото", Toast.LENGTH_LONG).show();
				clearMem(photosList);
			}			
		}
		catch(Exception e){
			// сюда мы врядли попаден, но на крайняк оставлю вот эту хрень снизу
			Toast.makeText(context, "Ошибка при загрузке фото", Toast.LENGTH_LONG).show();
			return null;
		}
	}
	// список загруженных и декодированных фоток
	return photosList;
	}


	// FIX
	private void clearMem(LinkedList<Bitmap> photosList) {
		// тут почистим память... попозже, наверное
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
//		return gesturedetector.onTouchEvent(ev);
	    if (gesturedetector != null) {
	        if (gesturedetector.onTouchEvent(ev))
	            return true;
	    }
	    return super.dispatchTouchEvent(ev);
	}
	
	// Предыдущая фотка
	private void nextView() {
		// Previous View
		if (cursorPosition >= allPhotosStructure.size() - 1)
			return;
		cursorPosition++;
		setNextImage(cursorPosition);
		switcher.setInAnimation(this, R.anim.in_animation1);
		switcher.setOutAnimation(this, R.anim.out_animation1);
		switcher.showPrevious();
	}

	// Следующая фотка 
	private void previousView() {
		// Next View
		if (cursorPosition <= 0  || cursorPosition == -1)
			return;
		cursorPosition--;
		setNextImage(cursorPosition);
		switcher.setInAnimation(this, R.anim.in_animation);
		switcher.setOutAnimation(this, R.anim.out_animation);
		switcher.showNext();
	}
	
	private void setNextImage(int cursorPos) {
		switch (cursorPos % 2) {
		case 0:
			im2.setImageBitmap((Bitmap) allPhotosStructure.get(cursorPos).get(2));
			break;
		case 1:
			im1.setImageBitmap((Bitmap) allPhotosStructure.get(cursorPos).get(2));
			break;
		}
		switch(command){
		case 0:
			head.setText((String)allPhotosStructure.get(cursorPosition).get(1)+"     "+(cursorPos+1)+"/"+allPhotosStructure.size());
		break;
		case 1:
			head.setText(getIntent().getExtras().getString("header")+"     "+(cursorPos+1)+"/"+allPhotosStructure.size());
		}
	}

	class MyGestureDetector extends SimpleOnGestureListener {
		// чувствительность на скокрость и интервал перелистывания фотки
		int SWIPE_MIN_VELOCITY = 100;
		int SWIPE_MIN_DISTANCE = 100;
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// текущая и конечная пизиции перетаскивания
			float ev1X = e1.getX();
			float ev2X = e2.getX();

			// разница между позициями
			final float xdistance = Math.abs(ev1X - ev2X);
			// получаем скорость курсора
			final float xvelocity = Math.abs(velocityX);

			// если мин скорость перетаскивания и мин интервах отвечают требованиям,
			// перелистнуть на другую фотку
			if ((xvelocity > SWIPE_MIN_VELOCITY)
					&& (xdistance > SWIPE_MIN_DISTANCE)) {
				if (ev1X > ev2X)// Switch Left
				{
					nextView();
				} else// Switch Right
				{
					previousView();
				}
			}
			return false;
		}
	 }

	@Override
	public void onClick(View v) {
		// Filter f = (Filter) v.getTag();
	       // FilterFullscreenActivity.show(this, input, f);
	}
	
	// перезаписать фото
	public void rewriteImage(View v){
		if(allPhotosStructure.size() == 0){
			Toast.makeText(context, "Перезапись невозможна, фотографии отсутсвуют.", Toast.LENGTH_SHORT).show();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Перезапись");
		builder.setMessage("Уверены что хотите перезаписать фотографию?");
		builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ничего не делать
			}
		});
		
		builder.show();
	}
	
	//удалить фото
	public void deleteImage(View v){
		if(allPhotosStructure.size() == 0){
			Toast.makeText(context, "Фотографии отсутствуют", Toast.LENGTH_SHORT).show();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Удаление");
		builder.setMessage("Уверены что хотите удалить фотографию?");
		builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//чистим форму с картинкой
				switch(cursorPosition % 2){
				case 0:
					Image.clearImages(im2);
					break;
				case 1:
					Image.clearImages(im1);					
				}
				// чистим буфер от картинки
				ApplicationMemory.eraseBitmapBuffer((Bitmap)allPhotosStructure.get(cursorPosition).get(2));
				// чистм с памяти телефона
				FileSystem.removeFile(photoFolder+allPhotosStructure.get(cursorPosition).get(0));
				// удаляем инфу о фото
				allPhotosStructure.remove(cursorPosition);
				//переключаемся на доступные фото
				// пробуем вправо
				Log.e(cursorPosition+"",allPhotosStructure.size()+"");
				//пробуем влево
				if(cursorPosition < allPhotosStructure.size())	setNextImage(cursorPosition);
				else {
					if(allPhotosStructure.size()>0)
					previousView();
					else{
						head.setText("Все фотографии удалены");
						Toast.makeText(context, "все фотографии удалены", Toast.LENGTH_SHORT).show();
					}
				}
				/*if((cursorPosition)>(allPhotosStructure.size()-1) & allPhotosStructure.size() != 0){
					previousView();
				}
				
				else{
					if((cursorPosition)<(allPhotosStructure.size()-1)){
						nextView();
					}
					else{
						head.setText("Все фотографии удалены");
						Toast.makeText(context, "все фотографии удалены", Toast.LENGTH_SHORT).show();
					}
				}*/
			}
		});
		builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		builder.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
	}
}
