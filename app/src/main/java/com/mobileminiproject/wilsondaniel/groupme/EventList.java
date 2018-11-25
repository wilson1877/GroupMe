package com.mobileminiproject.wilsondaniel.groupme;

import android.app.Activity;
import android.support.annotation.NonNull;
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
public class EventList extends ArrayAdapter<Event> {
    private Activity context;
    List<Event> events;
    ListView eventList;
    public EventList(Activity context, List<Event> events) {
        super(context, R.layout.activity_event_list, events);
        this.context = context;
        this.events = events;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_event_list, null, true);
        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textViewTitle);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        TextView textViewVenue = (TextView) listViewItem.findViewById(R.id.textViewVenue);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);
        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.textViewTime);
        Event event = events.get(position);
        textViewTitle.setText(event.getTitle());
        textViewDesc.setText(event.getDescription());
        textViewVenue.setText(event.getVenue());
        textViewDate.setText(event.getDate());
        textViewTime.setText(event.getTime());
        return listViewItem;
    }
}
