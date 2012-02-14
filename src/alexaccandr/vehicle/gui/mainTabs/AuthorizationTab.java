package alexaccandr.vehicle.gui.mainTabs;


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

/*
 * Страница авторизации клиента
 * После нажатия кнопки "сохранить" устройство подключается 
 * к веб системе, отправляет ей логин, пароль и компанию. 
 * Если данные верны придет положительный ответ, 
 * информация сохраняется и можно приступать к основной работе. 
 */
public class AuthorizationTab extends Activity{
	
	// кнопки
	Button saveButton;
	Button cancelButton;
	
	// поля
	EditText firstName;
	EditText secondName;
	EditText company;
	EditText email;
	EditText password;
	
	// ссылка на контекст
	Context context;
	
	// 
	private ProgressDialog pd;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.e("ON CONFIGURATION_CHANGED","...");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_auth_layout);
		// получить ссылку на текущий контекст
		context = this;
		//получить ссылки на кнопки
		//назначить слушателей
		saveButton = (Button)findViewById(R.auth.saveButton);
		cancelButton= (Button)findViewById(R.auth.cancelButton);
		saveButton.setOnClickListener(saveListener);
		cancelButton.setOnClickListener(cancelListener);		
		
		// получить ссылки на поля
		firstName = (EditText)findViewById(R.auth.first_name);
		secondName = (EditText)findViewById(R.auth.second_name);
		company = (EditText)findViewById(R.auth.company);
		email = (EditText)findViewById(R.auth.email);
		password = (EditText)findViewById(R.auth.password);
		
	} 
	
	// слушатель на кнопку "сохранить"
    OnClickListener saveListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			// отправить запрос на сервер
			sendAuthorizationRequestToServer();
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
		alertbox.setTitle("Отмена авторизации");
		alertbox.setMessage("Покинуть приложение?");
		
		// установить слушателя на "положительную" кнопку
		alertbox.setPositiveButton("Выйти",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// покинуть приложение
						System.exit(0);
					}
				});
		
		// установить слушателя на "отрицательную" кнопку
		alertbox.setNegativeButton("Отмена",
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
			sendRequest();
		}
	}
	
	/*
	 * отпарвить post запрос (авторизация) на сервер
	 */
	private void sendRequest() {
		pd = ProgressDialog.show(context, "Запрос авторизации",
				"Please wait...", true, false);
		Thread thread = new Thread(null, sendingThread);
		thread.start();
	}

	/*
	 * Вывод полосы прогресса
	 * отправка post запроса
	 * обработка ответа
	 */
	private Runnable sendingThread = new Runnable() {
		@Override
		public void run() {
			// FIX
			//ServerManager sm = new ServerManager();
			//sm.postRequest(context, reportHandler, 0, fields);
						
			// ЗАГЛУШКА
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message mess = new Message();
			Bundle bndl  = new Bundle();
			bndl.putInt("cmnd", 2);
			mess.setData(bndl);
			reportHandler.sendMessage(mess);
		}
	};

	/*
	 * управление полосой прогресса
	 */
	private Handler reportHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int position = msg.getData().getInt("cmnd");
			switch(position){
			// отправка запроса
			case 0:
				break;
			//обрабока запроса
			case 1:
				break;
			//отключить полосу прогресса
			case 2:
				toast("Успешная авторизация");
				pd.dismiss();
				break;
			case 3:
				toast("Ошибка авторизации");
				pd.dismiss();
			}
		}
	};
	

	/*
	 * @return null если хотя бы одно из полей не заполнено, иначе массив
	 * значений текстоовых полей
	 */
	String[] selectFields() {

		// получить значения тектовых полей
		String firstName = this.firstName.getText().toString();
		String secondName = this.secondName.getText().toString();
		String company = this.company.getText().toString();
		String email = this.email.getText().toString();
		String password = this.password.getText().toString();
		// если одно из полей пустое - запретить отправку и вывести сообщение
		// имя
		if (firstName.length() == 0) {
			toast("Ошибка. Поле 'Имя' не было введено");
			return null;
		}
		// фамилия
		if (secondName.length() == 0) {
			toast("Ошибка. Поле 'Фамилия' не было введено");
			return null;
		}
		// компания
		if (company.length() == 0) {
			toast("Ошибка. Поле 'Компания' не было введено");
			return null;
		}
		// email
		if (email.length() == 0) {
			toast("Ошибка. Поле 'E-mail' не было введено");
			return null;
		}
		// пароль
		if (password.length() == 0) {
			toast("Ошибка. Поле 'Пароль' не было введено");
			return null;
		}

		// вернуть значения полей
		return new String[] { firstName, secondName, company, email, password };
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
