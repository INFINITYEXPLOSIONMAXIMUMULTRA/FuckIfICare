package edu.gatech.events;

import android.app.Activity;
import android.os.Bundle;
import edu.gatech.events.R;

public class EventDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        setTitle("Event details");
    }
}
