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
import com.examenparcial.entidad.Marca;
import com.examenparcial.R;

import java.util.List;

public class MarcaAdapter extends ArrayAdapter<Marca> {
    private Context context;
    private List<Marca> marcas;

    public MarcaAdapter(Context context, int resource, List<Marca> marcas) {
        super(context, resource, marcas);
        this.context = context;
        this.marcas = marcas;
    }

    @Override
    public View getView(final int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_list, parent, false);


        TextView txtId = (TextView) rowView.findViewById(R.id.txtListViewID);
        TextView txtNombre = (TextView) rowView.findViewById(R.id.txtListViewName);

        txtId.setText(String.format("#ID: %d", marcas.get(pos).getIdMarca()));
        txtNombre.setText(String.format("NOMBRE: %s", marcas.get(pos).getNombre()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MarcaActivity.class);
                intent.putExtra("var_id", String.valueOf(marcas.get(pos).getIdMarca()));
                intent.putExtra("var_nombre", marcas.get(pos).getNombre());
                intent.putExtra("var_estado", marcas.get(pos).getEstado());
                intent.putExtra("var_metodo", "VER");
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
