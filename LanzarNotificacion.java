package com.example.adnsoftware1.lanzarnotificacion;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.net.URI;
import java.net.URL;

import static android.media.RingtoneManager.TYPE_NOTIFICATION;

public class LanzarNotificacion extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ManejadorMensaje manejadorMensaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanzar_notificacion);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("ADN Software");
        progressDialog.setMessage("Un momento Porfavor");
        progressDialog.setCancelable(true);

        manejadorMensaje = new ManejadorMensaje();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lanzar_notificacion, menu);
        return true;
    }

    public void LanzarNoti(View view ){
        progressDialog.show();
        Thread hilo = new Thread(new Timer());
        //  hilo.setDaemon(true);
        hilo.start();



    }
    public void Notifi(){
        /*PURI soud = RingtoneManager.getDefaultType(RingtoneManager.getActualDefaultRingtoneUri(RingtoneManager.TYPE_NOTIFICATION));*/
        NotificationCompat.Builder notificacion =  new  NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setLargeIcon((((BitmapDrawable) ResourcesCompat
                        .getDrawable(getResources(), R.drawable.noti, null)).getBitmap()))
                        .setContentTitle("Tienes una tarea")
                        .setContentText("Mira tu tarea")
                        .setTicker("ADN Tarea")
                        .setContentInfo("2");
           Intent Inotificacion= new Intent(getApplicationContext(),segundaVentana.class);

        PendingIntent IntencionPendiente= PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),segundaVentana.class),0);
        notificacion.setContentIntent(IntencionPendiente);

        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(10, notificacion.build());
    }

    private  class Timer implements  Runnable{
        int i = 0;

        @Override
        public void run() {
            for(i=5;i>=0;i--){
                try {
                    Thread.sleep(1000);


                }catch (Exception o) {
                }

                Bundle bundle = new Bundle();
                bundle.putInt("Current count", i);
                Message message = new Message();
                message.setData(bundle);

                manejadorMensaje.sendMessage(message);

            }

            progressDialog.dismiss();
        }
    }

    private  class ManejadorMensaje  extends Handler{

        @Override
        public void handleMessage(Message msg) {
            int currentCount = msg.getData().getInt("Current count");
            progressDialog.setMessage("Un momento por favor.." + currentCount);

            if( currentCount == 0)
            {
                Notifi();
            }

        }



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
}
