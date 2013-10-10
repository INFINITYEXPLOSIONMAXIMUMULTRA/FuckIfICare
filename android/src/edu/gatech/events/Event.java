package edu.gatech.events;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Event implements Parcelable {
    public String title;
    public String description;
    public String location;
    public String/*Date*/ time;

    public Event() {}

    public Event(String title, String time, String location, String description) {
        this.title = title;
        this.time = time;
        if (!description.equals("")) {
            this.location = location;
        } else {
            this.location = "Student Center";
        }
        if (!description.equals("")) {
            this.description = description;
        } else {
            this.description = "Placeholder description";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeString(time);
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel parcel) {
            Event event = new Event();
            event.title = parcel.readString();
            event.description = parcel.readString();
            event.location = parcel.readString();
            event.time = parcel.readString();
            return event;
        }

        @Override
        public Event[] newArray(int i) {
            return new Event[i];
        }
    };
}
