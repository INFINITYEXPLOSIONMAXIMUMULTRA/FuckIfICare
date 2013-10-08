package edu.gatech.events;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllEventsFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        data.add(new HashMap<String, String>() {{
            put("Title", "Build a Bear");
            put("Time", "Tuesday 11:00 AM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "Get Artsy");
            put("Time", "Friday 11:00 AM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "Take a Professor to Lunch");
            put("Time", "October 8th 11:00 AM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "Tenative Game Show Night");
            put("Time", "October 10th 7:00 PM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "Pumpkin Carving");
            put("Time", "October 17th 2:00 PM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "Netherworld");
            put("Time", "October 17th 7:00 PM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "General Member Meeting");
            put("Time", "October 22nd 6:00 PM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "Homecoming Concert");
            put("Time", "October 31st 8:00 PM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "Hip Hop Dance Class");
            put("Time", "November 7th 7:00 PM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "General Member Meeting");
            put("Time", "November 19th 6:00 PM");
        }});
        data.add(new HashMap<String, String>() {{
            put("Title", "Tentative Game Show Night");
            put("Time", "November 21st 8:00 PM");
        }});
        setListAdapter(new SimpleAdapter(getActivity(), data, android.R.layout.simple_list_item_2, new String[]{"Title", "Time"}, new int[]{android.R.id.text1, android.R.id.text2}));
    }
}
