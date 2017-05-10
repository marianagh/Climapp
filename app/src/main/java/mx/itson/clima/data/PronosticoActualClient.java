package mx.itson.clima.data;

import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mx.itson.clima.R;

/**
 * Created by Veronica on 5/3/2017.
 */

public class PronosticoActualClient extends AsyncTask <String, String, String> {

    static Context context;
    static JSONObject data;
    static JSONObject pronosticos;
    static ProgressDialog progress;
    static String ciudad;

    public PronosticoActualClient(Context c) {
        this.context = c;
    }


    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.setTitle("Cargando");
        progress.show();
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            data = getClima(ciudad);

    } catch (Exception e) {
        Log.i("Error", e.getMessage());
    }
            return null;
    }

    @Override
    protected void onPostExecute(String Void) {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    public JSONObject getClima(String ciudad){
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+ciudad+"&units=metric&APPID="+context.getString(R.string.api_key));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            Log.d("ApiCall",url.toString());

            while((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            data = new JSONObject(json.toString());

            if(data.getInt("cod") == 500) {
                System.out.println("Cancelled");
                data = null;
            }

        } catch (Exception e) {

            System.out.println("Exception "+ e.getMessage());

        }
        if(data!=null){
            Log.d("Pronostico",data.toString());
        }
        return data;
    }

    public JSONObject getClimaFuturo(String ciudad){
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q="+ciudad+"&units=metric&APPID="+context.getString(R.string.api_key));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            Log.d("ApiCall",url.toString());

            while((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            data = new JSONObject(json.toString());

            if(data.getInt("cod") == 500) {
                System.out.println("Cancelled");
                data = null;
            }

        } catch (Exception e) {

            System.out.println("Exception "+ e.getMessage());

        }
        if(data!=null){
            Log.d("Pronostico",data.toString());
        }
        return data;
    }
}
