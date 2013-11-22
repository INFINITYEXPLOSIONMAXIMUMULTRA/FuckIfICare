package edu.gatech.events;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SettingsActivity extends Activity{
	
	SeekBar numberOfHours = null;
	//activity to handle the settings page to a user so they can set preferences
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        numberOfHours = (SeekBar)findViewById(R.id.hoursSeekBar);
        //going to use this for getting the status of the seek bar.
        numberOfHours.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
        	int sliderPosition = 0;//keeps track of the position of the seekbar
        	
        	/**
        	 * Handles the changes for the slider on the settings screen.
        	 * 
        	 * @param seek The seekbar that is being used.
        	 * @param position The position that the seekbar is 
        	 */
			@Override
			public void onProgressChanged(SeekBar seek, int position, boolean fromUser) {
				//This particular seekbar has a maximum of 24 hours
				Log.d("seekbar seek",seek.toString());
				Log.d("seekbar position",Integer.valueOf(position).toString());
				this.sliderPosition = position;
				TextView numberOfHours = (TextView) findViewById(R.id.numHours);
				numberOfHours.setText(Integer.valueOf(position).toString());
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
        	
        });
	}
}