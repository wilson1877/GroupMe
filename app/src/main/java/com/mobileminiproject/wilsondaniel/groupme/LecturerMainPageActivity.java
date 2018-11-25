package com.mobileminiproject.wilsondaniel.groupme;

import android.os.Bundle;
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

import static com.mobileminiproject.wilsondaniel.groupme.Class.lecturerID;

public class LecturerMainPageActivity extends AppCompatActivity {

    ListView listViewClasses;
    CardView cardView;
    TextView textViewName, textViewDesc;

    //our database reference object
    DatabaseReference databaseClasses;

    List<Class> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseClasses = FirebaseDatabase.getInstance().getReference("classes");

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        cardView = (CardView) findViewById(R.id.cardView);
        listViewClasses = findViewById(R.id.listViewClasses);

        //list to store classes
        classes = new ArrayList<>();

        listViewClasses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Class claSS = classes.get(i);
                showUpdateDeleteDialog(claSS.getClassID(), claSS.getClassName());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseClasses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous class list
                classes.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting class
                    Class claSS = postSnapshot.getValue(Class.class);
                    //adding class to the list
                    classes.add(claSS);
                }

                //creating adapter
                ClassList classAdapter = new ClassList(LecturerMainPageActivity.this, classes);
                //attaching adapter to the listview
                listViewClasses.setAdapter(classAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean updateClass(String classID, String className, String classDescription, String lecturerID) {
        //getting the specified class reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("classes").child(classID);

        //updating class
        Class claSS = new Class(classID, className, classDescription, User.userID);
        dR.setValue(claSS);
        Toast.makeText(getApplicationContext(), "Class Updated", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean deleteClass(String classID) {
        //getting the specified class reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("classes").child(classID);

        //removing class
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Class Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String classID, String className) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_delete_class_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextDesc = (EditText) dialogView.findViewById(R.id.editTextDesc);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateBtn);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteBtn);

        dialogBuilder.setTitle(className);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = editTextName.getText().toString().trim();
                String classDescription = editTextDesc.getText().toString().trim();
                if (!TextUtils.isEmpty(className) && !TextUtils.isEmpty((classDescription))) {
                    updateClass(classID, className, classDescription, lecturerID);
                    b.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please fill in the form", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteClass(classID);
                b.dismiss();
            }
        });
    }
}
