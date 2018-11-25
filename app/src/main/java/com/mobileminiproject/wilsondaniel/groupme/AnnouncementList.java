package com.mobileminiproject.wilsondaniel.groupme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Belal on 2/26/2017.
 */

public class AnnouncementList extends ArrayAdapter<Announcement> {
    private Activity context;
    List<Announcement> announcements;
    ListView announcementList;

    public AnnouncementList(Activity context, List<Announcement> announcements) {
        super(context, R.layout.activity_announcement_list, announcements);
        this.context = context;
        this.announcements = announcements;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_announcement_list, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textViewTitle);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);

        Announcement artist = announcements.get(position);
        textViewTitle.setText(artist.getTitle());
        textViewDesc.setText(artist.getDescription());

        return listViewItem;
    }
}