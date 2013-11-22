package edu.gatech.events;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.location.LocationClient;

import java.util.Calendar;

public class GeofenceIntentService extends IntentService {

    public static final String PREFS_NAME = "settings";

    public GeofenceIntentService() {
        super("GeofenceIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Geofence Service", "Handling Geofence transition");

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean notifWhenBusy = settings.getBoolean("notifWhenBusy", true);

        boolean isBusy = isBusy();

        if(notifWhenBusy && !isBusy) {
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

            builder.setContentTitle("Geofence Notification");
            builder.setContentText("You hit a geofence!");
            builder.setContentIntent(notificationPendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, builder.build());
        }
    }

    public boolean isBusy() {
        Uri uri = Uri.parse("content://com.android.calendar/events");

        String[] projection = new String[] {
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.EVENT_LOCATION
        };

        String selection = "((" + CalendarContract.Events.DTSTART + " < ?) AND ("
                + CalendarContract.Events.DTEND + " > ?))";

        String[] selectionArgs = new String[] {String.valueOf(Calendar.getInstance().getTimeInMillis()),
                String.valueOf(Calendar.getInstance().getTimeInMillis())};

        Cursor cursor = this.getContentResolver().query(uri, projection, selection, selectionArgs, null);

        return (cursor != null) && (cursor.getCount() >= 1);
    }
}
