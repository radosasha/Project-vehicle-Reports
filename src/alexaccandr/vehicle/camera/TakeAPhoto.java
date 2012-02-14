package alexaccandr.vehicle.camera;

import alexaccandr.vehicle.gui.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

import android.hardware.Camera;
import android.hardware.Camera.Size;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class TakeAPhoto extends Activity implements SurfaceHolder.Callback,
		View.OnClickListener, Camera.PictureCallback, Camera.PreviewCallback,
		Camera.AutoFocusCallback {
	Context context;
	// переменные для работы с камерой
	private Camera camera;
	private SurfaceHolder surfaceHolder;
	private SurfaceView preview;
	// кнопка для "сделать сдимок"
	private Button shotBtn;
	private Button saveBtn;
	private Button cancelBtn;

	// доступные метки
	LinkedList<HashMap<String, Integer>> tags;
	HashMap<String, Integer> tagss;
	String[] allTags;
	
	// номер комманды 
	// 0 - камера фотографирует в раздел "Фото"
	// 1 - камера фотографирует в раздел "Внутренний/Внешний осмотр"
	int command;
	// директория для сохранения фото
	String dir;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ссылка на контекст
		context = this;

		// принудительно установить ориентацию LANDSCAPE
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// сделать картинку полноэкранной
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// уберем заголовок
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// установить разметку
		setContentView(R.layout.camera_layout);

		// инициализация кнопки
		shotBtn = (Button) findViewById(R.camera.takeaphoto);
		shotBtn.setOnClickListener(this);

		saveBtn = (Button) findViewById(R.camera.save);
		saveBtn.setOnClickListener(this);

		cancelBtn = (Button) findViewById(R.camera.cancel);
		cancelBtn.setOnClickListener(this);

		// сделать видимой только кнопку для создания фотографий
		buttonsVisible(1);

		// извлекаем номер команды из намерения
		Intent ite = getIntent();
		command = ite.getExtras().getInt("cmnd");
		// извлекаем директорию для сохнанения фото
		dir = ite.getExtras().getString("dir");
		
		// инициализируем поле с метками 
		if(command == 0) inic();
		
	}

	// при возвращении в окно - включить камеру
	@Override
	protected void onResume() {
		super.onResume();
		camera = Camera.open();
		// получить ссылку на surface камеры
		preview = (SurfaceView) findViewById(R.id.SurfaceView01);

		// получаем доступ к предпросмотру камеры
		surfaceHolder = preview.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// если вышли из контекста приложения - освобождаем камеру
	// чтобы другие приложения могли беспрепятственно ей воспользоваться
	@Override
	protected void onPause() {
		super.onPause();
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}

	// не использую
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		try {
			camera.setPreviewDisplay(holder);
			camera.setPreviewCallback(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		camera.startPreview();
	}

	// слушатель на создание камеры
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		

	/*	// корректируем размеры страницы в соответсвии с параметрами камеры
		// нашего устройства (так и быть, Вашего)
		Size previewSize = camera.getParameters().getPreviewSize();
		float aspect = (float) previewSize.width / previewSize.height;

		int previewSurfaceWidth = preview.getWidth(); // ширина
		int previewSurfaceHeight = preview.getHeight(); // высота

		LayoutParams lp = preview.getLayoutParams();

		// корректируем размер отображаемого preview, чтобы не было искажений
		if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
			// портретный вид
			camera.setDisplayOrientation(90);
			lp.height = previewSurfaceHeight;
			lp.width = (int) (previewSurfaceHeight / aspect);
			;
		} else {
			// ландшафтный
			camera.setDisplayOrientation(0);
			lp.width = previewSurfaceWidth;
			lp.height = (int) (previewSurfaceWidth / aspect);
		}

		// установить разметку
		preview.setLayoutParams(lp);*/
		// запустить камеру окда?
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	// слушатель на нажатие кнопки
	// количество сделанный фото в разделе "осмотр"
	int count;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		// сделать фото
		case R.camera.takeaphoto:
			switch (command) {
			// для раздела "фото"
			case 0:
				if (tags.size() == 0) {
					Toast.makeText(context, "Все метки заполнены", Toast.LENGTH_SHORT).show();
					break;
				}
				camera.autoFocus(this);
				break;
			// для раздела "осмотр"
			case 1:
				// если уже сделано 2 фото, запретим дальнейшую съемку
				File directory = new File(dir);
				count = directory.list().length;
				if(count >= 2){
					Toast.makeText(context, "Достигнут предел фотографий(2 фото) для данной неисправности", Toast.LENGTH_LONG).show();
					break;
				}
				// сделано менее 2 фото, разрешаем съемку
				camera.autoFocus(this);
				break;
			}
			break;
			
		// сохранить
		case R.camera.save:
			// вызвать предварительное меню для сохранения
			savePic();
			// выставить кнопки по умолчанию
			returnOriginal();
			break;
			
		// отмена
		case R.camera.cancel:
			// выставить кнопки по умолчанию
			returnOriginal();
		}
	}

	// метод устанавливает камеру и кнопки в исходное состояние
	void returnOriginal() {
		// запустить предпросмотр
		paramCamera.startPreview();
		// установить кнопку "сделать фото" видимой
		buttonsVisible(1);
	}

	// сделанное фото
	byte[] paramArrayOfByte;
	// ссылка на текущий контекст камеры
	Camera paramCamera;

	@Override
	public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera) {
		this.paramArrayOfByte = paramArrayOfByte;
		this.paramCamera = paramCamera;
		// сделать кнопки "Сохранить" и "отмена" видимыми
		buttonsVisible(0);
		// после того, как снимок сделан, показ превью отключается. необходимо
		// включить его
		// paramCamera.startPreview();
	}

	// метод для сохранения фото по указанной директории
	void savePic() {
		// в соответвии с командой будут предоставлены различные инструкции		
		switch(command){
		// инструкции по сохранению в раздел "Фото"
		case 0:
			createTypeDialog();		
			break;
		// инструкции по сохранению в раздел "Осмотр"
		case 1:
			savePictureToDir("Photo"+(new Random().nextInt(1000000)));
		}
	}

	// меняет видимость кнопок "сделать фото", "сохнанить", "отмена"
	void buttonsVisible(int num) {
		switch (num) {
		// видимы кнопку "сохранить" и "отмена"
		case 0:
			shotBtn.setVisibility(4);
			shotBtn.setText("");
			cancelBtn.setVisibility(0);
			saveBtn.setVisibility(0);
			break;

		// видима только кнопка "сделать фото"
		case 1:
			shotBtn.setText("Сделать фото");
			shotBtn.setVisibility(0);
			cancelBtn.setVisibility(4);
			saveBtn.setVisibility(4);
		}
	}

	// сфокусировать и сделать снимок
	@Override
	public void onAutoFocus(boolean paramBoolean, Camera paramCamera) {
		if (paramBoolean) {
			// если удалось сфокусироваться, делаем снимок
			// while(shotBtn.isPressed()){}
			paramCamera.takePicture(null, null, null, this);
		}
	}

	@Override
	public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera) {
		// здесь можно обрабатывать изображение, показываемое в preview

	}

	// метод вызывает диалоговое окно со списком доступных меток
	// по выбору одной из меток, удаляем её из списка доступных в соответсвии со значением счетчика
	// получаем название фото, сохраняем её, либо уведомляем пользователя о лимите фотографий если все метки уже заняты
	void createTypeDialog() {

		// получить доступные метки
		final String [] items = getItems();
		// инициализация списка
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// собрать заголовок
		builder.setTitle("Добавить метку");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Entry<String,Integer> es =tags.get(item).entrySet().iterator().next();
				// получаем значение счетчика
				int count = es.getValue();
				// если значение счетчика достигает нуля, удаляем метку из списка доступных
				// сохраняем фотографию 
				if(--count == 0){
					//при удачном сохранении фотографии удаляем метку
					// название фотографии состоит из названия метки + счетчик
					if(savePictureToDir(es.getKey()))tags.remove(item);					
				}
				else{
					//(для "без меток") иначе уменьшим счетчик  и сохраним фотографию
					if(savePictureToDir(es.getKey()+count))	es.setValue(count);
				}
			}
			// FIX
			// Toast.makeText(context, items[item], Toast.LENGTH_SHORT).show();
			// Intent cameraIntent = new
			// Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			// context.startActivity(cameraIntent);
		});
		builder.show();
	}
	
	// созраняем фото по указанной директории
	boolean savePictureToDir(String fileName) {
		try {
			// создаем директорию если отсутсвует
			File saveDir = new File(dir);
			
			if (!saveDir.exists()) {
				// тестовая переменная
				boolean res = saveDir.mkdirs();
				Log.e("Директория ".concat(dir),res+"");
			}
			// конечная директория файла
			// имя файла - System.currentTimeMillis()
			//FileOutputStream os = new FileOutputStream(String.format(
					//dir+"%d.jpg", System.currentTimeMillis()));
			String fullDir = dir+(fileName+".jpg").replace(" ", "_");
			FileOutputStream os = new FileOutputStream(fullDir);			
			os.write(paramArrayOfByte);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			// FIX
			// обработка нехватки память на SD карте
			Toast.makeText(context, "Ошибка при сохранении фотографии", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	// получить массив доступных меток
	private String[] getItems() {
		String items[] = new String[tags.size()];
		for (int i = 0; i < tags.size(); i++) {
			items[i] = tags.get(i)
					.entrySet()
					.iterator()
					.next()
					.getKey();
		}
		return items;
	}
	
	// инициализация
	void inic() {
		// доступные метки
		// параметр String - наименования меток
		// параметр Integer - счетчик использованных меток
		HashMap<String, Integer> t1 = new HashMap<String, Integer>();
		t1.put("Вид спереди", 1);
		HashMap<String, Integer> t2 = new HashMap<String, Integer>();
		t2.put("Вид сзади", 1);
		HashMap<String, Integer> t3 = new HashMap<String, Integer>();
		t3.put("Вид салона ч.з пассажирскую дверь", 1);
		HashMap<String, Integer> t4 = new HashMap<String, Integer>();
		t4.put("Без метки", 3);
		tags = new LinkedList<HashMap<String, Integer>>();
		tags.add(t1);
		tags.add(t2);
		tags.add(t3);
		tags.add(t4);
	}
}