package com.example.kanishk.hackmit;

import watch.nudge.gesturelibrary.AppControllerReceiverService;

/**
 * Created by kanishk on 9/19/15.
 */
public class GestureStartReceiver extends AppControllerReceiverService {
    @Override
    protected Class getWatchActivityClass() {
        return MainActivity.class;
    }
}
