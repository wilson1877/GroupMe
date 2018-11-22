package com.mobileminiproject.wilsondaniel.groupme;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {


    //this method will be called
    //when the token is generated
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //now we will have the token
        String token = FirebaseInstanceId.getInstance().getToken();

        //for now we are displaying the token in the log
        //copy it as this method is called only when the new token is generated
        //and usually new token is only generated when the app is reinstalled or the data is cleared
        Log.d("MyRefreshedToken", token);
    }
}
//Token -> fD8WV_B1wAA:APA91bHcgwbuo0YsBYAGNDp4PPcEItbGA263zfqvImIz2MyTgapLKw8FmbyZ_5ZWvUHggqp1akVuX6qOPkw5EMbV6hfFclbfCHWzCf1vkbrFYqgb_wKhQToBTaAR_Q51hopxp5U_tM2b