package alexaccandr.vehicle.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.util.Log;  

public class ServerManager {
	
	// конструктор
	public ServerManager(){		
	}
	
	/*
	 * @return null если произошла ошибка, иначе JSON/XML дынные ответа
	 */
	public  String postRequest(Context context, Handler reportHandler, int commandNumber, Object data) {
		// Создание новго Http клиента, инициализация полей
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter("http.protocol.version",	HttpVersion.HTTP_1_1);
		httpclient.getParams().setParameter("http.socket.timeout",		new Integer(5000));
		httpclient.getParams().setParameter("http.connection.timeout",	new Integer(5000));
		httpclient.getParams().setParameter("http.protocol.content-charset",	"UTF-8");
		HttpPost httppost = new HttpPost("адрес сервера");

		// post данные
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("data", (String)data));
		
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost,
					responseHandler);
			return response;
			
		} catch (Exception e) {
			System.out.println("Отсутствует интернет соединение");
			Log.e("ОШИБКА", "НЕТ СВЯЗИ С СЕРВЕРОМ");			
		}

		return "ERROR";
	}
    
}
