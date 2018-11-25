package com.mobileminiproject.wilsondaniel.groupme;

public class Announcement {

    private String announcementID, title, description;

    public Announcement() {}

    public Announcement(String announcementID, String title, String description) {
        this.announcementID = announcementID;
        this.title = title;
        this.description = description;
    }

    public String getAnnouncementID() {
        return announcementID;
    }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

}

