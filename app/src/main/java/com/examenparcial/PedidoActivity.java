package com.examenparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.examenparcial.entidad.Cliente;
import com.examenparcial.entidad.Detalle;
import com.examenparcial.entidad.Pedido;
import com.examenparcial.entidad.Producto;
import com.examenparcial.entidad.Ubigeo;
import com.examenparcial.entidad.Usuario;
import com.examenparcial.servicio.ServicioRest;
import com.examenparcial.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoActivity extends AppCompatActivity {

    ServicioRest servicio;
    EditText edtUId, edtEntrega, edtLugar, edtCantidad;
    Spinner spnEstado;
    Button btnSave;
    TextView txtUId;
    final String metodo = "";


    Spinner spnDep, spnPro,spnDis, spnCli, spnProd;
    String selDep, selPro;
    int idUbigeo = -1;

    ArrayAdapter<String> adapterDep,adapterPro;
    ArrayAdapter<Ubigeo> adapterDis;
    ArrayAdapter<Cliente> adapterCli;
    ArrayAdapter<Producto> adapterProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        setTitle("CRUD de Marca");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtUId = (TextView) findViewById(R.id.txtIdRol);
        edtUId = (EditText) findViewById(R.id.edtIdRol);
        edtEntrega = (EditText) findViewById(R.id.edtFechaEntrega);
        edtLugar = (EditText) findViewById(R.id.edtLugarEntrega);
        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        spnEstado = (Spinner) findViewById(R.id.spnEstado);


        btnSave = (Button) findViewById(R.id.btnSave);

        servicio = ConnectionRest.getConnection().create(ServicioRest.class);
        /*Bundle extras = getIntent().getExtras();*/

        adapterDep =  new ArrayAdapter<String>(PedidoActivity.this,R.layout.support_simple_spinner_dropdown_item);
        adapterDep.add("[ Seleccione Departamento ]");
        spnDep =  findViewById(R.id.spnDepartamentos);
        spnDep.setAdapter(adapterDep);

        adapterPro =  new ArrayAdapter<String>(PedidoActivity.this,R.layout.support_simple_spinner_dropdown_item);
        adapterPro.add("[ Seleccione Provincia ]");
        spnPro =  findViewById(R.id.spnProvincias);
        spnPro.setAdapter(adapterPro);

        adapterDis =  new ArrayAdapter<Ubigeo>(PedidoActivity.this,R.layout.support_simple_spinner_dropdown_item);
        adapterDis.add(new Ubigeo(0,"","","[ Selecciona Distrito ]"));
        spnDis =  findViewById(R.id.spnDistritos);
        spnDis.setAdapter(adapterDis);

        adapterCli =  new ArrayAdapter<Cliente>(PedidoActivity.this,R.layout.support_simple_spinner_dropdown_item);
        adapterCli.add(new Cliente(0,"","[ Selecciona Cliente ]"));
        spnCli =  findViewById(R.id.spnClientes);
        spnCli.setAdapter(adapterCli);

        adapterProd =  new ArrayAdapter<Producto>(PedidoActivity.this,R.layout.support_simple_spinner_dropdown_item);
        adapterProd.add(new Producto(0,"[ Selecciona Producto ]",0.0));
        spnProd =  findViewById(R.id.spnProducto);
        spnProd.setAdapter(adapterProd);

        //servicio = ConnectionRest.getConnection().create(ServicioRest.class);
        cargaDepartamento();
        cargaCliente();
        cargaProducto();

        spnDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){ //Si no es el primer elemento
                    selDep = spnDep.getSelectedItem().toString();
                    cargaProvincia(selDep);
                }else{
                    adapterPro.clear();
                    adapterPro.add("[ Selecciona Provincia ]");
                    adapterPro.notifyDataSetChanged();
                    adapterDis.clear();
                    adapterDis.add(new Ubigeo(0,"","","[ Selecciona Distrito ]"));
                    adapterDis.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spnPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){ //Si no es el primer elemento
                    selPro = spnPro.getSelectedItem().toString();
                    cargaDistrito(selDep, selPro);
                }else{
                    adapterDis.clear();
                    adapterDis.add(new Ubigeo(0,"","","[ Selecciona Distrito ]"));
                    adapterDis.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spnDis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    idUbigeo = adapterDis.getItem(position).getIdUbigeo();
                    String mensaje = "Ubigeo : " + idUbigeo ;
                    //mostrarMensaje("Selección de Datos", mensaje);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Detalle> ds = new ArrayList<Detalle>();
                Pedido u = new Pedido();;
                u.setFechaEntrega(edtEntrega.getText().toString());
                u.setLugarEntrega(edtLugar.getText().toString());
                u.setEstado(spnEstado.getSelectedItem().toString());
                u.setUbigeo((Ubigeo) spnDis.getSelectedItem());
                u.setCliente((Cliente) spnCli.getSelectedItem());
                u.setUsuario(new Usuario(1));//se deberia obtener de session
                Producto p = (Producto) spnProd.getSelectedItem();
                Detalle d = new Detalle(p.getIdProducto(),p.getPrecio(), Integer.parseInt(edtCantidad.getText().toString()));
                ds.add(d);
                u.setDetalles(ds);

                //PENDIENTE ARREGLO DE DETALLE DE PEDIDO

                //if (metodo.equals("REGISTRAR")) {
                    mensaje("Se pulsó agregar");
                    add(u);
                //}

                Intent intent = new Intent(PedidoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    public void add(Pedido u) {
        Call<Pedido> call = servicio.agregaPedido(u);
        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if (response.isSuccessful()) {
                    mensaje("Registro exitoso");
                }
            }
            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
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


    /****/

    public void cargaDepartamento(){
        Call<List<String>> call = servicio.listaDepartamentos();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> lista = response.body();
                if(lista != null){
                    adapterDep.addAll(lista);
                    adapterDep.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }


    public void cargaProvincia(String dep){
        Log.e("INFO", "->"+  dep +"<-");
        Call<List<String>> call = servicio.listaProvincias(dep);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> lista = response.body();
                if(lista != null){
                    adapterPro.clear();
                    adapterPro.add("[ Selecciona Provincia ]");
                    adapterPro.addAll(lista);
                    adapterPro.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }


    public void cargaDistrito(String dep, String dis){
        Log.e("INFO", "->"+  dep +"<-");
        Log.e("INFO", "->"+  dis +"<-");
        Call<List<Ubigeo>> call = servicio.listaDistritos(dep, dis);
        call.enqueue(new Callback<List<Ubigeo>>() {
            @Override
            public void onResponse(Call<List<Ubigeo>> call, Response<List<Ubigeo>> response) {
                List<Ubigeo> lista = response.body();
                if(lista != null){
                    adapterDis.clear();
                    adapterDis.add(new Ubigeo(0,"","","[ Selecciona Distrito ]"));
                    adapterDis.addAll(lista);
                    adapterDis.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Ubigeo>> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }

    public void cargaCliente(){
        Call<List<Cliente>> call = servicio.listaCliente();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                List<Cliente> lista = response.body();
                if(lista != null){
                    adapterCli.clear();
                    adapterCli.add(new Cliente(0,"","[ Selecciona Cliente ]"));
                    adapterCli.addAll(lista);
                    adapterCli.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }

    public void cargaProducto(){
        Call<List<Producto>> call = servicio.listaProducto();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                List<Producto> lista = response.body();
                if(lista != null){
                    adapterProd.clear();
                    adapterProd.add(new Producto(0,"[ Selecciona Producto ]",0.0));
                    adapterProd.addAll(lista);
                    adapterProd.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }

    /*
    public void mostrarMensaje(String titulo, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setTitle(titulo);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }*/

}
