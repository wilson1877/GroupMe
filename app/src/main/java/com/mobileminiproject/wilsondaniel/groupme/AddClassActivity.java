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

public class AddClassActivity extends AppCompatActivity {

    EditText inputClassName, inputClassDescription;
    Button createButton, cancelButton;
    List<User> classes;
    DatabaseReference databaseClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);

        databaseClasses = FirebaseDatabase.getInstance().getReference("classes");

        inputClassName = (EditText) findViewById(R.id.inputClassName);
        inputClassDescription = (EditText) findViewById(R.id.inputClassDescription);
        createButton = (Button) findViewById(R.id.createButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        classes = new ArrayList<>();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == createButton){
                    addClass();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == cancelButton){
                    finish();
                    startActivity(new Intent(getApplicationContext(), LecturerActivity.class));
                }
            }
        });
    }

    private void addClass() {

        String className = inputClassName.getText().toString().trim();
        String classDescription = inputClassDescription.getText().toString().trim();

        if (!TextUtils.isEmpty(className) && !TextUtils.isEmpty(classDescription)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our User
            String classID = databaseClasses.push().getKey();

            //creating a Class Object
            Class classes = new Class(classID, className, classDescription, User.userID);

            //Saving the User
            databaseClasses.child(classID).setValue(classes);

            //setting edittext to blank again
            inputClassName.setText("");
            inputClassDescription.setText("");

            //displaying a success toast
            Toast.makeText(this, "Class created", Toast.LENGTH_LONG).show();

            finish();
            startActivity(new Intent(getApplicationContext(), LecturerActivity.class));

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please complete the form", Toast.LENGTH_LONG).show();
        }
    }
}
