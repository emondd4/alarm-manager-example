package com.android.example.alarmmanagerexample;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver
{
static int times=0;
    @Override
    public void onReceive( Context context, Intent intent )
    {
        PowerManager pm = (PowerManager) context.getSystemService( Context.POWER_SERVICE );
        PowerManager.WakeLock wl = pm.newWakeLock( PowerManager.PARTIAL_WAKE_LOCK, "SUNNAH_PRO_NOTIFY" );
        wl.acquire();

        // Put here YOUR code.
        Toast.makeText( context, "In <3 with the Shape of you", Toast.LENGTH_LONG ).show();

        wl.release();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder( context )
                        .setSmallIcon( R.mipmap.ic_launcher )
                        .setContentTitle( "My notification" )
                        .setContentText( intent.getStringExtra( "reminder" )+ " "+(times++)+" times! gg" );

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService( NOTIFICATION_SERVICE );
        // Builds the notification and issues it.
        mNotifyMgr.notify( mNotificationId, mBuilder.build() );

        context.startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "https://www.google.com" ) ) );


    }
}
