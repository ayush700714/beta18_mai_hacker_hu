package com.example.locnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements ReceiveSMS.MessageListener {
    private String phoneNo = "+918960507109";
    TextView send_msg;
    String msg;
    Double lat = 0.0, longt = 0.0;
    String key = "mai_hacker_hu";
    String Destination;
    LocationListener locationListener;
    LocationManager locationManager;
    String json;
    String msgContent = "DSFSDFDSFSDFSDFsdf";
    int i = 0;
    int p = 0;
    ArrayList<location> a = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ReceiveSMS.bindListener(this);
        recyclerView = findViewById(R.id.recycle);
         adapter = new RecycleAdapter(a);
        LinearLayoutManager l = new LinearLayoutManager(SecondActivity.this);
        l.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(SecondActivity.this, 2));
        recyclerView.setAdapter(adapter);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("loc changed", "onLocationChanged: ");
                if (p == 0) {
                    Toast.makeText(SecondActivity.this, "Location set", Toast.LENGTH_SHORT).show();
                    p = 1;
                }
                lat = location.getLatitude();
                longt = location.getLongitude();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("BROADCAST", "RECIEVER");
            if (ActivityCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 12);
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("Dfddfdfd", "configButton:231232 ");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            configButton();
        } else {
            configButton();
        }


        final Button send2 = (Button) findViewById(R.id.send2);
        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.edittext_chatbox);
                msg = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(msg)) {

                    json = "{" + "\n   \"key\":" + " \"" + key + "\"," + "\n \"lat\":" + "\"" + lat + "\"," + "\n \"log\":" + " \"" + longt + "\" ," + "\n \"destination\":" + "\"" + msg + "\"" + "\n}";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Log.d("zxzxzxzxzxd", json);
                        if (ActivityCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        sendSMS(phoneNo, json);
                    } else {
                        sendSMS(phoneNo, json);
                    }

                } else {
                    Toast.makeText(SecondActivity.this, "Please enter your location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS(phoneNo, json);
                }
            case 11:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configButton();
                }

        }
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
            Log.d("msgwerer", "@#$@#$");

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void configButton() {
        Log.d("Dfddfdfd", "configButton: ");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
    }


    @Override
    public void messageReceived(location l1) {
        Log.d("aa bhi ja", "aa gya");
        a.add(l1);
        adapter.notifyDataSetChanged();

    }
}
