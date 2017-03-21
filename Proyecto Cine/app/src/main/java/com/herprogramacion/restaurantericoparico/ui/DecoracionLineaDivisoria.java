package com.herprogramacion.restaurantericoparico.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.herprogramacion.restaurantericoparico.R;

/**
 * ItemDecoration personalizado para dibujar la linea drawable/linea_divisoria.xml en los
 * elementos de un recycler view
 */
public class DecoracionLineaDivisoria extends RecyclerView.ItemDecoration {
    private Drawable lineaDivisoria;

    public DecoracionLineaDivisoria(Context context) {
        lineaDivisoria = ContextCompat.getDrawable(context, R.drawable.linea_divisoria);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + lineaDivisoria.getIntrinsicHeight();

            lineaDivisoria.setBounds(left, top, right, bottom);
            lineaDivisoria.draw(c);
        }
    }
}
