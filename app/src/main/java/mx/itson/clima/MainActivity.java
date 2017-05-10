package mx.itson.clima;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import mx.itson.clima.entidades.Ciudad;
import mx.itson.clima.adapters.CiudadAdapter;


public class MainActivity extends AppCompatActivity {
    private static RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;
    private static List<Ciudad> ciudadList;
    private static Ciudad c;
    static CiudadAdapter ciudadAdapter;
    static ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
         c = new Ciudad(this);
        ciudadList = new ArrayList<Ciudad>();
        ciudadAdapter = new CiudadAdapter(this, ciudadList);
        mRecyclerView.setAdapter(ciudadAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new GetCiudades(MainActivity.this).execute();


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_registro:
                Intent i = new Intent(getApplicationContext(), RegistrarActivity.class);
                MainActivity.this.startActivity(i);
                return true;
            default:  return super.onOptionsItemSelected(item);
        }

    }

    public static class GetCiudades extends AsyncTask<Void, Void, Void>{

        public Context context;

        public GetCiudades (Context c){
            this.context = c;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ciudadList = new ArrayList<>(c.obtenerLista());
            ciudadAdapter.updateList(ciudadList);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(context);
            progress.setTitle(context.getString(R.string.cargando));
            progress.show();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progress.isShowing()) {
                progress.dismiss();
            }
        }


    }
    @Override
    protected void onDestroy() {
        try {
            if (progress != null && progress.isShowing()) {
                progress.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
