package com.example.kanishk.hackmit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import watch.nudge.gesturelibrary.AbstractGestureClientActivity;
import watch.nudge.gesturelibrary.GestureConnectionConstants;
import watch.nudge.gesturelibrary.GestureConstants;

import static watch.nudge.gesturelibrary.GestureConstants.SubscriptionGesture;

public class MainActivity extends AbstractGestureClientActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;
    private Vibrator vibrator;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayList<String> listItems2=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    List<Pair<String,Integer>> list = new ArrayList<Pair<String,Integer>>();
    ListView listview;
    ArrayList<String> contactsArray = new ArrayList<String>();
    BroadcastReceiver contactsReceiver;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;
        setContentView(R.layout.activity_main);
        setAmbientEnabled();
        setSubscribeWindowEvents(true);

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Pair<String,Integer> ob = new Pair<String,Integer>("test", 999);
        list.add(0, ob);
        IntentFilter filter = new IntentFilter("contacts");
        contactsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Here","Im here");
                String jsonContacts = intent.getStringExtra(GestureConnectionConstants.CUSTOM_KEY1);
                try {
                    JSONArray jsonArrayContacts = new JSONArray(jsonContacts);
                    if(jsonArrayContacts != null) {
                        contactsArray.clear();
                        for (int i = 0; i < jsonArrayContacts.length(); i++) {
                            contactsArray.add(jsonArrayContacts.get(i).toString());
                        }
                    }
                    listview = (ListView) findViewById(R.id.contacts);
                    adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, contactsArray);
                    listview.setAdapter(adapter);
                    listview.setSelection(index);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };



        LocalBroadcastManager.getInstance(this).registerReceiver(contactsReceiver, filter);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(contactsReceiver);
    }

    @Override
    public void onSnap() {
        Toast.makeText(this, "Feeling snappy!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFlick() {

    }

    @Override
    public void onTwist() {
        Toast.makeText(this, "Feeling twisty!", Toast.LENGTH_LONG).show();
        vibrator.vibrate(250);

    }

    @Override
    public void onTiltX(float v) {
        Toast.makeText(this, "Feeling Tiltxxx!", Toast.LENGTH_LONG).show();
        if(v == 2 || v == 4 || v == 6 || v ==8){
            index++;
            vibrator.vibrate(250);
            listview.setSelection(index);
        }else if(v == -2 || v == -4 || v == -6 || v == -8){
            index --;
            vibrator.vibrate(250);
            listview.setSelection(index);
        }
    }

    @Override
    public void onTilt(float v, float v1, float v2) {

    }

    @Override
    public void onGestureWindowClosed() {

    }

    @Override
    public ArrayList<GestureConstants.SubscriptionGesture> getGestureSubscpitionList() {
        ArrayList<GestureConstants.SubscriptionGesture> gestures = new ArrayList<SubscriptionGesture>();
        gestures.add(SubscriptionGesture.TWIST);
        gestures.add(SubscriptionGesture.TILT);
        gestures.add(SubscriptionGesture.SNAP);
        gestures.add(GestureConstants.SubscriptionGesture.FLICK);
        return gestures;
    }

    @Override
    public boolean sendsGestureToPhone() {
        return true;
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
//        if (isAmbient()) {
//            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
//            mTextView.setTextColor(getResources().getColor(android.R.color.white));
//            mClockView.setVisibility(View.VISIBLE);
//
//            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
//        } else {
//            mContainerView.setBackground(null);
//            mTextView.setTextColor(getResources().getColor(android.R.color.black));
//            mClockView.setVisibility(View.GONE);
//        }
    }
}
