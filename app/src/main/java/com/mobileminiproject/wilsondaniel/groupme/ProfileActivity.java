package com.mobileminiproject.wilsondaniel.groupme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLogout;
    private Button buttonPrivateChat;
    private Button buttonGroupChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonPrivateChat = (Button) findViewById(R.id.buttonPrivateChat);
        buttonGroupChat = (Button) findViewById(R.id.buttonGroupChat);
        buttonLogout.setOnClickListener(this);
        buttonPrivateChat.setOnClickListener(this);
        buttonGroupChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            Toast.makeText(this, "Logout successful!", Toast.LENGTH_SHORT).show();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == buttonPrivateChat){
            finish();
            startActivity(new Intent(this, UserListActivity.class));
        }

        if(view == buttonGroupChat){
            finish();
            startActivity(new Intent(this, GroupChatActivity.class));
        }
    }
}
