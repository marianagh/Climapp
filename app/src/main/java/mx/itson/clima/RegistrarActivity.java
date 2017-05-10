package mx.itson.clima;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mx.itson.clima.entidades.Ciudad;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener {

    static TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textInputLayout = (TextInputLayout) findViewById(R.id.txtLayout);
        setContentView(R.layout.activity_registrar);
        Button btnSave = (Button) findViewById(R.id.btnGuardar);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Ciudad c = new Ciudad(this);
        String nombre = ((EditText) findViewById(R.id.txtNombre)).getText().toString();
        if (!isEmpty(nombre)){
            if (isPatternValid(nombre)){
                textInputLayout.setErrorEnabled(false);
                c.agregar(nombre);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                RegistrarActivity.this.startActivity(i);
            } else {
                textInputLayout.setError(RegistrarActivity.this.getString(R.string.error_number));
            }

        }else {
            textInputLayout.setError(this.getString(R.string.error_empty));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.registrar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_regresar:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                RegistrarActivity.this.startActivity(i);
                return true;
            default:  return super.onOptionsItemSelected(item);
        }

    }

    public boolean isEmpty(String ciudad){

        if (ciudad.equals("")){
            return true;
        } else {
            return false;
        }
    }

    public boolean isPatternValid(String ciudad){
        String pattern = "[a-zA-Z]";
        if ( pattern.matches(ciudad)){
            return true;} else {
            return false;
        }
    }

}
