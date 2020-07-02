package com.examenparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import com.examenparcial.entidad.Marca;
import com.examenparcial.servicio.ServicioRest;
import com.examenparcial.util.ConnectionRest;

public class MarcaActivity extends AppCompatActivity {

    ServicioRest servicio;
    EditText edtUId, edtNombre;
    Spinner spnEstado;
    Button btnSave;
    Button btnDel;
    TextView txtUId;
    final String metodo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca);

        setTitle("CRUD de Marca");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtUId = (TextView) findViewById(R.id.txtIdRol);
        edtUId = (EditText) findViewById(R.id.edtIdRol);
        edtNombre = (EditText) findViewById(R.id.edtRolNombre);
        spnEstado = (Spinner) findViewById(R.id.spnRolEstado);
        btnSave = (Button) findViewById(R.id.btnRolSave);
        btnDel = (Button) findViewById(R.id.btnRolDel);

        servicio = ConnectionRest.getConnection().create(ServicioRest.class);
        Bundle extras = getIntent().getExtras();
        final String metodo = extras.getString("var_metodo");
        final String var_id = extras.getString("var_id");

        if (metodo.equals("VER")) {
            String var_nombre = extras.getString("var_nombre");
            String var_estado = extras.getString("var_estado");

            edtUId.setText(var_id);
            edtNombre.setText(var_nombre);
            selectValue(spnEstado, var_estado);
            edtUId.setFocusable(false);
        }else if (metodo.equals("REGISTRAR")) {
            txtUId.setVisibility(View.INVISIBLE);
            edtUId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Marca u = new Marca();
                u.setNombre(edtNombre.getText().toString());
                u.setEstado(spnEstado.getSelectedItem().toString());
                if (metodo.equals("VER")) {
                    u.setIdMarca(Integer.parseInt(var_id));
                    mensaje("Se pulsó  actualizar");
                    update(u);
                } else if (metodo.equals("REGISTRAR")) {
                    mensaje("Se pulsó agregar");
                    add(u);
                }

                Intent intent = new Intent(MarcaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje("Se pulsó eliminar");
                delete(Integer.parseInt(var_id));
                Intent intent = new Intent(MarcaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void add(Marca u) {
        Call<Marca> call = servicio.agregaMarca(u);
        call.enqueue(new Callback<Marca>() {
            @Override
            public void onResponse(Call<Marca> call, Response<Marca> response) {
                if (response.isSuccessful()) {
                    mensaje("Registro exitoso");
                }
            }
            @Override
            public void onFailure(Call<Marca> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void update(Marca u) {
        Call<Marca> call = servicio.actualizaMarca(u);
        call.enqueue(new Callback<Marca>() {
            @Override
            public void onResponse(Call<Marca> call, Response<Marca> response) {
                if (response.isSuccessful()) {
                    mensaje("Actualización exitosa");
                }
            }
            @Override
            public void onFailure(Call<Marca> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void delete(int id) {
        Call<Marca> call = servicio.eliminaMarca(id);
        call.enqueue(new Callback<Marca>() {
            @Override
            public void onResponse(Call<Marca> call, Response<Marca> response) {
                if (response.isSuccessful()) {
                    mensaje("Eliminación exitosa");
                }
            }
            @Override
            public void onFailure(Call<Marca> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void mensaje(String msg) {
        Toast toast1 = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast1.show();
    }

    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}
