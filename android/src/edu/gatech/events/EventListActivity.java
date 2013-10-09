package edu.gatech.events;

import android.app.Activity;
import android.os.Bundle;

public class EventListActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_list);
        setTitle("Student Center Events");
    }
}