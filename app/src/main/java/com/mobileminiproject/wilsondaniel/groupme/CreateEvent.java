package com.mobileminiproject.wilsondaniel.groupme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateEvent extends AppCompatActivity {

    EditText editTextTitle, editTextDesc, editTextVenue, editTextDate, editTextTime;
    Button postBtn, viewBtn;
    DatePickerDialog datePickerDialog;

    List<Event> Events;

    DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //getting the reference of users node
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        //getting views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDesc = (EditText) findViewById(R.id.editTextDesc);
        editTextVenue = findViewById(R.id.editTextVenue);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        postBtn = findViewById(R.id.postBtn);
        viewBtn = findViewById(R.id.viewBtn);
        editTextTime = (EditText) findViewById(R.id.editTextTime);

        //list to store users
        Events = new ArrayList<>(); //should be in login, not used here.

        //adding an onclicklistener to button
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == postBtn) {
                    postEvent();
                }
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == viewBtn) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), EventHome.class));
                }
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CreateEvent.this,
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
                mTimePicker = new TimePickerDialog(CreateEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTextTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }


    private void postEvent() {
        //getting the values to save
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDesc.getText().toString().trim();
        String venue = editTextVenue.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(title)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Auction Draft
            String eventID = databaseEvents.push().getKey();

            //creating an Auction Draft Object
            Event event = new Event (eventID, title, description, venue, date, time);

            //Saving the Auction Draft
            databaseEvents.child(eventID).setValue(event);

            //setting edittext to blank again
            editTextTitle.setText("");
            editTextDesc.setText("");
            editTextVenue.setText("");
            editTextDate.setText("");
            editTextTime.setText("");

            //displaying a success toast
            Toast.makeText(this, "Event posted!", Toast.LENGTH_SHORT).show();

            finish();
            startActivity(new Intent(getApplicationContext(), EventHome.class));

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please fill up the event details", Toast.LENGTH_SHORT).show();
        }
    }
}


