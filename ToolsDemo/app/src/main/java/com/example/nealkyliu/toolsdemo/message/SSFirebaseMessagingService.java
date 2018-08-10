package com.example.nealkyliu.toolsdemo.message;


import com.example.nealkyliu.toolsdemo.utils.DebugLogger;
import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/9
 **/
public class SSFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        DebugLogger.d(this, "token", s);
    }
}
