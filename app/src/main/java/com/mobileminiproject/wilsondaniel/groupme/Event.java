package com.mobileminiproject.wilsondaniel.groupme;

public class Event {

    private String eventID, title, description, venue, date, time;

    public Event() {}

    public Event(String eventID, String title, String description, String venue, String date, String time) {
        this.eventID = eventID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.venue = venue;
        this.time = time;
    }

    public String getEventID() {
        return eventID;
    }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getDate() { return date; }

    public String getVenue() { return venue; }

    public String getTime() { return time; }

}