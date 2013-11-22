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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

        int transition = LocationClient.getGeofenceTransition(intent);
        List<Geofence> geofences = LocationClient.getTriggeringGeofences(intent);
        InputStream inStream = null;
        Event event = null;
        try {
            URL downloadUrl = new URL("http://wesley-crusher.firba1.com:8080/api/v1.0/location/geteventsfornexthours/24");
            HttpURLConnection connect = (HttpURLConnection) downloadUrl.openConnection();
            connect.setReadTimeout(10000);
            connect.setConnectTimeout(15000);
            connect.setRequestMethod("GET");

            inStream = connect.getInputStream();
            Reader read = new InputStreamReader(inStream, "UTF-8");
            char [] buffers = new char[600];
            read.read(buffers);
            JSONObject json = new JSONObject(String.valueOf(buffers));
            JSONArray eventArray = json.getJSONArray("events");
            JSONObject temp = (JSONObject) eventArray.get(0);
            event = new Event();
            event.title = temp.getString("event_name");
            event.time = temp.getString("start_date");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }


        Intent notificationIntent = new Intent(getApplicationContext(), EventDetailActivity.class);
        notificationIntent.putExtra("Event", event);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.stat_notify_chat);
        builder.setContentTitle(event.title);
        builder.setContentText(event.location);
        builder.setContentIntent(notificationPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }
}
