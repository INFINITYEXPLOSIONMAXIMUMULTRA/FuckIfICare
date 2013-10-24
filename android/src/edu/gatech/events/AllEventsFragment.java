package edu.gatech.events;

import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AllEventsFragment extends ListFragment {
	
	String webServiceResults;

    List<Event> events;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	new WebserviceManager().execute("http://wesley-crusher.firba1.com:8080/api/v1.0/location/getlocations");
        super.onActivityCreated(savedInstanceState);
        events = new ArrayList<Event>();
        //all static data rest gets populated in postExecute
        events.add(new Event("Build a Bear", "Tuesday 11:00 AM", "", ""));
        events.add(new Event("Get Artsy", "Friday 11:00 AM", "", ""));
        events.add(new Event("Take a Professor to Lunch", "October 8th 11:00 AM", "", ""));
        events.add(new Event("Tenative Game Show Night", "October 10th 7:00 PM", "", ""));
        events.add(new Event("Pumpkin Carving", "October 17th 2:00 PM", "", ""));
        events.add(new Event("Netherworld", "October 17th 7:00 PM", "", ""));
        events.add(new Event("General Member Meeting", "October 22nd 6:00 PM", "", ""));
        events.add(new Event("Homecoming Concert", "October 31st 8:00 PM", "", ""));
        events.add(new Event("Hip Hop Dance Class", "November 7th 7:00 PM", "", ""));
        events.add(new Event("General Member Meeting", "November 19th 6:00 PM", "", ""));
        events.add(new Event("Tentative Game Show Night", "November 21st 8:00 PM", "", ""));
        
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtra("Event", events.get(position));
        startActivity(intent);
    }
    
	private class WebserviceManager extends AsyncTask<String, Void, String> {
    	@Override
    	protected String doInBackground(String... params){
    		try{
    			return downloadUrl(params[0]);
    		} 	
    		catch(IOException e){
    			Log.d("Webservice-connect","downloadUrl failed"+e.getMessage());
    		}
    		
    		return "";
    	}

    	/**
    	 * Overrides the onPostExecute function for AsyncTask so that we can 
    	 */
		@Override
    	protected void onPostExecute(String result){
    		webServiceResults = result;
    		events.add(new Event(webServiceResults,"aaaaaaa","",""));
            setListAdapter(new EventListAdapter(getActivity(), events));
    	}
		
		public String downloadUrl(String url) throws IOException {
			InputStream inStream = null;
			try {
				URL downloadUrl = new URL(url);
				HttpURLConnection connect = (HttpURLConnection)downloadUrl.openConnection();
				connect.setReadTimeout(10000);
				connect.setConnectTimeout(15000);
				connect.setRequestMethod("GET");
				
				Log.d("Webservice-connect","connected");
				inStream = connect.getInputStream();
				Log.d("Webservice-connect","inStream:"+inStream.toString());
				Reader read = new InputStreamReader(inStream, "UTF-8");
				char [] buffers = new char[600];
				read.read(buffers);
				Log.d("webservice-connect",buffers.toString());
				return new String(buffers);
			}
			catch(Exception e) {
				Log.d("webservice-connect-error",e.getMessage());
			}
			finally {
				if(inStream != null) {
					inStream.close();
				}
			}
			return "";
		}
    }
}