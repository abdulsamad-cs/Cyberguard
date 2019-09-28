package com.example.noor.fypv3;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleJobIntentService extends JobIntentService {

    private static final String TAG =SimpleJobIntentService.class.getSimpleName(); ;
    final Handler handler=new Handler();
    // Unique job id for this service
    static final int JOB_ID=5460;
    // method to enqueue work in this service
    static void enqueueWork(Context context, Intent work){
        //Toast.makeText(context, "enqueing", Toast.LENGTH_SHORT).show();
        enqueueWork(context,SimpleJobIntentService.class,JOB_ID,work);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        // You have received the work intent now do api call here
        Log.d(TAG,"Launching api call: "+intent.getStringExtra("twitterHandle"));
        getBullyStatus(intent.getStringExtra("twitterHandle"));
        //toast("Hey from service, will meet you again after 30 sec");
    }
    // Helper for showing tests
    void toast(final CharSequence text) {
        handler.post(new Runnable() {
            @Override public void run() {
                Toast.makeText(SimpleJobIntentService.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // service is destroying, release any holding resource here
    }

    public void getBullyStatus(final String twitterHandle){

        Call<ResponseClass> call = MainScreen.apiInterfaceForAiModule.getBullyStatus(twitterHandle);
        //progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                try {
                    if(response.body().getResponse().equals("ok"))
                    {
                        //progressBar.setVisibility(View.INVISIBLE);
                        if(response.body().getStatus().equals("Bullying")) {
                            //Toast.makeText(SimpleJobIntentService.this, "noti", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SimpleJobIntentService.this, childListsMAin.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent.getActivity(SimpleJobIntentService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                            NotificationCompat.Builder builder = new NotificationCompat.Builder(SimpleJobIntentService.this, "samad")
                                    .setSmallIcon(R.drawable.alert)
                                    .setContentTitle("Bully Alert - "+twitterHandle)
                                    .setContentText("We found some suspicious activities in your child's account")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(SimpleJobIntentService.this);

                            // notificationId is a unique int for each notification that you must define
                            notificationManager.notify(0, builder.build());
                        }
                        //tvResponce.setText(response.body().getTweet()+"\n\n"+response.body().getStatus());
                    }
                    else if (response.body().getResponse().equals("error"))
                    {
                        //progressBar.setVisibility(View.INVISIBLE);
                        //Toast.makeText(getActivity(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
                        //tvResponce.setText("Something went wrong. Try again");


                    }


                }catch (NullPointerException e)
                {
                    //progressBar.setVisibility(View.INVISIBLE);
                    //Toast.makeText(getActivity(), "Can't found account.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseClass> call, Throwable t) {
                //progressBar.setVisibility(View.INVISIBLE);
                //tvResponce.setText("Something went wrong. Try again\n"+t.getMessage().toString());
                //tvResponce.setText("Tweet: Look at your face, ugly!. You should die.\nSender: @mavelloussamad\n\nStatus: Bullying.");



            }
        });
    }
}
