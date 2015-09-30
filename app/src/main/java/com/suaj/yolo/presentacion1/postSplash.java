package com.suaj.yolo.presentacion1;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class postSplash extends ActionBarActivity implements View.OnClickListener {

    private EditText edtUsurio, edtPass;
    private Button bEntrar;
    private TextView txtReg, txtOlv;
    private static final int CONTENT_VIEW_ID = 10101010;
    //facebook login
    public static final String FRAGMENT_TAG = "fragment_tag";
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_splash);

        edtUsurio = (EditText) findViewById(R.id.edtUsuario);
        edtPass = (EditText) findViewById(R.id.edtPass);
        bEntrar = (Button) findViewById(R.id.bEntrar);
        bEntrar.setOnClickListener(this);
        txtOlv = (TextView) findViewById(R.id.txtOlv);
        txtOlv.setOnClickListener(this);
        txtReg = (TextView) findViewById(R.id.txtReg);
        txtReg.setOnClickListener(this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.suaj.yolo.presentacion1", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("Clave:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }

        ////LOGIN FACEBOOK
        //facebook login
        mFragmentManager =  getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();


        //codigo fragment por tipo
        Bundle bundle = new Bundle();
        bundle.putString("actividad", "login");
        // set Fragmentclass Arguments

        FLoginFragment fragobj = new FLoginFragment();
        fragobj.setArguments(bundle);

        transaction.replace(android.R.id.content, fragobj,FRAGMENT_TAG);
        transaction.commit();
        ////LOGIN FACEBOOK
        /**/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_splash, menu);
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
            case R.id.bEntrar:
                LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this, edtUsurio.getText().toString(), edtPass.getText().toString());
                loginAsyncTask.execute();
                break;
            case R.id.txtOlv:
                finish();
                break;
            case R.id.txtReg:
                Intent i = new Intent(this, Registro.class);
                startActivity(i);
                finish();
                break;


        }
    }
}
