package com.android.example.alarmmanagerexample;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver
{
    static int times = 0;

    @Override
    public void onReceive( Context context, Intent intent )
    {
        PowerManager pm = (PowerManager) context.getSystemService( Context.POWER_SERVICE );
        PowerManager.WakeLock wl = pm.newWakeLock( PowerManager.PARTIAL_WAKE_LOCK, "SUNNAH_PRO_NOTIFY" );
        wl.acquire();


        wl.release();


        Uri soundUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder( context )
                        .setSmallIcon( R.mipmap.ic_launcher )
                        .setSound( soundUri )
                        .setContentTitle( "AlarmManagerExample" )
                        .setContentText( intent.getStringExtra( "reminder" ) );


        // Sets an ID for the notification
        int mNotificationId = intent.getIntExtra( "code", 133 );
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService( NOTIFICATION_SERVICE );
        // Builds the notification and issues it.
        mNotifyMgr.notify( mNotificationId, mBuilder.build() );


    }
}
