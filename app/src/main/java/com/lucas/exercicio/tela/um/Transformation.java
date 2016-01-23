package com.lucas.exercicio.tela.um;

import android.graphics.Bitmap;

/**
 * @author Lucas Campos
 *         1/23/16
 */
public class Transformation implements com.squareup.picasso.Transformation {

    private LocalAdapter.ViewHolder holder;

    public Transformation(LocalAdapter.ViewHolder holder) {
        this.holder = holder;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = this.holder.getImageView().getWidth();

        Bitmap result = Bitmap.createScaledBitmap(source, width, width * 9 / 16, false);
        if (result != source) {
            source.recycle();
        }

        return result;
    }

    @Override
    public String key() {
        return "transformation" + " desiredWidth";
    }
}
