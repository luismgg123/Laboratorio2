package com.suaj.yolo.presentacion1;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;


public class MainView extends View {
    private MainActivity main;
    public MainView(Context context){
        super(context);
        main = (MainActivity)context;
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(main.scr_bitmap, 0, 0, null);
    }

    protected void drawPoint(final float x, final float y){
        main.scr_canvas.drawCircle(x,y,main.brush_radius,main.paint);
    }
}
