//package com.example.noor.fypv3;
//
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.NotificationManagerCompat;
//import android.view.View;
//import android.widget.Toast;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ApiCallService  extends Service {
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//
//
//
//
//        return Service.START_NOT_STICKY;
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    public void getBullyStatus(List<String> names){
//
//        Call<ResponseClass> call = MainScreen.apiInterfaceForAiModule.getAllBullyStatus(names);
//        //progressBar.setVisibility(View.VISIBLE);
//
//        call.enqueue(new Callback<ResponseClass>() {
//            @Override
//            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
//                try {
//
//                    if(response.body().getResponse().equals("ok"))
//                    {
//
//                        //progressBar.setVisibility(View.INVISIBLE);
//                        if(response.body().getStatus().equals("Bullying")) {
//                            Toast.makeText(getActivity(), "noti", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getActivity(), notifications.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
//
//
//                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "samad")
//                                    .setSmallIcon(R.drawable.alert)
//                                    .setContentTitle("Bully Alert")
//                                    .setContentText("We found some suspicious activities in your child's account")
//                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                                    .setContentIntent(pendingIntent)
//                                    .setAutoCancel(true);
//                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
//
//                            // notificationId is a unique int for each notification that you must define
//                            notificationManager.notify(0, builder.build());
//                        }
//                        //tvResponce.setText(response.body().getTweet()+"\n\n"+response.body().getStatus());
//                    }
//                    else if (response.body().getResponse().equals("error"))
//                    {
//                        //progressBar.setVisibility(View.INVISIBLE);
//                        //Toast.makeText(getActivity(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
//                        tvResponce.setText("Something went wrong. Try again");
//
//
//                    }
//
//
//                }catch (NullPointerException e)
//                {
//                    progressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getActivity(), "Can't found account.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseClass> call, Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);
//                tvResponce.setText("Something went wrong. Try again\n"+t.getMessage().toString());
//                //tvResponce.setText("Tweet: Look at your face, ugly!. You should die.\nSender: @mavelloussamad\n\nStatus: Bullying.");
//
//
//
//            }
//        });
//    }
//}
