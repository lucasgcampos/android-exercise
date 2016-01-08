package com.lucas.exercicio.tela.um;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    public static final int WIDTH_LAND = 650;
    public static final int HEIGHT_LAND = 200;
    public static final int WIDTH_PORTRAIT = 430;
    public static final int HEIGHT__PORTRAIT = 200;

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
        ViewHolder holder = new ViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Local itemSelecionado = modelos.getAvfms().get(position);

        ImageView imageView = (ImageView) holder.layout.findViewById(R.id.imagem);

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

        Bitmap foto = BitmapFactory.decodeFile(mediaFile.getPath());
        if (foto != null) {
            Bitmap reduzida = null;
            if (context.getResources().getBoolean(R.bool.isLand)) {
                reduzida = Bitmap.createScaledBitmap(foto, WIDTH_LAND, HEIGHT_LAND, true);
            } else {
                reduzida = Bitmap.createScaledBitmap(foto, WIDTH_PORTRAIT, HEIGHT__PORTRAIT, true);
            }
            imageView.setImageBitmap(reduzida);
        } else {
            Picasso.with(context)
                    .load(URL_WALLPAPER + itemSelecionado.getImage())
                    .fit()
                    .into(imageView);
        }

        TextView venue = (TextView) holder.layout.findViewById(R.id.venue);
        venue.setText(itemSelecionado.getVenue());

        TextView note = (TextView) holder.layout.findViewById(R.id.note);
        note.setText(itemSelecionado.getNote());

        TextView view = (TextView) holder.layout.findViewById(R.id.view);
        view.setText(String.valueOf(itemSelecionado.getViews()));
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
        public View layout;

        public ViewHolder(View tela) {
            super(tela);
            layout = tela;
        }
    }

}
