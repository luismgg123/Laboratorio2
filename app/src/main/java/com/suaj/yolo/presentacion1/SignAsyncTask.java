package com.suaj.yolo.presentacion1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by HP-LM on 12/09/2015.
 */
public class SignAsyncTask extends AsyncTask<Void, Void, String> {

    private Activity activity;
    private ProgressDialog dialog;
    private String username;
    private String password;

    public SignAsyncTask(Activity activity, String username, String password){
        super();
        this.activity = activity;
        this.username = username.replace(" ","+");
        this.password = password;
        this.dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute(){
        this.dialog.setMessage("Signing in...");
        this.dialog.setTitle("App Dibujo");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    //Ejecución de otro proceso
    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpclient=new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.inkadroid.com/inkadroid/webservice/2015/g1/respuesta.php?tag=register&usuario="+username+"&password="+password);

        try{
            HttpResponse response = httpclient.execute(httppost);
            String str;
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                str = EntityUtils.toString(response.getEntity());
            }else{
                str = "error";
            }
            return str;
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected  void onPostExecute(String result){
        this.dialog.dismiss();
        try {
            if (!result.equalsIgnoreCase("error")){
                JSONArray jsonArray = new JSONArray(result);
                JSONObject json = jsonArray.getJSONObject(0);
                boolean status = json.getBoolean("error");

                if(status==true){
                    String errormsg=json.getString("error_msg");
                    if (errormsg.equalsIgnoreCase("Usuario ya existe")){
                        Toast.makeText(activity.getApplicationContext(), errormsg, Toast.LENGTH_LONG).show();
                    }
                    if (errormsg.equalsIgnoreCase("Error en registro")){
                        Toast.makeText(activity.getApplicationContext(), errormsg, Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent i = new Intent((Registro)activity, postSplash.class);
                    activity.startActivity(i);
                    activity.finish();
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }



}
