package mx.itson.clima.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.itson.clima.R;
import mx.itson.clima.entidades.Pronostico;
import mx.itson.clima.util.CiudadRender;

/**
 * Adaptardor para la vista de los pronosticos.
 * Created by Veronica on 5/8/2017.
 */

public class PronosticoAdapter extends RecyclerView.Adapter<PronosticoAdapter.ViewHolder>{
    public static ArrayList<Pronostico> pronosticos;
    private static Context context;

    public PronosticoAdapter(Context context, ArrayList<Pronostico> pronosticos) {
        this.pronosticos = pronosticos;
        this.context = context;
    }

    public void updateList(ArrayList<Pronostico> pronosticos){
        this.pronosticos = pronosticos;
        this.notifyDataSetChanged();
    }

    public PronosticoAdapter (){}

    private Context getContext() {
        return context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtCiudad;
        public TextView txtHumedad;
        public TextView txtTemperatura;
        public ImageView ivClima;
        public TextView txtFecha;
        public TextView txtViento;
        public TextView txtMinTemp;
        public TextView txtMaxTemp;
        public TextView txtDescripcion;


        public ViewHolder(View itemView) {
            super(itemView);
            txtFecha = (TextView) itemView.findViewById(R.id.textView);
            txtCiudad = (TextView) itemView.findViewById(R.id.txtCiudad);
            txtHumedad = (TextView) itemView.findViewById(R.id.txtHumedad);
            txtTemperatura = (TextView) itemView.findViewById(R.id.txtMain_temp);
            txtViento = (TextView) itemView.findViewById(R.id.txtViento);
            ivClima = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtMinTemp = (TextView) itemView.findViewById(R.id.txtMinTemp);
            txtMaxTemp = (TextView) itemView.findViewById(R.id.txtMaxTemp);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcion);

        }

        @Override
        public void onClick(View view) {

        }

    }

    @Override
    public PronosticoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View pronosticoView = inflater.inflate(R.layout.pronostico_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(pronosticoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PronosticoAdapter.ViewHolder holder, int position) {
        Pronostico p = pronosticos.get(position);
        holder.txtDescripcion.setText(p.getDescripcion());
        holder.txtFecha.setText(p.getFecha());
        holder.txtHumedad.setText(context.getString(R.string.humedad) + " " +p.getHumedad());
        holder.txtMaxTemp.setText(context.getString(R.string.max) + " " +p.getMaxTemp() + context.getString(R.string.grados));
        holder.txtMinTemp.setText(context.getString(R.string.min) +" " +p.getMinTemp() + context.getString(R.string.grados) );
        holder.txtTemperatura.setText(p.getMainTemp()+ context.getString(R.string.grados));
        holder.txtViento.setText(context.getString(R.string.viento )+" "+p.getViento() + " Km/h" );
        setIcon(p.getId(), holder);

    }

    @Override
    public int getItemCount() {
        return pronosticos.size();
    }


    public void setIcon(int actualId, PronosticoAdapter.ViewHolder holder ){
        int id = actualId / 100;
        if(actualId == 800){
                holder.ivClima.setImageResource(R.drawable.clear);

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
}
