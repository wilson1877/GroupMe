package com.mobileminiproject.wilsondaniel.groupme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementHome extends AppCompatActivity {

    //view objects
    ListView listViewAnnouncements;
    CardView cardView;
    TextView textViewTitle, textViewDesc;
    Toolbar mToolbar;


    //our database reference object
    DatabaseReference databaseArtists;

    //a list to store all the artist from firebase database
    List<Announcement> announcements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_home);

//        Toolbar mToolbar= (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                startActivity(new Intent(getApplicationContext(), CreateAnnouncement.class));
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),CreateAnnouncement.class);
                startActivity(intent);
            }
        });

        //getting the reference of artists node
        databaseArtists = FirebaseDatabase.getInstance().getReference("announcements");

        //getting views
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        cardView = (CardView) findViewById(R.id.cardView);
        listViewAnnouncements = findViewById(R.id.listViewAnnouncements);

        //list to store artists
        announcements = new ArrayList<>();

        listViewAnnouncements.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Announcement announcement = announcements.get(i);
                showUpdateDeleteDialog(announcement.getAnnouncementID(), announcement.getTitle());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                announcements.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Announcement announcement = postSnapshot.getValue(Announcement.class);
                    //adding artist to the list
                    announcements.add(announcement);
                }

                //creating adapter
                AnnouncementList announcementAdapter = new AnnouncementList(AnnouncementHome.this, announcements);
                //attaching adapter to the listview
                listViewAnnouncements.setAdapter(announcementAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean updateAnnouncement(String announcementID, String title, String description) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("announcements").child(announcementID);

        //updating artist
        Announcement announcement = new Announcement(announcementID, title, description);
        dR.setValue(announcement);
        Toast.makeText(getApplicationContext(), "Announcement Updated", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean deleteAnnouncement(String announcementID) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("announcements").child(announcementID);

        //removing artist
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Announcement Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String announcementID, String title) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_announcement_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextDesc = (EditText) dialogView.findViewById(R.id.editTextDesc);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateBtn);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteBtn);

        dialogBuilder.setTitle(title);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString().trim();
                String description = editTextDesc.getText().toString().trim();
                if (!TextUtils.isEmpty(title)) {
                    updateAnnouncement(announcementID, title, description);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAnnouncement(announcementID);
                b.dismiss();
            }
        });
    }


}
