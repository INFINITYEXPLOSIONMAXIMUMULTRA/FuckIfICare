package com.example.mobile_events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, EventListActivity.class);
                startActivity(intent);
                break;
            case R.id.button1:
                Intent intent2 = new Intent(this, EventDetailActivity.class);
                startActivity(intent2);
                break;

        }
    }
}
