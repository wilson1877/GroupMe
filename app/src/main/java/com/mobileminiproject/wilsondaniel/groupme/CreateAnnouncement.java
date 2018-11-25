package com.mobileminiproject.wilsondaniel.groupme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateAnnouncement extends AppCompatActivity {

    EditText editTextTitle, editTextDesc;
    Button postBtn, viewBtn;

    List<Announcement> Announcements;

    DatabaseReference databaseAuctionDraft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);

        //getting the reference of users node
        databaseAuctionDraft = FirebaseDatabase.getInstance().getReference("announcements");

        //getting views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDesc = (EditText) findViewById(R.id.editTextDesc);
        postBtn = findViewById(R.id.postBtn);
        viewBtn = findViewById(R.id.viewBtn);

        //list to store users
        Announcements = new ArrayList<>(); //should be in login, not used here.

        //adding an onclicklistener to button
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == postBtn) {
                    postAnnouncement();
                }
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == viewBtn) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), AnnouncementHome.class));
                }
            }
        });
    }


    private void postAnnouncement() {
        //getting the values to save
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDesc.getText().toString().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(title)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Auction Draft
            String announcementID = databaseAuctionDraft.push().getKey();

            //creating an Auction Draft Object
            Announcement announcement = new Announcement (announcementID, title, description);

            //Saving the Auction Draft
            databaseAuctionDraft.child(announcementID).setValue(announcement);

            //setting edittext to blank again
            editTextTitle.setText("");
            editTextDesc.setText("");

            //displaying a success toast
            Toast.makeText(this, "Announcement posted!", Toast.LENGTH_SHORT).show();

            finish();
            startActivity(new Intent(getApplicationContext(), AnnouncementHome.class));

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please fill up the announcement details", Toast.LENGTH_SHORT).show();
        }
    }
}

