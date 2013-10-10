package edu.gatech.events;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AllEventsFragment extends ListFragment {

    List<Event> events;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        events = new ArrayList<Event>();
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
        setListAdapter(new EventListAdapter(getActivity(), events));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtra("Event", events.get(position));
        startActivity(intent);
    }
}
