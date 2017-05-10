package mx.itson.clima.entidades;

import android.content.Context;

/**
 * Created by Veronica on 5/7/2017.
 */

public class Pronostico {

    private Ciudad ciudad;
    private String fecha;
    private String viento;
    private String mainTemp;
    private String minTemp;
    private String maxTemp;
    private String humedad;
    private String descripcion;
    private String amanecer;
    private String puestaSol;
    private Context context;
    private int id;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Pronostico (Context context){
        this.context = context;
    }

    public Pronostico (){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getViento() {
        return viento;
    }

    public void setViento(String viento) {
        this.viento = viento;
    }

    public String getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(String mainTemp) {
        this.mainTemp = mainTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Pronostico(Ciudad ciudad){
        this.ciudad = ciudad;
    }


    public String getAmanecer() {
        return amanecer;
    }

    public void setAmanecer(String amanecer) {
        this.amanecer = amanecer;
    }

    public String getPuestaSol() {
        return puestaSol;
    }

    public void setPuestaSol(String puestaSol) {
        this.puestaSol = puestaSol;
    }



}
