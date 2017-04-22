package com.example.chintan.tamagotchigo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by chintan24 on 7/30/2016.
 */
public class Myservice extends MyIntentService {
    float curTime=0;
    public Myservice(String name) {
        super(name);

    }

    @Override
    public void onCreate() {
        // do any initialization here
       // mediaPlayer = MediaPlayer.create(this, R.raw.music);
    }

    // Called as a result of a call to startService()
    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {

        do{
            curTime = System.nanoTime() / 1000000000f;
            //System.out.println("curTime  : "+curTime+" timWhenHunger "+Assets.timeWhenHungry );
        }while(curTime < Assets.GAMETIMER);
            Notify();
        return START_STICKY;
    }

    //For notification
    private void Notify () {
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("TamagotchiGo")
                .setContentText("Hungry!, Give me food.")
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.egg);

        // Create an ID number for this notification
        int mNotifyID = 999;
        // Get an instance of the notification manager
        NotificationManager manager = (NotificationManager) getSystemService (NOTIFICATION_SERVICE);
        // Issue the notification
        manager.notify(mNotifyID, mBuilder.build());
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
    }

    // Not using this but it is required
    @Override
    public IBinder onBind (Intent intent) { return null;}

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
