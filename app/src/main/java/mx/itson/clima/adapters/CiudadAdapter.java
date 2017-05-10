package mx.itson.clima.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.itson.clima.PronosticoActivity;
import mx.itson.clima.R;
import mx.itson.clima.entidades.Ciudad;
import mx.itson.clima.util.CiudadRender;

/**
 * Adaptador para la vista del pronostico por ciudad.
 * Created by Veronica on 5/3/2017.
 */

public class CiudadAdapter extends RecyclerView.Adapter<CiudadAdapter.ViewHolder> {
    private static List<Ciudad> ciudades;
    private static Context mContext;

    public CiudadAdapter(Context context, List<Ciudad> ciudades) {
        this.ciudades = ciudades;
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombreCiudad;
        public TextView temperatura;
        public ImageView ivClima;
        public TextView min;
        public TextView max;
        public TextView humedad;
        public TextView amanecer;
        public TextView puestaSol;
        public TextView viento;


    public ViewHolder(View itemView) {
            super(itemView);
            nombreCiudad = (TextView) itemView.findViewById(R.id.txtCiudad);
            temperatura = (TextView) itemView.findViewById(R.id.txtTemperatura);
            ivClima = (ImageView) itemView.findViewById(R.id.ivClima);
            min = (TextView) itemView.findViewById(R.id.mainMin);
            max = (TextView) itemView.findViewById(R.id.mainMax);
            humedad = (TextView) itemView.findViewById(R.id.mainHumedad);
            amanecer = (TextView) itemView.findViewById(R.id.mainAmanecer);
            puestaSol= (TextView) itemView.findViewById(R.id.mainSunset);
            viento = (TextView) itemView.findViewById(R.id.mainWind);
            itemView.setOnClickListener(this);

        }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            Ciudad c = ciudades.get(position);
            Bundle elementos = new Bundle();
            elementos.putString("nombre", c.getNombre().toString());
            Intent intent = new Intent(view.getContext(), PronosticoActivity.class);
            intent.putExtras(elementos);
            mContext.startActivity(intent);
        }
    }

    }

    @Override
    public CiudadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.ciudad_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ciudad c = ciudades.get(position);
        CiudadRender render = new CiudadRender(this.getContext());
        render.getClimaByCiudad(c.getNombre(), holder);

    }

    public void updateList(List<Ciudad> ciudades){
        this.ciudades = ciudades;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ciudades.size();
    }

}
