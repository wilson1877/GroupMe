package com.mobileminiproject.wilsondaniel.groupme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventHome extends AppCompatActivity {
    //view objects
    ListView listViewEvents;
    CardView cardView;
    TextView textViewTitle, textViewDesc, textViewVenue, textViewDate, textViewTime;
    EditText editTextDate, editTextTime;
    DatePickerDialog datePickerDialog;
    //our database reference object
    DatabaseReference databaseEvents;
    //a list to store all the artist from firebase database
    List<Event> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),CreateEvent.class);
                startActivity(intent);
            }
        });

        //getting the reference of artists node
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        //getting views
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        textViewVenue = (TextView) findViewById(R.id.textViewVenue);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        cardView = (CardView) findViewById(R.id.cardView);
        listViewEvents = findViewById(R.id.listViewEvents);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        //list to store artists
        events = new ArrayList<>();
        listViewEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = events.get(i);
                showUpdateDeleteDialog(event.getEventID(), event.getTitle());
                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                events.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Event event = postSnapshot.getValue(Event.class);
                    //adding artist to the list
                    events.add(event);
                }
                //creating adapter
                EventList eventAdapter = new EventList(EventHome.this, events);
                //attaching adapter to the listview
                listViewEvents.setAdapter(eventAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private boolean updateEvent(String eventID, String title, String description, String venue, String date, String time) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventID);
        //updating artist
        Event event = new Event(eventID, title, description, venue, date, time);
        dR.setValue(event);
        Toast.makeText(getApplicationContext(), "Event Updated", Toast.LENGTH_SHORT).show();
        return true;
    }
    private boolean deleteEvent(String eventID) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventID);
        //removing artist
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Event Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }
    private void showUpdateDeleteDialog(final String eventID, String title) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_event_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextDesc = (EditText) dialogView.findViewById(R.id.editTextDesc);
        final EditText editTextVenue = (EditText) dialogView.findViewById(R.id.editTextVenue);
        final EditText editTextDate = (EditText) dialogView.findViewById(R.id.editTextDate);
        final EditText editTextTime = (EditText) dialogView.findViewById(R.id.editTextTime);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateBtn);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteBtn);
        dialogBuilder.setTitle(title);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(EventHome.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                editTextDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EventHome.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTextTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString().trim();
                String description = editTextDesc.getText().toString().trim();
                String venue = editTextVenue.getText().toString().trim();
                String date = editTextDate.getText().toString().trim();
                String time = editTextTime.getText().toString().trim();
                if (!TextUtils.isEmpty(title)) {
                    updateEvent(eventID, title, description, venue, date, time);
                    b.dismiss();
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(eventID);
                b.dismiss();
            }
        });
    }
}
