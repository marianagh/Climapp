package mx.itson.clima;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import mx.itson.clima.adapters.PronosticoAdapter;
import mx.itson.clima.entidades.Pronostico;

public class PronosticoActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PronosticoAdapter pronosticoAdapter;
    ArrayList<Pronostico> pronosticoList;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronostico);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvPronosticoFuturo);
        pronosticoList = new ArrayList<Pronostico>();
        String ciudad = this.getIntent().getExtras().getString("nombre").toString();
        pronosticoAdapter = new PronosticoAdapter(this, pronosticoList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(PronosticoActivity.this));
        mRecyclerView.setAdapter(pronosticoAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        setTitle(getString(R.string.pronostico) + " " + ciudad + getString(R.string.pronostico_futuro));
        new PronosticoClientTask(ciudad).execute(ciudad);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pronostico, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_regresar:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                PronosticoActivity.this.startActivity(i);
                return true;
            default:  return super.onOptionsItemSelected(item);
        }

    }

    public class PronosticoClientTask extends AsyncTask<String, String, String> {

         Context context;
         JSONObject pronosticos;
         String ciudad;
         PronosticoAdapter pa;

        public PronosticoClientTask (String ciudad){
            this.ciudad = ciudad;
        }


        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(PronosticoActivity.this);
            progress.setTitle("Cargando");
            progress.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                pronosticoList = getPronosticos(ciudad);
            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String Void) {
            super.onPostExecute("");
            if (progress.isShowing()) {
                progress.dismiss();
            }

            PronosticoActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    pronosticoAdapter.updateList(pronosticoList);
                    mRecyclerView.setItemAnimator(new SlideInUpAnimator());

                }
            });


        }

        public JSONObject getClimaFuturo(String ciudad){
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q="+ciudad+"&units=metric&APPID="+PronosticoActivity.this.getString(R.string.api_key));

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp = "";
                Log.d("ApiCall",url.toString());

                while((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                pronosticos = new JSONObject(json.toString());

                if (pronosticos != null){
                    return pronosticos;
                }

                if(pronosticos.getInt("cod") == 500) {
                    System.out.println("Cancelled");
                    pronosticos = null;
                }
            } catch (final Exception e) {

                System.out.println("Exception "+ e.getMessage());
                e.printStackTrace();
            }
            if(pronosticos!=null){
                Log.d("Pronostico",pronosticos.toString());
            }

            return pronosticos;
        }

        public ArrayList<Pronostico> getPronosticos(String ciudad){
            pronosticoList = new ArrayList<>();
            JSONObject json = getClimaFuturo(ciudad);

            try {
                // Lista de pronosticos dentro del JSON
                JSONArray list = json.getJSONArray("list");
                for(int i =0; i < list.length(); i++){
                    Pronostico p = new Pronostico();
                    //Pronostico actual
                    JSONObject data = list.getJSONObject(i);
                    //Main info del pronostico
                    JSONObject main = data.getJSONObject("main");
                    p.setMainTemp(main.getString("temp"));
                    p.setMaxTemp(main.getString("temp_max"));
                    p.setMinTemp(main.getString("temp_min"));
                    p.setHumedad(main.getString("humidity"));
                    //Weather info
                    JSONArray weather = data.getJSONArray("weather");
                    p.setDescripcion(weather.getJSONObject(0).getString("main"));
                    p.setId(weather.getJSONObject(0).getInt("id"));
                    //Wind info
                    JSONObject wind = data.getJSONObject("wind");
                    p.setViento(wind.getString("speed"));
                    //Fecha
                    p.setFecha(data.getString("dt_txt"));
                    pronosticoList.add(p);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return pronosticoList;
        }

    }

}
