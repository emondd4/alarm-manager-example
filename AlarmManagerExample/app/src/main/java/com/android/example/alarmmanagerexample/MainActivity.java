package com.android.example.alarmmanagerexample;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity
{

    static final int WAKE_LOCK_REQUEST_CODE = 49488;  // random number

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        checkWakeLockPermission();
    }

    void checkWakeLockPermission()
    {


        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.WAKE_LOCK ) == PackageManager.PERMISSION_GRANTED )
        {
            setUpNotifiation();
        }
        else
        {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WAKE_LOCK}, WAKE_LOCK_REQUEST_CODE );
        }

    }

    @Override public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults )
    {
        switch ( requestCode )
        {

            case WAKE_LOCK_REQUEST_CODE:

                for ( int result : grantResults )
                {
                    if ( result == PackageManager.PERMISSION_DENIED )
                    {
                        return;
                    }
                }

                setUpNotifiation();
                break;


        }
    }


    void setUpNotifiation()
    {


        Long time = new GregorianCalendar().getTimeInMillis()+1*60*1000;

       Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtra( "reminder","LOLOLOLOLOLOL Noob reported!" );


        AlarmManager alarmManager = (AlarmManager) getSystemService( Context.ALARM_SERVICE);

        alarmManager.setRepeating(  AlarmManager.RTC_WAKEUP, time,30*1000, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
       // alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarm Scheduled for next minute", Toast.LENGTH_LONG).show();
    }
}
