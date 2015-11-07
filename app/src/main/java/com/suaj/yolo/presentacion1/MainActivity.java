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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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

        // Buscar AdView como recurso y cargar una solicitud.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

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
