package mx.itson.clima.entidades;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.itson.clima.R;
import mx.itson.clima.data.ClimaDB;

/**
 * Created by Veronica on 5/3/2017.
 */

public class Ciudad {
    private int id;
    private  String nombre;
    private String code;
    Context context;
    Handler handler;

    public Ciudad (Context context){
        this.context = context;
    }

    public Ciudad (){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Obtiene la lista de ciudades registradas.
     * @return Lista de ciudades.
     */
    public List<Ciudad> obtenerLista(){
        List<Ciudad> ciudades = new ArrayList<Ciudad>();
        try {
            ClimaDB db = new ClimaDB(context,"ClimaDB", null, 1);
            SQLiteDatabase database = db.getReadableDatabase();
            String query = "SELECT id, nombre, code from Ciudad";
            Cursor cursor = database.rawQuery(query, null);
            while (cursor.moveToNext()){
                Ciudad c = new Ciudad();
                c.setId(Integer.parseInt(cursor.getString(0)));
                c.setNombre(cursor.getString(1) != null ? cursor.getString(1): "");
                c.setCode(cursor.getString(2)!= null ? cursor.getString(2):"");
                ciudades.add(c);
            }

            database.close();
        }catch (Exception ex){
            Toast.makeText(context, R.string.texto_error, Toast.LENGTH_LONG).show();
            System.out.println(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return ciudades;
    }

    public void agregar(String nombre){

       try{
           ClimaDB db = new ClimaDB(context, "ClimaDB", null,1 );
           SQLiteDatabase database = db.getWritableDatabase();
           SQLiteStatement statement = database.compileStatement("INSERT INTO Ciudad (nombre) values (?)");
           statement.bindString(1, nombre);
           statement.execute();
           db.close();
           Toast.makeText(context, R.string.texto_guardado, Toast.LENGTH_SHORT).show();
       } catch (Exception ex){
           Toast.makeText(context, R.string.texto_error, Toast.LENGTH_LONG).show();
           System.out.println(ex.getMessage());

       }
    }

    public String toString(){
        return this.getNombre();
    }



}
