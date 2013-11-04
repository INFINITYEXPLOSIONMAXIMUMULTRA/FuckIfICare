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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllEventsFragment extends ListFragment {
	
	String webServiceResults;

    List<Event> events;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	new WebserviceManager().execute("http://wesley-crusher.firba1.com:8080/api/v1.0/location/geteventsfornexthours/12");
        super.onActivityCreated(savedInstanceState);
        events = new ArrayList<Event>();
        //the rest of the data is populated in the AsyncTask class specifically
        //in the onPostExecute function
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
    		try {
				JSONObject json = new JSONObject(result);
				JSONArray eventArray = json.getJSONArray("events");
				for(int i=0;i<eventArray.length();i++){
					JSONObject temp = (JSONObject) eventArray.get(i);
					Event tempEvent = new Event();
					tempEvent.title = temp.getString("event_name");
					tempEvent.time = temp.getString("start_date");
					events.add(tempEvent);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
    		//events.add(new Event(webServiceResults,"aaaaaaa","",""));
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