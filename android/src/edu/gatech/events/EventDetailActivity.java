package edu.gatech.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventDetailActivity extends Activity {

    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        setTitle("Event details");

        event = getIntent().getParcelableExtra("Event");
        //handles setting up the 
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(event.title);
        TextView time = (TextView) findViewById(R.id.time);
        time.setText(event.time.toString());
        TextView location = (TextView) findViewById(R.id.location);
        location.setText(event.location);
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(event.description);
        
        //Following is to add a button listener so that when the user clicks on the 
        //calendar button that they can actually add it to their google calendar.
        Button addToCalendarButton = (Button)findViewById(R.id.calendarButton);
        addToCalendarButton.setOnClickListener(new View.OnClickListener() {
			//TODO factor this out into its own class.
			
        	@Override
			public void onClick(View v) {
				//Creates an intent that will allow the user to directly add the event into their
        		//google calendar.  It adds the title and location to their calendar event
				Intent startCalendarIntent = new Intent(Intent.ACTION_INSERT);
				startCalendarIntent.setType("vnd.android.cursor.item/event");
				startCalendarIntent.putExtra(Events.TITLE, event.title);
				startCalendarIntent.putExtra(Events.EVENT_LOCATION, event.location);
				startActivity(startCalendarIntent);
				//TODO add the starting date to the calendar
			}
		});
        
    }
}
