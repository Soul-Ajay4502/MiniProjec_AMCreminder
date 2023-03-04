package com.example.amcremind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class dashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    TextView firebasenameview;
    Button toast;
String id,nm,loc,message;
    private CardView addItems, deleteItems, scanItems, viewInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addItems = (CardView) findViewById(R.id.addItems);
        deleteItems = (CardView) findViewById(R.id.deleteItems);
        scanItems = (CardView) findViewById(R.id.scanItems);
        viewInventory = (CardView) findViewById(R.id.viewInventory);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel("My Notification","channel",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        addItems.setOnClickListener(this);
        deleteItems.setOnClickListener(this);
        scanItems.setOnClickListener(this);
        viewInventory.setOnClickListener(this);

        checkChildRenewal();
    }

    private void checkChildRenewal() {
        FirebaseDatabase.getInstance().getReference().child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Items item = child.getValue(Items.class);
                    if (item != null) {
                        String renewalDateString = item.getRenewal();
                        id=item.getid();
                         nm=item.getItemname();
                        loc=item.getLocation();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date renewalDate = null;
                        try {
                            renewalDate = df.parse(renewalDateString);
                        } catch (Exception e) {
                            Log.e("dashboardActivity", e.getMessage());
                        }
                        if (renewalDate != null) {
                            Calendar renewalCalendar = Calendar.getInstance();
                            renewalCalendar.setTime(renewalDate);

                            Calendar currentCalendar = Calendar.getInstance();
                            currentCalendar.setTime(new Date());

                            long diff = currentCalendar.getTimeInMillis() - renewalCalendar.getTimeInMillis();
                            long daysDiff = diff / (24 * 60 * 60 * 1000);
                            if (daysDiff == 30) {
                                sendSms();
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("dashboardActivity", databaseError.getMessage());
            }
        });
    }

    private void sendSms() {
        String phoneNumber = "9947988394"; // Replace with your phone number
         message = "Renewal of"+" "+nm+" "+"at"+" "+"lab"+loc+" "+"with id"+" "+id+" "+"is due in 30 days.";

        if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(dashboardActivity.this, "My Notification");
            builder.setSmallIcon(R.drawable.notification_icon);
            builder.setContentTitle("AMC");
            builder.setContentText(message);
            builder.setAutoCancel(true);

            // Create a pending intent for the notification, which will open the MainActivity when the user taps the notification
            Intent intent = new Intent(dashboardActivity.this, dashboardActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(dashboardActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            // Create a notification manager
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Send the notification
            notificationManager.notify(1, builder.build());





        }
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.addItems:
                i = new Intent(this, additemActivity.class);
                startActivity(i);
                break;
            case R.id.deleteItems:
                i = new Intent(this, developerActivity.class);
                startActivity(i);
                break;
            case R.id.scanItems:
                // i = new Intent(this, ScanItemActivity.class);
                // startActivity(i);
                Toast.makeText(dashboardActivity.this, "THIS FEATURE WILL BE ADDED", Toast.LENGTH_SHORT).show();

                break;
            case R.id.viewInventory:
                i = new Intent(this, RetreiveDataActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("9947988394", null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SMS sending failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

}
