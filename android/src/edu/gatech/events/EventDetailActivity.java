package edu.gatech.events;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetailActivity extends Activity {

    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        setTitle("Event details");

        event = getIntent().getParcelableExtra("Event");

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(event.title);
        TextView time = (TextView) findViewById(R.id.time);
        time.setText(event.time.toString());
        TextView location = (TextView) findViewById(R.id.location);
        location.setText(event.location);
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(event.description);
    }
}
