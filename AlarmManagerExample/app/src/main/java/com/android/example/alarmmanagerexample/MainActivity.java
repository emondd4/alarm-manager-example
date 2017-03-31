package com.android.example.alarmmanagerexample;

import android.Manifest;
import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{

    static final int WAKE_LOCK_REQUEST_CODE = 49488;  // random number

    @BindView(R.id.enable_button) Button enableButton;

    @BindView(R.id.disable_button) Button disableButton;


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind( this );
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


        final Long time = new GregorianCalendar().getTimeInMillis() + 1 * 60 * 1000;

        final RemindersIntentManager remindersIntentManager = RemindersIntentManager.getInstance( this );

        String toParse ="31-3-2017 16:44"; //set any date
        SimpleDateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm"); // I assume d-M, you may refer to M-d for month-day instead.
        Date date = null; // You will need try/catch around this
        try
        {
            date = formatter.parse(toParse);
        }
        catch ( ParseException e )
        {
            e.printStackTrace();
        }

        long timeFromDay = date.getTime();


        final AlarmManager alarmManager = (AlarmManager) getSystemService( Context.ALARM_SERVICE );
        // alarmManager.cancel( PendingIntent.getBroadcast( this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT ) );
        alarmManager.setRepeating( AlarmManager.RTC_WAKEUP, time, 30 * 1000, remindersIntentManager.getChristmasIntent() );
        alarmManager.set( AlarmManager.RTC_WAKEUP, timeFromDay, remindersIntentManager.getDotaIntent() );

        Toast.makeText( this, "Alarm Scheduled for next minute", Toast.LENGTH_LONG ).show();

        enableButton.setOnClickListener( new View.OnClickListener()
        {
            @Override public void onClick( View view )
            {

                alarmManager.setRepeating( AlarmManager.RTC_WAKEUP, time, 30 * 1000, remindersIntentManager.getChristmasIntent() );

            }
        } );

        disableButton.setOnClickListener( new View.OnClickListener()
        {
            @Override public void onClick( View view )
            {
                alarmManager.cancel( remindersIntentManager.getChristmasIntent() );
            }
        } );


    }
}
