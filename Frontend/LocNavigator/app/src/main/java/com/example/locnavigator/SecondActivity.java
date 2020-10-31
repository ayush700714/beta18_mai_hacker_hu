package com.example.locnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
   private String phoneNo = "+918960507109";
    TextView send_msg;
    String msg;
    Double lat=0.0,longt=0.0;
    String key="mai_hacker_hu";
    String Destination;
    LocationListener locationListener;
    LocationManager locationManager;
    String json;
    String msgContent="DSFSDFDSFSDFSDFsdf";
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("loc changed", "onLocationChanged: ");
                lat=location.getLatitude();
                longt=location.getLongitude();
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
            Log.d("Dfddfdfd", "fck ");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, 12);
                return;
            }
            msgContent= getFirstSms();
            Log.d("msg",msgContent);
            Log.d("msgwerer","@#$@#$");
        } else {
            Log.d("msgwerer","@#$@#$");
            msgContent= getFirstSms();
            Log.d("msg",msgContent);
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
                  if(!TextUtils.isEmpty(msg))
                  {
                       send_msg = findViewById(R.id.text_message_sent);
                      send_msg.setText(msg);
                      send_msg.setVisibility(View.VISIBLE);
                      json="{"+ "\n   \"key\":"+ " \""+key+"\","+"\n \"lat\":"+ "\""+lat+"\","+"\n \"log\":"+" \""+longt+"\" ,"+"\n \"destination\":"+"\""+msg+"\""+"\n}";
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
                          sendSMS(phoneNo,json);
                      } else {
                          sendSMS(phoneNo,json);
                      }

                  }
                  else
                  {
                      Toast.makeText(SecondActivity.this,"Please enter your location",Toast.LENGTH_SHORT).show();
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
            case 12:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    msgContent= getFirstSms();
                    Log.d("msg",msgContent);
                    Log.d("msgwerer","@#$@#$");
                }
        }
    }
    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
            Log.d("msgwerer","@#$@#$");

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
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
    public String getFirstSms() {
        Uri message = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
        String msgData = "";
        Cursor c = cr.query(message, null, null, null, null);
        startManagingCursor(c);
        Log.d("msg",msgContent);
        if (c.moveToFirst()) {
            msgData=c.getString(c.getColumnIndexOrThrow("body"));

        }

        c.close();

        return msgData;
    }
}
