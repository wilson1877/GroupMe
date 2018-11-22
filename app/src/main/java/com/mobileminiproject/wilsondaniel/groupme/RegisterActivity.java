package com.mobileminiproject.wilsondaniel.groupme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.mobileminiproject.wilsondaniel.groupme.User.chatWith;

public class RegisterActivity extends AppCompatActivity {

    public static final String USER_ID = ".userID";
    public static final String USER_NAME = ".name";
    public static final String CONTACT_NO = ".contactNo";
    public static final String EMAIL_ADD = ".emailAddress";
    public static final String PASSWORD = ".password";
    public static final String IS_STUDENT = ".isStudent";
    public static final String CHATWITH = ".chatWith";

    EditText editTextName, editTextContact, editTextEmail, editTextPassword;
    RadioGroup userType;
    RadioButton isLecturer, isStudent;
    Button buttonSignup;
    TextView textViewSignin;

    List<User> users;

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //getting the reference of users node
        databaseUser = FirebaseDatabase.getInstance().getReference("users");

        //getting views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        isLecturer = (RadioButton) findViewById(R.id.isLecturer);
        isStudent = (RadioButton) findViewById(R.id.isStudent);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        //list to store users
        users = new ArrayList<>();

        //adding an onclicklistener to button
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == buttonSignup){
                    addUser();
                }
            }
        });

        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == textViewSignin){
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
    }

    private void addUser() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
        String contactNo = editTextContact.getText().toString().trim();
        int contactNO = Integer.parseInt(contactNo);
        String emailAddress = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        boolean userType = isStudent.isChecked();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(contactNo) && !TextUtils.isEmpty(emailAddress) &&
                !TextUtils.isEmpty(password)) {

                //getting a unique id using push().getKey() method
                //it will create a unique id and we will use it as the Primary Key for our User
                String userID = databaseUser.push().getKey();

                //creating an User Object
                User users = new User(userID, name, contactNO, emailAddress, password, userType, chatWith);

                //Saving the User
                databaseUser.child(userID).setValue(users);

                //setting edittext to blank again
                editTextName.setText("");
                editTextContact.setText("");
                editTextEmail.setText("");
                editTextPassword.setText("");
                isStudent.setChecked(false);

                //displaying a success toast
                Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();

                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please complete the register form", Toast.LENGTH_LONG).show();
        }
    }
}