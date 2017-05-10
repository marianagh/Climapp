package mx.itson.clima;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import mx.itson.clima.entidades.Ciudad;
import mx.itson.clima.adapters.CiudadAdapter;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Ciudad> ciudadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        Ciudad c = new Ciudad(this);
        ciudadList = c.obtenerLista();
        CiudadAdapter ciudadAdapter = new CiudadAdapter(this, ciudadList);
        mRecyclerView.setAdapter(ciudadAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


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

}
