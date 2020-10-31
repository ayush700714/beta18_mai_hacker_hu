package com.example.sms_client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiveSMS extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
            if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                Bundle bundle = intent.getExtras();
                SmsMessage[] msgs = null;
                String from;
                if(bundle!=null){
                    try {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for(int i=0;i<msgs.length;i++){
                            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                            from = msgs[i].getOriginatingAddress();
                            String body = msgs[i].getMessageBody();
                            Toast.makeText(context,from+body,Toast.LENGTH_LONG).show();
                            RetrofitInit init = new RetrofitInit();
                            Call<Void> call = init.init().connect();
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.code()==200){

                                        Toast.makeText(context,"success",Toast.LENGTH_LONG).show();
                                        Log.e("Shivam","success");
                                    }else{
                                        Log.e("Shivam","404");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("Shivam",t.getMessage());
                                }
                            });
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}
