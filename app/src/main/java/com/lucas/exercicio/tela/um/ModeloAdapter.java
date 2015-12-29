package com.lucas.exercicio.tela.um;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucas.exercicio.R;
import com.squareup.picasso.Picasso;

/**
 * @author Lucas Campos
 *         12/28/15
 */
public class ModeloAdapter extends BaseAdapter{

    private final ListaModelo modelos;
    private final Activity context;

    public ModeloAdapter(ListaModelo modelos, Activity context) {
        this.modelos = modelos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modelos.getAvfms().size();
    }

    @Override
    public Object getItem(int position) {
        return modelos.getAvfms().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = convertView;

        if (linha == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            linha = inflater.inflate(R.layout.lista, parent, false);
        }

        Modelo first = (Modelo) modelos.getAvfms().get(position);

        ImageView imageView = (ImageView) linha.findViewById(R.id.imagem);
        Picasso.with(context).load("http://aviewfrommyseat.com/wallpaper/" + first.getImage()).resize(400, 200).into(imageView);

        TextView venue = (TextView) linha.findViewById(R.id.venue);
        venue.setText(first.getVenue());

        TextView note = (TextView) linha.findViewById(R.id.note);
        note.setText(first.getNote());

        TextView view = (TextView) linha.findViewById(R.id.view);
        view.setText(String.valueOf(first.getViews()));

        return linha;
    }
}
