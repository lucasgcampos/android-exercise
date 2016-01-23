package com.lucas.exercicio.tela.um;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucas.exercicio.R;
import com.lucas.exercicio.TargetPicasso;
import com.lucas.exercicio.tela.dois.DescricaoActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * @author Lucas Campos
 *         12/28/15
 */
public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolder> {

    public static final String VENUE = "venue";
    public static final String URL_WALLPAPER = "http://aviewfrommyseat.com/wallpaper/";

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista, parent, false);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView recycler = (RecyclerView) context.findViewById(R.id.lista);
                int itemPosition = recycler.getChildPosition(layout);

                Intent intent = new Intent(context, DescricaoActivity.class);
                intent.putExtra(VENUE, modelos.getAvfms().get(itemPosition).getVenue());
                context.startActivity(intent);
            }
        });

        TextView venue = (TextView) layout.findViewById(R.id.venue);
        TextView note = (TextView) layout.findViewById(R.id.note);
        TextView view = (TextView) layout.findViewById(R.id.view);
        ImageView imageView = (ImageView) layout.findViewById(R.id.imagem);

        return new ViewHolder(layout, venue, note, view, imageView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Local itemSelecionado = modelos.getAvfms().get(position);

        File folder = context.getFilesDir();

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File mediaFile = new File(folder.getPath() + File.pathSeparator + itemSelecionado.getImage());
        if (!mediaFile.exists()) {
            Picasso.with(context)
                    .load(URL_WALLPAPER + itemSelecionado.getImage())
                    .into(new TargetPicasso(itemSelecionado.getImage(), context));
        }

        Picasso.with(context).load(mediaFile).fit().centerCrop().into(holder.imageView);

        holder.venue.setText(itemSelecionado.getVenue());
        holder.note.setText(itemSelecionado.getNote());
        holder.view.setText(String.valueOf(itemSelecionado.getViews()));
    }

    @Override
    public int getItemCount() {
        return modelos.getAvfms().size();
    }

    private final ListaLocal modelos;
    private final Activity context;

    public LocalAdapter(ListaLocal modelos, Activity context) {
        this.modelos = modelos;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView venue;
        private TextView note;
        private TextView view;
        private ImageView imageView;

        public ViewHolder(View layout, TextView venue, TextView note, TextView view, ImageView imageView) {
            super(layout);
            this.view = view;
            this.note = note;
            this.venue = venue;
            this.imageView = imageView;
        }

    }

}
