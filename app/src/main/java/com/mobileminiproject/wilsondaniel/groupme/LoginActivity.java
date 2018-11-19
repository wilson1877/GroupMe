package com.mobileminiproject.wilsondaniel.groupme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonSignin;
    TextView textViewSignUp;
    List<User> userList;

    //our database reference object
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //getting the reference of users node
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignin = findViewById(R.id.buttonSignin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        //list to store users
        userList = new ArrayList<>();

        //adding an onclicklistener to button
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == buttonSignin){
                    validateLogin();
                }
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == textViewSignUp){
                    finish();
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous user list
                userList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting users
                    User user = postSnapshot.getValue(User.class);
                    //adding artist to the list
                    userList.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void validateLogin() {

        //getting the login input values
        String emailIn = editTextEmail.getText().toString().trim();
        String passwordIn = editTextPassword.getText().toString().trim();
        Boolean isFound = false;

        for (int x=0; x<userList.size(); x++) {
            String email = userList.get(x).getEmailAddress();
            String password = userList.get(x).getPassword();

//            System.out.println(email);
//            System.out.println(password);

            if (emailIn.isEmpty()) {
                Toast.makeText(this, "Please enter your email address!", Toast.LENGTH_SHORT).show();
                break;
            }
            if (passwordIn.isEmpty()) {
                Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
                break;
            }
            if (emailIn.equals(email) && passwordIn.equals(password)) {
                isFound = true;
                break;
            }
        }
        if (isFound == true) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
    }
}