package com.example.pc.ing1_.Menu.Friend;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pc.ing1_.Menu.Friend.multi_chat.Multi_Chat_Activity;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.aaa.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Socket_Service extends Service {
    Socket socket;
    static BufferedReader in;
    public static PrintWriter out;
    String no;
    public static  int nnn;
    Notification Notifi;
    public Socket_Service(){

    }

    IBinder iBinder=new BindServiceBinder();
    CallBack_ callBack_;
    public interface  CallBack_{
        public void Call(String data);
    }
    MCallback mCallBack;
    public interface  MCallback{
        public void MCall(String data);
    }
    public  void registerCallback(CallBack_ cb){
        callBack_=cb;
    }
    public  void mregisterCallback(MCallback cb){
        mCallBack=cb;
    }


    public void Func(String data){
        Log.d("바인드","call");
        JSONObject json= null;
        int no = -100;
        boolean single = false;
        try {
            json = new JSONObject(data+"");
            no = json.getInt("from");
            single= Boolean.parseBoolean(json.getString("single"));
            Log.d("싱글",single+"");

        } catch (JSONException e) {
//            e.printStackTrace();
            Log.d("통과","실패");
            mCallBack.MCall(data);
        }
        Log.d("통과0","");
        if(single) {
            //0일때 채팅방 목록 대기실
            //-1일때는 무조건 노티받아야함 퍼즈 상태
            //nnn==no일땐 해당 채팅방에 있을때 나머지 수신 받아야함
            Log.d("통과1","true");
            if ((nnn != -1 && nnn == no) || nnn == 0) {
                callBack_.Call(data);
            }
            if(nnn==-1||nnn!=no){

                noty(data);
//                Notifi_M.notify(777,Notifi);
            }

        }else{
            try {
                no=Integer.parseInt(json.getString("room_no"));
                Log.d("통과1","123");
            } catch (JSONException e) {
//                e.printStackTrace();
                Log.d("통과1","1233");
            }
            Log.d("통과2","123");
            if(nnn==0||nnn==no){
                Log.d("통과3","123");
                mCallBack.MCall(data);
            }
            try {
                if((nnn==-1||nnn!=no)&&json.getString("sys_message").equals("")){
                    noty1(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class  BindServiceBinder  extends Binder {
        public Socket_Service getService(){
            return Socket_Service.this; // return current service

             }

    }

    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)

        Log.d("test", "서비스의 onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test", "서비스의 start");
        Log.d("test", "서비스의 "+intent.getStringExtra("no"));

nnn=-1;
        no=intent.getStringExtra("no");
                Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket=new Socket("54.180.168.210",5555);
                    out=new PrintWriter(socket.getOutputStream(),true);
                    in=new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    out.println(no);
                    Log.d("서비스",no);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true) {
                try{
                        final String data = in.readLine()+"";
                        if (data != null) {
                            Func(data);
                            Log.d("서비스",data);
                        }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("통과",String.valueOf(e));

                }
                }

            }
        });
        thread.start();


        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("test", "서비스의 onDestroy");
    }

    private void noty(String data){
        String name="";
        String message="";
        String profile="";
        String user_no="";
        try {
            JSONObject jsonObject=new JSONObject(data);
            name=jsonObject.getString("user_name");
            message=jsonObject.getString("message");
            profile=jsonObject.getString("user_image");
            user_no=jsonObject.getString("from");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String finalName = name;
        final String finalMessage = message;
        final Bitmap[] bitmap = new Bitmap[1];
        final String finalUser_no = user_no;
        Glide.with(getApplicationContext()).asBitmap().load("http://54.180.168.210/profile/"+profile).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                bitmap[0] =resource;
                Handler handler=new Handler(Looper.getMainLooper());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Socket_Service.this, Chat_Activity.class);
                        intent.putExtra("user_no", finalUser_no +"");
                        PendingIntent pendingIntent = PendingIntent.getActivity(Socket_Service.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Notifi = new Notification.Builder(getApplicationContext())
                                    .setContentTitle(finalName)
                                    .setContentText(finalMessage)
                                    .setSmallIcon(Icon.createWithBitmap(bitmap[0]))
                                    .setTicker("알림")
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                                    .build();
                        }
                        Log.d("노티","노티");

                        NotificationManager Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        Notifi_M.notify(777,Notifi);
                    }
                },0);
            }
        });

    }



    private void noty1(String data){
        String name="";
        String message="";
        String room_no="";
        try {
            JSONObject jsonObject=new JSONObject(data);
            name=jsonObject.getString("names");
            message=jsonObject.getString("message");
            room_no=jsonObject.getString("room_no");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String finalName = name;
        final String finalMessage = message;
        final String finalUser_no = room_no;



        Handler handler=new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Socket_Service.this, Multi_Chat_Activity.class);
                intent.putExtra("room_no", finalUser_no +"");
                PendingIntent pendingIntent = PendingIntent.getActivity(Socket_Service.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Notifi = new Notification.Builder(getApplicationContext())
                            .setContentTitle(finalName)
                            .setContentText(finalMessage)
                            .setTicker("알림")
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .build();
                }
                Log.d("노티","노티");

                NotificationManager Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notifi_M.notify(777,Notifi);
            }
        },0);

    }

}
