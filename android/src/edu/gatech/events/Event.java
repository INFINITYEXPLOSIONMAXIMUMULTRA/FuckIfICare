package edu.gatech.events;

import java.util.Date;

public class Event {
    public String title;
    public String description;
    public String location;
    public String/*Date*/ time;

    public Event() {}

    public Event(String title, String time, String location, String description) {
        this.title = title;
        this.time = time;
        this.location = location;
        this.description = description;
    }
}
