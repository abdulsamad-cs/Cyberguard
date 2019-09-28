package com.example.noor.fypv3;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import org.reactivestreams.Subscription;

//import java.util.Observable;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;





/**
 * A simple {@link Fragment} subclass.
 */
public class childActivities extends Fragment implements View.OnClickListener {


    TextView tv,tvResponce;
    Button btnMonitor,btnalerts;
    ProgressBar progressBar;
    String twitterHandle="";
    ImageView imgbackactivities;
    //FragmentManager fm=getActivity().getSupportFragmentManager();
    public childActivities() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_child_activities, container, false);
        progressBar = v.findViewById(R.id.progressBar);
        Bundle bundle = this.getArguments();
        imgbackactivities = (ImageView) v.findViewById(R.id.imgBackActivities);
        imgbackactivities.setOnClickListener(this);
        tv= (TextView) v.findViewById(R.id.txt);
        twitterHandle=bundle.getString("twitterHandle");
        tv.setText(twitterHandle+" - "+bundle.getString("child_email"));
        btnMonitor= (Button) v.findViewById(R.id.btnMonitor);
        btnalerts=(Button) v.findViewById(R.id.btn_start_service);
        btnalerts.setOnClickListener(this);
        btnMonitor.setOnClickListener(this);
        tvResponce=(TextView) v.findViewById(R.id.response);


        return v;
    }

    public void getBullyStatus(){

        Call<ResponseClass> call = MainScreen.apiInterfaceForAiModule.getBullyStatus(twitterHandle);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                try {
                    if(response.body().getResponse().equals("ok"))
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        if(response.body().getStatus().equals("Bullying")) {
                            //Toast.makeText(getActivity(), "noti", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), notifications.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);


                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "samad")
                                    .setSmallIcon(R.drawable.alert)
                                    .setContentTitle("Bully Alert - "+twitterHandle)
                                    .setContentText("We found some suspicious activities in your child's account")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

                            // notificationId is a unique int for each notification that you must define
                            notificationManager.notify(0, builder.build());
                        }
                        tvResponce.setText(response.body().getTweet()+"\n\n"+response.body().getStatus());
                    }
                    else if (response.body().getResponse().equals("error"))
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        //Toast.makeText(getActivity(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
                        tvResponce.setText("Something went wrong. Try again");


                    }


                }catch (NullPointerException e)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Can't found account.", Toast.LENGTH_SHORT).show();
                }
                           }

            @Override
            public void onFailure(Call<ResponseClass> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                tvResponce.setText("Something went wrong. Try again\n"+t.getMessage().toString());
                //tvResponce.setText("Tweet: Look at your face, ugly!. You should die.\nSender: @mavelloussamad\n\nStatus: Bullying.");



            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==btnMonitor){
            getBullyStatus();
        }
        else if (view==btnalerts)
        {

            scheduleTask(30000);
            Toast.makeText(getActivity(), "alerts on", Toast.LENGTH_SHORT).show();
        }
        /*if (view==btnSchedule){
            BackgroundService service;
            Intent responseComplete;

            @Nullable
            @Override
            public IBinder onBind(Intent intent) {
                return null;
            }

            @Override
            public int onStartCommand(Intent intent, int flags, int startId) {

                if (!service.isRunning()) {
                    service.start();
                    service.isRunning = true;
                }
                return super.onStartCommand(intent, flags, startId);
            }

            @Override
            public void onCreate() {
                super.onCreate();
                service = new BackgroundService();
                Log.d("CREATED>>>>", "Created");
            }

            @Override
            public void onDestroy() {
                super.onDestroy();
                if (service.isRunning) {
                    service.interrupt();
                    service.isRunning = false;
                    service = null;
                }
            }

            class BackgroundService extends Thread {
                public boolean isRunning = false;
                public long milliSecs = 10000;
                //        Handler networkRequest= new Handler();
                Runnable runTask = new Runnable() {
                    @Override
                    public void run() {
                        isRunning = true;
                        while (isRunning) {
                            try {
                                getBullyStatus();

                                Thread.sleep(milliSecs);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                isRunning = false;
                            }
                        }
                    }
                };

                public boolean isRunning() {
                    return isRunning;
                }

                @Override
                public void run() {
                    super.run();
                    runTask.run();
                }



            }


                   }*/
        if(view==imgbackactivities)
        {

            getActivity().getSupportFragmentManager().popBackStack();
            getActivity().getSupportFragmentManager().beginTransaction().show(getActivity().getSupportFragmentManager().findFragmentByTag("childList")).commit();

        }
    }
    private void scheduleTask(long alarmTime){

        Calendar calendar=Calendar.getInstance();

        Intent serviceIntent=new Intent(getActivity(),AlarmReciever.class);
        // pass any data here if you want [optional]
        serviceIntent.putExtra("twitterHandle",twitterHandle);

        // create pending intent to launch when alarm goes off
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7,serviceIntent,0);
        // get reference to alarm manager
        AlarmManager alarmManager=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        // setting alarm time
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmTime,pendingIntent);

    }
}