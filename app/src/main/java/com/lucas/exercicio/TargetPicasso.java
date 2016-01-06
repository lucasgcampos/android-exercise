package com.lucas.exercicio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Lucas Campos
 *         1/6/16
 */
public class TargetPicasso implements Target {

    private final String filename;
    private final Context context;

    public TargetPicasso(String filename, Context context) {
        this.context = context;
        this.filename = filename;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        File folder = context.getFilesDir();

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File mediaFile = new File(folder.getPath() + File.pathSeparator + filename);

        try {
            mediaFile.createNewFile();

            FileOutputStream ostream = new FileOutputStream(mediaFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
            ostream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        return;
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        return;
    }
}
