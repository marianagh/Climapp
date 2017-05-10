package mx.itson.clima.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import mx.itson.clima.R;
import mx.itson.clima.adapters.CiudadAdapter;
import mx.itson.clima.data.PronosticoActualClient;

/**
 * Created by Veronica on 5/5/2017.
 */

public class CiudadRender {

    public Context context;
    Handler handler;

    public CiudadRender(){}

    public CiudadRender(Context context){
        handler = new Handler();
        this.context = context;
    }

    private void actualizarDatos(final String ciudad, final CiudadAdapter.ViewHolder holder){
        new Thread(){
            public void run(){

                final JSONObject json = new PronosticoActualClient(context).getClima(ciudad);
                        if(json != null){
                            handler.post(new Runnable(){
                                public void run(){
                                    renderClima(json, holder);
                                }
                            });
                        } else {
                            handler.post(new Runnable(){
                                public void run(){
                                    Toast.makeText(context,
                                            context.getString(R.string.texto_error),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        }.start();
    }

    private void renderClima(JSONObject json, CiudadAdapter.ViewHolder holder){
        try {
            holder.nombreCiudad.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            float temp = Float.valueOf(main.getString("temp"));

             holder.temperatura.setText(String.valueOf(temp)+ context.getString(R.string.grados));
             holder.min.setText(context.getString(R.string.min)+" "+String.valueOf(new Float(main.getString("temp_min")))+context.getString(R.string.grados));
             holder.max.setText(context.getString(R.string.max)+" "+String.valueOf(new Float(main.getString("temp_max")))+context.getString(R.string.grados));
             holder.humedad.setText(context.getString(R.string.humedad)+" "+String.valueOf(new Float(main.getString("humidity")))+"%");
             holder.amanecer.setText(context.getString(R.string.amanecer)+" " +dateParse(json.getJSONObject("sys").getString("sunrise")));
             holder.puestaSol.setText(context.getString(R.string.puesta_sol)+" "+dateParse(json.getJSONObject("sys").getString("sunset")));
             holder.viento.setText(context.getString(R.string.viento)+" "+json.getJSONObject("wind").getString("speed") + "Km/h");

             setIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000, holder);

        }catch(Exception e){
            System.out.println(e.getMessage());
            Log.e("CiudadRender", "Uno o mas campos no fueron encontrados.");
        }
    }

    public void setIcon(int actualId, long sunrise, long sunset, CiudadAdapter.ViewHolder holder ){
        int id = actualId / 100;
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                holder.ivClima.setImageResource(R.drawable.clear);
            } else {
                holder.ivClima.setImageResource(R.drawable.clearnight);
            }
        } else {
            switch(id) {
                case 2 : holder.ivClima.setImageResource(R.drawable.weather_storm);
                    break;
                case 3 : holder.ivClima.setImageResource(R.drawable.drizzle_day);
                    break;
                case 7 : holder.ivClima.setImageResource(R.drawable.weather_fog);
                    break;
                case 8 : holder.ivClima.setImageResource(R.drawable.few_clouds);
                    break;
                case 6 : holder.ivClima.setImageResource(R.drawable.weather_snow);
                    break;
                case 5 : holder.ivClima.setImageResource(R.drawable.rain_day);
                    break;
            }
        }
    }

    public String dateParse(String timestamp){
        java.util.Date time= new java.util.Date(new Long(timestamp)*1000);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT-7"));
        return df.format(time);
    }

    public void getClimaByCiudad(String city, CiudadAdapter.ViewHolder holder){
        actualizarDatos(city, holder);
    }
}
