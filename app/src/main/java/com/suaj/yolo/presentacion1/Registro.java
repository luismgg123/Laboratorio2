package com.suaj.yolo.presentacion1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Registro extends ActionBarActivity implements View.OnClickListener{

    private EditText edtUsuario, edtPass, edtRePass;
    private Button btnRegistrar, btnRe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro);

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtRePass = (EditText) findViewById(R.id.edtRePass);
        btnRe= (Button) findViewById(R.id.btnRe);
        btnRe.setOnClickListener(this);
        btnRegistrar= (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnRegistrar:
               // if(edtPass.getText().toString()==edtRePass.getText().toString()){
                SignAsyncTask signAsyncTask = new SignAsyncTask(this, edtUsuario.getText().toString(), edtPass.getText().toString());
                signAsyncTask.execute();
               /* } else{
                    Toast.makeText(this,"Password no coincide",Toast.LENGTH_LONG).show();
                }*/
                break;
            case R.id.btnRe:
                Intent i = new Intent(this, postSplash.class);
                startActivity(i);
                finish();
                break;


        }
    }
}
