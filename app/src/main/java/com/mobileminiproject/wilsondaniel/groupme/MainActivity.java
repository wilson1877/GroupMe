package com.mobileminiproject.wilsondaniel.groupme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLecturer, buttonStudent;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        buttonLecturer = (Button) findViewById (R.id.buttonLecturer);
        buttonStudent = (Button) findViewById (R.id.buttonStudent);

        buttonLecturer.setOnClickListener(this);
        buttonStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == buttonLecturer){
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == buttonStudent){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, RegisterActivity.class));
        }

    }
}
