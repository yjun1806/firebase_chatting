package com.test.chatting;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class UI_update_service extends Service {

    //Update_Handler mHandler = new Update_Handler(R.id.hendler_test);
    BackgroundThread backgroundThread;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private NotificationManager manager;
    private String CHANNEL_ID = "FIREBASE_NOTI_CHANNEL";

    ChildEventListener childEventListener;

    public UI_update_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
       /* backgroundThread = new BackgroundThread();
        backgroundThread.setRunning(true);
        backgroundThread.start();*/
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.i("Ui 서비스", "스레드 시작");
        createNotificationChannel();

        // 데이터베이스 감지 부분
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.i("서비스", "추가!! : ");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Chat_room_info chat_room_info = dataSnapshot.getValue(Chat_room_info.class);
                Log.i("서비스", "변화!! : " +  chat_room_info.last_message);

                //내가 현재 보고있는 채팅방의 아이디와 변화가 있는 채팅방의 아이디가 같으면 알림 안함
                if (!chat_room_info.Chatting_room_id.equals(MainActivity.now_watching_chat_room_id)) {

                    // 마지막 메세지를 보낸 아이디가 내가 아니면 알림을 띄워준다.
                    if(!chat_room_info.last_message_id.equals(MainActivity.Login_id)){
                        sendNotification(chat_room_info.last_message_id, chat_room_info.last_message, chat_room_info);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("서비스", "제거!! : " );

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i("서비스", "이동!! : ");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("서비스", "취소!! : ");

            }
        };
        databaseReference.child("MemberData").child(MainActivity.Login_id).child("Chat_room_list").addChildEventListener(childEventListener);



        return super.onStartCommand(intent, flags, startId);
    }

    public class BackgroundThread extends Thread{
        boolean running = false;
        void setRunning(boolean b){
            running = b;
        }

        @Override
        public void run() {
           /* while(running){
                try {
                    Log.d("서비스 스레드", "1초 쉬기 직전");

                    sleep(3000);
*/


             /*   } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //mHandler.sendMessage(mHandler.obtainMessage());
            }*/
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* backgroundThread.setRunning(false);
        backgroundThread.interrupt();*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(999);
        Log.i("Ui 서비스", "서비스 중지");

    }

    private void sendNotification(String senderID, String message, Chat_room_info chat_room_info) {
        Log.i("변화탐지 서비스", "sendNotification");


        Intent intent = new Intent(this, Chatting_Window.class);
        intent.putExtra("Chatting_room_id", chat_room_info);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_info))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("새로운 메세지가 도착했습니다.")
                .setContentText(senderID +"님의 메세지 :  "+message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(999 /* ID of notification */, notificationBuilder.build());


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "CHATTING_CHANNEL",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
