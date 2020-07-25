package com.examenparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.examenparcial.adaptador.PedidoAdapter;
import com.examenparcial.entidad.Pedido;
import com.examenparcial.servicio.ServicioRest;
import com.examenparcial.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    Button btnLista;
    ListView listView;
    PedidoAdapter adaptadorListView;
    ServicioRest servicio;
    List<Pedido> list = new ArrayList<Pedido>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Examen Parcial Cibertec");

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnLista = (Button) findViewById(R.id.btnLista);

        // Al adaptador se le pasa la data y el diseño
        listView = (ListView) findViewById(R.id.listView);

        // Se crea la conexion al servicio
        servicio = ConnectionRest.getConnection().create(ServicioRest.class);

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje("Se pulsó el listado");
                listData();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje("Se pulsó el agregar");
                Intent intent = new Intent(MainActivity.this, PedidoActivity.class);
                intent.putExtra("var_metodo", "REGISTRAR");
                startActivity(intent);
            }
        });

        //Para cargar los datos al inicio
        listData();
    }


    public void listData(){
        Call<List<Pedido>> call = servicio.listaPedido();
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(response.isSuccessful()){
                    //Aqui es donde obtiene la data y se coloca en el list
                    mensaje("Listado exitoso");
                    list = response.body();
                    adaptadorListView = new PedidoAdapter(MainActivity.this, R.layout.activity_list, list);
                    listView.setAdapter(adaptadorListView);
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    void mensaje(String msg){
        Toast toast1 =  Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG);
        toast1.show();
    }
}
