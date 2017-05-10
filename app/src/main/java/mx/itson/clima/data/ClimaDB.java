package mx.itson.clima.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Veronica on 5/3/2017.
 */


public class ClimaDB extends SQLiteOpenHelper {
    /**
     * Crea la base de datos.
     * @param contextoActual Contexto en el cual se creara la base de datos.
     * @param nombre Nombre de la base de datos.
     * @param factory Constructor de la instancia para realizar las consultas.
     * @param version Indica la version de la base de datos.
     */
    public ClimaDB(Context contextoActual, String nombre, SQLiteDatabase.CursorFactory factory, int version){

        super(contextoActual, nombre, factory, version);
    }

    /**
     * Realiza operaciones al existir una version nueva de la base de datos.
     * @param baseDatos La base de datos actual.
     * @param versionAnterior Numero de version anterior.
     * @param versionNueva Numero de version nueva.
     */
    public void onUpgrade(SQLiteDatabase baseDatos, int versionAnterior, int versionNueva){

    }

    /**
     * Crea la tabla de contactos al crear la base de datos.
     * @param baseDatos Base de datos sobre la cual se creara la base de datos.
     */
    public void onCreate(SQLiteDatabase baseDatos){
        try {

            baseDatos.execSQL("CREATE TABLE Ciudad (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, code TEXT)");
            ContentValues cv = new ContentValues();
            baseDatos.execSQL("INSERT INTO Ciudad (nombre, code) values ('Empalme', '4006806')");
            baseDatos.execSQL("INSERT INTO Ciudad (nombre, code) values ('Hermosillo', '4004898')");
            baseDatos.execSQL("INSERT INTO Ciudad (nombre, code) values ('Guaymas', '4005143')");

        } catch (Exception ex){
            Log.i("Error", ex.getMessage());
        }
    }
}
