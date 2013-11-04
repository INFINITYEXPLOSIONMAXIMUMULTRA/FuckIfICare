package edu.gatech.events;

import android.*;
import android.R;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.List;

public class GeofenceIntentService extends IntentService {
    public GeofenceIntentService() {
        super("GeofenceIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Geofence Service", "Handling Geofence transition");

        if(LocationClient.hasError(intent)) {
            //TODO handle error
            return;
        }

//        int transition = LocationClient.getGeofenceTransition(intent);
//        List<Geofence> geofences = LocationClient.getTriggeringGeofences(intent);

        Intent notificationIntent = new Intent(getApplicationContext(), EventDetailActivity.class);
        notificationIntent.putExtra("Event", new Event("Party", "Party Time", "Partyland", "PARTY!"));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.stat_notify_chat);
        builder.setContentTitle("Geofence Notification");
        builder.setContentText("You hit a geofence!");
        builder.setContentIntent(notificationPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }
}
