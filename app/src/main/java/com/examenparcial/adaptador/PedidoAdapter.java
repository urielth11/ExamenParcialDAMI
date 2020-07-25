package com.examenparcial.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.examenparcial.MarcaActivity;
import com.examenparcial.R;
import com.examenparcial.entidad.Marca;
import com.examenparcial.entidad.Pedido;

import java.util.List;

public class PedidoAdapter extends ArrayAdapter<Pedido> {
    private Context context;
    private List<Pedido> pedidos;

    public PedidoAdapter(Context context, int resource, List<Pedido> pedidos) {
        super(context, resource, pedidos);
        this.context = context;
        this.pedidos = pedidos;
    }

    @Override
    public View getView(final int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_list, parent, false);


        TextView txtId = (TextView) rowView.findViewById(R.id.txtListViewID);
        TextView txtNombre = (TextView) rowView.findViewById(R.id.txtListViewName);

        txtId.setText(String.format("#ID: %d", pedidos.get(pos).getIdPedido()));
        txtNombre.setText(String.format("FECHA REGISTRO: %s", pedidos.get(pos).getFechaRegistro()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MarcaActivity.class);
                intent.putExtra("var_id", String.valueOf(pedidos.get(pos).getIdPedido()));
                intent.putExtra("var_nombre", String.valueOf(pedidos.get(pos).getFechaRegistro()));
                intent.putExtra("var_estado", String.valueOf(pedidos.get(pos).getFechaEntrega()));
                intent.putExtra("var_metodo", "VER");
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
