package com.example.mobile_events;

import android.app.Activity;
import android.os.Bundle;

public class EventDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        setTitle("Event details");
    }
}
