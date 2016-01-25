package com.lucas.exercicio;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Lucas Campos
 *         1/25/16
 */
public class CustomImageView extends ImageView {

    public CustomImageView(Context context) {
        super(context);
    }

    public int getAltura() {
        return getWidth() / 16 * 9;
    }

    public int getLargura() {
        return getHeight() * 4 / 3;
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
