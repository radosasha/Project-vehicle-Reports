package alexaccandr.vehicle.gui.addVehicleTabs;


import java.util.LinkedList;



import alexaccandr.vehicle.gui.R;
import alexaccandr.vehicle.server.ServerManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DataTab extends Activity{
	
	// кнопки
	Button saveButton;
	Button cancelButton;
	
	// поля
	EditText vin;
	EditText marka;
	EditText model;
	EditText odometer;
	EditText comments;
	
	// ссылка на контекст
	Context context;
	
	// 
	private ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_data_layout);
		// получить ссылку на текущий контекст
		context = this;
		//получить ссылки на кнопки
		//назначить слушателей
		saveButton = (Button)findViewById(R.data.saveButton);
		cancelButton= (Button)findViewById(R.data.cancelButton);
		saveButton.setOnClickListener(saveListener);
		cancelButton.setOnClickListener(cancelListener);		
		
		// получить ссылки на поля
		vin = (EditText)findViewById(R.data.vin);
		marka = (EditText)findViewById(R.data.marka);
		model = (EditText)findViewById(R.data.model);
		odometer = (EditText)findViewById(R.data.odometer);
		comments = (EditText)findViewById(R.data.comments);
		
	} 
	
	// слушатель на кнопку "сохранить"
    OnClickListener saveListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			// сохранить в БД
			selectFields();
		}
	};

	// слушатель на кнопку "отмена"
	OnClickListener cancelListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// вывести диалоговое окно "покинуть приложение"
			showExitConfirmMessage();
		}
	};
	
	/*
	 * вывод диалогового окна
	 * подтверждение отмены авториазции
	 * и выхода их приложения
	 */
	void showExitConfirmMessage(){
		
		// инициализация диалогового окна
		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
		alertbox.setTitle("Отменить добавление?");
		
		// установить слушателя на "положительную" кнопку
		alertbox.setPositiveButton("Да",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// покинуть приложение
						System.exit(0);
					}
				});
		
		// установить слушателя на "отрицательную" кнопку
		alertbox.setNegativeButton("Нет",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// ничего не делать, свернуть диалоговое окно
					}
				});
		alertbox.show();
	}
	
	/*
	 * Запрос авторизации на сервер
	 */
	String [] fields = null;
	void sendAuthorizationRequestToServer(){
		// если поля введены корректно, отпарвить post запрос на сервер
		fields = selectFields();
		if(fields != null){
			//sendRequest();
		}
	}
	
	/*
	 * @return null если хотя бы одно из полей не заполнено, иначе массив
	 * значений текстоовых полей
	 */
	String[] selectFields() {

		// получить значения тектовых полей
		String vin = this.vin.getText().toString();
		String marka = this.marka.getText().toString();
		String model = this.model.getText().toString();
		String odometer = this.odometer.getText().toString();
		String comments = this.comments.getText().toString();
		// если одно из полей пустое - запретить отправку и вывести сообщение
		// имя
		if (vin.length() == 0) {
			toast("Ошибка. Поле 'VIM' не было введено");
			return null;
		}
		// фамилия
		if (marka.length() == 0) {
			toast("Ошибка. Поле 'Марка' не было введено");
			return null;
		}
		// компания
		if (model.length() == 0) {
			toast("Ошибка. Поле 'Модель' не было введено");
			return null;
		}
		// email
		if (odometer.length() == 0) {
			toast("Ошибка. Поле 'Пробег' не было введено");
			return null;
		}
		

		// вернуть значения полей
		return new String[] { vin, marka, model, odometer, comments};
	}
	
	// вывод сообщения пользователю
	void toast(String msg){
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	// открыть соединение с базой двнных
	void dbOpen() {
	}

	// закрыть соединение с базой данных
	void dbClose() {
	}
	}
