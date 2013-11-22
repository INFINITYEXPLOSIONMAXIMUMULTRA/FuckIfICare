package edu.gatech.events;

//import android.R;
import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    GoogleMap map;
    MapFragment mapFragment;
    AllEventsFragment eventsFragment;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        GeofenceHandler handler = new GeofenceHandler(this);
        List<Geofence> geofenceList = new ArrayList<Geofence>();
        Geofence.Builder geofenceBuilder = new Geofence.Builder();
        geofenceBuilder.setRequestId("Phi Sigma Kappa");
        geofenceBuilder.setCircularRegion(33.777152, -84.391853, 200);
        geofenceBuilder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER);
        geofenceBuilder.setExpirationDuration(Geofence.NEVER_EXPIRE);
        geofenceList.add(geofenceBuilder.build());
        geofenceBuilder = new Geofence.Builder();
        geofenceBuilder.setRequestId("College of Computing");
        geofenceBuilder.setCircularRegion(33.777417, -84.397318, 200);
        geofenceBuilder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER);
        geofenceBuilder.setExpirationDuration(Geofence.NEVER_EXPIRE);
        geofenceList.add(geofenceBuilder.build());
        handler.addGeofences(geofenceList);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.event_view_list, android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(spinnerAdapter, new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int i, long l) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft;
                switch (i) {
                    case 0:
                        ft = fm.beginTransaction();
                        ft.hide(mapFragment);
                        ft.show(eventsFragment);
                        ft.commit();
                        break;
                    case 1:
                        ft = fm.beginTransaction();
                        ft.hide(eventsFragment);
                        ft.show(mapFragment);
                        ft.commit();
                        break;
                    default:
                        break;
                }
                return true;
            }

        });

        eventsFragment = (AllEventsFragment) getFragmentManager().findFragmentById(R.id.events);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        final Marker studentCenter = map.addMarker(new MarkerOptions().position(new LatLng(33.773792, -84.398497)).title("Student Center").snippet("More fun that you can shake a stick at!"));
        map.addMarker(new MarkerOptions().position(new LatLng(33.775322,-84.399114)).title("Ferst Center").snippet("Home of Drama Tech"));
        map.addMarker(new MarkerOptions().position(new LatLng(33.775705, -84.404006)).title("Campus Recreation Center"));
        map.addMarker(new MarkerOptions().position(new LatLng(33.772535,-84.392816)).title("Bobby Dood Stadium"));
        map.addMarker(new MarkerOptions().position(new LatLng(33.780806,-84.392784)).title("McCamish Basketball Pavilion"));
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.equals(studentCenter)) {
                    Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(result, this, 0);

            if (errorDialog != null) {
                //TODO display error dialog
            }
        }
    }
    
    /**
     * Adds an option menu to this activitiy.  I am going to use it 
     * eventually to allow the user to filter events by date.
     * 
     * The menu is stored under res/values/settings_menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.settings_menu, menu);//settings_menu has an xml file defining it.
    	return true;
    }
    
    /**
     * Handles which options from the option menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//handles when the user selects an item from the option menu.
    	switch(item.getItemId()){
    	case R.id.resultFilterID:
    		//starts the settings activity because the user clicked on the filter results option
    		Intent settingIntent = new Intent(this,SettingsActivity.class);
    		startActivity(settingIntent);
    		return true;
    	default:
    		return true;
    	}
    }
}
