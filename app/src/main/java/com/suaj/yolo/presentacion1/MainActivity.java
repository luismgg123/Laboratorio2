package com.suaj.yolo.presentacion1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class MainActivity extends ActionBarActivity implements View.OnClickListener  {
//holi2
    protected int width;
    protected int height;
    protected int color, colorEraser;
    protected float brush_radius;
    protected boolean anti_alias;
    protected Paint paint;
    protected Bitmap scr_bitmap;
    protected Canvas scr_canvas;

    private Vista drawView;
    private ImageButton b1,b2,b3,b4,b5,b6,b7, btnsave, btnphoto;
    private ImageButton c1, c2, c3, c4, c5, c6, c7, c8, c9, c10 , c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22, c23, c24;
    private Button btnCloseBrush, btnCloseEraser;


    //DEFAULTS
    int progressSize = 3;
    int progressSizeEraser = 30;



    private SeekBar sbBrushSize,sbEraserSize;
    private TextView brushSizeTxt,eraserSizeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        width = dm.widthPixels;
        height = dm.heightPixels;
        brush_radius =10;
        anti_alias = true;
        color = Color.BLACK;
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(anti_alias);

        b1= (ImageButton) findViewById(R.id.button1);
        b1.setOnClickListener(this);
        b2= (ImageButton) findViewById(R.id.button2);
        b2.setOnClickListener(this);
        b3= (ImageButton) findViewById(R.id.button3);
        b3.setOnClickListener(this);
        b4= (ImageButton) findViewById(R.id.button4);
        b4.setOnClickListener(this);
        b5= (ImageButton) findViewById(R.id.button5);
        b5.setOnClickListener(this);
        btnsave= (ImageButton) findViewById(R.id.btnsave);
        btnsave.setOnClickListener(this);
        btnphoto= (ImageButton) findViewById(R.id.btnphoto);
        btnphoto.setOnClickListener(this);
        b6= (ImageButton) findViewById(R.id.button6);
        b6.setOnClickListener(this);
        b7= (ImageButton) findViewById(R.id.button7);
        b7.setOnClickListener(this);



        //colores predefinidos
        c1 = (ImageButton) findViewById(R.id.imageButton);
        c1.setOnClickListener(this);
        c2 = (ImageButton) findViewById(R.id.imageButton2);
        c2.setOnClickListener(this);
        c3 = (ImageButton) findViewById(R.id.imageButton3);
        c3.setOnClickListener(this);
        c4 = (ImageButton) findViewById(R.id.imageButton4);
        c4.setOnClickListener(this);
        c5 = (ImageButton) findViewById(R.id.imageButton5);
        c5.setOnClickListener(this);
        c6 = (ImageButton) findViewById(R.id.imageButton6);
        c6.setOnClickListener(this);
        c7 = (ImageButton) findViewById(R.id.imageButton7);
        c7.setOnClickListener(this);
        c8 = (ImageButton) findViewById(R.id.imageButton8);
        c8.setOnClickListener(this);
        c9 = (ImageButton) findViewById(R.id.imageButton9);
        c9.setOnClickListener(this);
        c10 = (ImageButton) findViewById(R.id.imageButton10);
        c10.setOnClickListener(this);
        c11 = (ImageButton) findViewById(R.id.imageButton11);
        c11.setOnClickListener(this);
        c12 = (ImageButton) findViewById(R.id.imageButton12);
        c12.setOnClickListener(this);
        c13 = (ImageButton) findViewById(R.id.imageButton13);
        c13.setOnClickListener(this);
        c14 = (ImageButton) findViewById(R.id.imageButton14);
        c14.setOnClickListener(this);
        c15 = (ImageButton) findViewById(R.id.imageButton15);
        c15.setOnClickListener(this);
        c16 = (ImageButton) findViewById(R.id.imageButton16);
        c16.setOnClickListener(this);
        c17 = (ImageButton) findViewById(R.id.imageButton17);
        c17.setOnClickListener(this);
        c18 = (ImageButton) findViewById(R.id.imageButton18);
        c18.setOnClickListener(this);
        c19 = (ImageButton) findViewById(R.id.imageButton19);
        c19.setOnClickListener(this);
        c20 = (ImageButton) findViewById(R.id.imageButton20);
        c20.setOnClickListener(this);
        c21 = (ImageButton) findViewById(R.id.imageButton21);
        c21.setOnClickListener(this);
        c22 = (ImageButton) findViewById(R.id.imageButton22);
        c22.setOnClickListener(this);
        c23 = (ImageButton) findViewById(R.id.imageButton23);
        c23.setOnClickListener(this);
        c24 = (ImageButton) findViewById(R.id.imageButton24);
        c24.setOnClickListener(this);







        Toast.makeText(getApplication(), "Ancho:"+width+ "X Alto:"+height, Toast.LENGTH_LONG).show();

        scr_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        scr_canvas = new Canvas(scr_bitmap);
        scr_canvas.drawColor(Color.BLUE);
        drawView = (Vista) findViewById(R.id.dibujando);


        drawView.setBrushSize(progressSize);


        colorEraser = Color.WHITE;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){


            //PALETA FIJA

            case R.id.imageButton:
                drawView.setColor("#ff0000");
                break;

            case R.id.imageButton2:
                drawView.setColor("#ff8000");
                break;

            case R.id.imageButton3:
                drawView.setColor("#ffff00");
                break;

            case R.id.imageButton4:
                drawView.setColor("#80ff00");
                break;

            case R.id.imageButton5:
                drawView.setColor("#00ff00");
                break;

            case R.id.imageButton6:
                drawView.setColor("#00ff80");
                break;

            case R.id.imageButton7:

                drawView.setColor("#00cccc");
                break;

            case R.id.imageButton8:
                drawView.setColor("#0080ff");
                break;

            case R.id.imageButton9:
                drawView.setColor("#0000ff");
                break;

            case R.id.imageButton10:
                drawView.setColor("#7f00ff");
                break;

            case R.id.imageButton11:
                drawView.setColor("#ff00ff");

                break;

            case R.id.imageButton12:
                drawView.setColor("#ff007f");

                break;

            case R.id.imageButton13:
                drawView.setColor("#ffcc99");

                break;

            case R.id.imageButton14:
                drawView.setColor("#A89703");

                break;

            case R.id.imageButton15:
                drawView.setColor("#663300");

                break;

            case R.id.imageButton16:
                drawView.setColor("#a0a0a0");
                break;

            case R.id.imageButton17:
                drawView.setColor("#000000");

                break;

            case R.id.imageButton18:
                drawView.setColor("#ffffff");
                break;

            case R.id.imageButton19:
                drawView.setColor("#003333");
                break;

            case R.id.imageButton20:
                drawView.setColor("#660000");
                break;

            case R.id.imageButton21:
                drawView.setColor("#190033");

                break;

            case R.id.imageButton22:
                drawView.setColor("#202020");

                break;

            case R.id.imageButton23:
                drawView.setColor("#006600");

                break;

            case R.id.imageButton24:
                drawView.setColor("#ff6666");
                break;


            //NUEVO
            case R.id.button1:
                AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setMessage("\u00bf"+"Desea crear un nuevo dibujo?");
                newDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        drawView.NuevaPintura();
                        dialog.dismiss();
                    }
                });
                newDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                newDialog.show();
                break;

            // TAMANO PINCEL
            case R.id.button2:
                //defaults
                drawView.setBrushSize(progressSize);
               // drawView.setColor2(color);
                //draw button clicked
                final Dialog brushDialog = new Dialog(this);
                //final AlertDialog.Builder brushDialog = new AlertDialog.Builder(this);
                //AlertDialog.Builder brushDialog = new AlertDialog.Builder(this);
                brushDialog.setTitle("Tama"+"\u00f1"+ "o de la brocha:");


                brushDialog.setContentView(R.layout.brush_ch);

                sbBrushSize = (SeekBar)  brushDialog.findViewById(R.id.sbBrushSize);
                brushSizeTxt = (TextView) brushDialog.findViewById(R.id.brushSizeTxt);
                brushSizeTxt.setText(" " + progressSize);
                sbBrushSize.setProgress(progressSize);
                sbBrushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                        progressSize = progresValue;
                        //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        brushSizeTxt.setText(" "  + progressSize);
                        drawView.setBrushSize(progressSize);
                        drawView.setLastBrushSize(progressSize);
                        //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                    }
                });


                btnCloseBrush = (Button)brushDialog.findViewById(R.id.btnCloseBrush);
                btnCloseBrush.setOnClickListener(this);


           /* if(btnCloseBrush.performClick()){
                dialog.cancel();
                brushDialog.dismiss();
            }*/
                btnCloseBrush.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        brushDialog.cancel();
                        brushDialog.dismiss();
                    }
                });


                brushDialog.show();

                break;

            //PALETA
            case R.id.button3:

                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, 0xff00ffff, new ColorPickerDialog.OnColorSelectedListener() {

                    @Override
                    public void onColorSelected(int color) {
                       drawView.setColor2(color);
                    }

                });
                colorPickerDialog.show();

                break;


            //COMPARTIR
            case R.id.button4:
               shareIt();
                break;


            //CLOSE
            case R.id.button5:
                AlertDialog.Builder newDialogF = new AlertDialog.Builder(this);
                newDialogF.setMessage("\u00bf"+"Desea Salir del Micropaint?");
                newDialogF.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                newDialogF.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                newDialogF.show();

                break;
            //photo
            case R.id.btnphoto:
                dispatchTakePictureIntent();
                galleryAddPic();
                break;
            //save
            case R.id.btnsave:
                AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
                //saveDialog.setTitle("Save drawing");
                saveDialog.setMessage("\u00BF "+"Desea guardar su dibujo en la galer" + "\u00ED"+ "a?");
                saveDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //save drawing
                        drawView.setDrawingCacheEnabled(true);
                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), drawView.getDrawingCache(),
                                UUID.randomUUID().toString() + ".png", "drawing");
                        if (imgSaved != null) {
                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Dibujo guardado en la galer" + "\u00ED"+ "a!", Toast.LENGTH_SHORT);
                            savedToast.show();
                        } else {
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Oops! La imagen no pudo ser guardada.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        drawView.destroyDrawingCache();
                    }
                });
                saveDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                saveDialog.show();
                break;

            //GALERIA
            case R.id.button6:

                //new button
                Log.e("ENTRE which", "ENTRE which");
                final String[] imgs =new String[] { "Pikachu", "Mario", "Kirby", "Donkey Kong",};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("          Seleccione una imagen:");
                builder.setAdapter(new MobileArrayAdapter(this, imgs),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("which",which+"");
                        drawView.setImage(which+1);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                builder.show();

                break;


            //BORRADOR
            case R.id.button7:
                //defaults
                drawView.setBrushSize(progressSizeEraser);
                drawView.setColor2(colorEraser);

                //draw button clicked
                final Dialog eraserDialog = new Dialog(this);
                //final AlertDialog.Builder eraserDialog = new AlertDialog.Builder(this);
                //AlertDialog.Builder eraserDialog = new AlertDialog.Builder(this);
                eraserDialog.setTitle("Tama"+"\u00f1"+ "o del borrador:");


                eraserDialog.setContentView(R.layout.eraser_ch);

                sbEraserSize = (SeekBar)  eraserDialog.findViewById(R.id.sbEraserSize);
                eraserSizeText = (TextView) eraserDialog.findViewById(R.id.eraserSizeTxt);
                eraserSizeText.setText(" " + progressSizeEraser);
                sbEraserSize.setProgress(progressSizeEraser);
                sbEraserSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                        progressSizeEraser = progresValue;
                        //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        eraserSizeText.setText(" "  + progressSizeEraser);
                        drawView.setBrushSize(progressSizeEraser);
                        drawView.setLastBrushSize(progressSizeEraser);
                        //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                    }
                });




                btnCloseEraser = (Button)eraserDialog.findViewById(R.id.btnCloseEraser);
                btnCloseEraser.setOnClickListener(this);


           /* if(btnCloseBrush.performClick()){
                dialog.cancel();
                eraserDialog.dismiss();
            }*/
                btnCloseEraser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eraserDialog.cancel();
                        eraserDialog.dismiss();
                    }
                });


                eraserDialog.show();
                break;

        }



    }

    String mCurrentPhotoPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap ibitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            drawView.setPhoto(ibitmap);
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);

    }


    //Metodo para compartir
    private void shareIt() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/png");
        drawView.setDrawingCacheEnabled(true);
        Bitmap image=drawView.getDrawingCache();
        Uri uri=getLocalBitmapUri(image);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Compartir imagen por..."));
        drawView.destroyDrawingCache();
    }


    public Uri getLocalBitmapUri(Bitmap bm) {
        Bitmap bmp = bm;
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;

    }


}
