package com.suaj.yolo.presentacion1;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class Vista extends View {

    //ruta del dibujo
    private Path drawPath;
    //paleta de pintura
    private Paint drawPaint, canvasPaint;
    //color inicial
    private int paintColor = Color.argb(180, 0, 0, 0);
    //canvas
    private Canvas drawCanvas;
    //el canvas necesita una imagen donde pintar
    private Bitmap canvasBitmap;
    //ancho de la brocha
    private float brushSize, lastBrushSize;
    //borrado si o no
    private boolean erase = false;
    //ancgo y alto
    int ancho, alto;

    public Vista(Context context, AttributeSet attrs) {
        super(context, attrs);
        definiendo();
    }

    //inicializancion de valores
    private void definiendo() {
        //ancho de la brocha
        brushSize = 20;
        lastBrushSize = brushSize;
        //ruta de los trazos
        drawPath = new Path();
        //paleta
        drawPaint = new Paint();
        //color de la paleta negro
        drawPaint.setColor(paintColor);
        //transparente
        drawPaint.setAntiAlias(true);
        //grosor de la paleta
        drawPaint.setStrokeWidth(brushSize);
        //estilo de la paleta
        drawPaint.setStyle(Paint.Style.STROKE);
        //paleta redondo
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        //nuevo canvas
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Procedemos a posicionar la imagen a partir de la posición 0,0 en la pantalla del dispositivo
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        //dibuja en base a una paleta
        canvas.drawPath(drawPath, drawPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Ajuste el comienzo deL siguiente contorno hasta el punto (x, y).
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                /*
                Añadir una línea desde el último punto hasta el punto especificado (x, y).
                Si no hubo un moveTo () , el primer punto se establece automáticamente en (0,0).
                 */
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                //dibuja en base a una paleta
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //actualizar
        invalidate();
        return true;
    }


    //nueva pintura
    public void NuevaPintura() {
        drawCanvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        invalidate();
    }


    //tamaño pincel
    public void setBrushSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize(){
        return lastBrushSize;
    }

    //galería
    public void setImage(int numImagen){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Bitmap imagenGaleria=null;
        if(numImagen==1){imagenGaleria = BitmapFactory.decodeResource(getResources(), R.drawable.img1);}
        else if(numImagen==2){imagenGaleria = BitmapFactory.decodeResource(getResources(), R.drawable.img2);}
        else if(numImagen==3){imagenGaleria = BitmapFactory.decodeResource(getResources(), R.drawable.img3);}
        else if(numImagen==4){imagenGaleria = BitmapFactory.decodeResource(getResources(), R.drawable.img4);}
        //canvasBitmap = imagenGalleria.copy(Bitmap.Config.ARGB_8888, true);
        //drawCanvas = new Canvas(canvasBitmap);
        Bitmap newBitmap=imagenGaleria.copy(Bitmap.Config.ARGB_8888, true);
        int alturaDibujo=ancho*newBitmap.getHeight()/newBitmap.getWidth();
        drawCanvas.drawBitmap(getResizedBitmap(newBitmap,ancho,alturaDibujo),0,0,null);
        invalidate();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }





    //COLOR
    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }
    public void setColor2(Integer newColor){
        invalidate();
        paintColor=newColor;
        //paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }


    //set erase true or false
    public void setErase(boolean isErase){
        erase=isErase;
        if(erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else drawPaint.setXfermode(null);
    }



    //size assigned to view
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ancho = w;
        alto = h;
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }


}