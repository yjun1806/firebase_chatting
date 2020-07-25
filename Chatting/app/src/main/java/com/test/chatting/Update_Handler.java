package com.test.chatting;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;


public class Update_Handler extends Handler {
    TextView mActivitiy;
    int i=0;

    Update_Handler(int activity){
        mActivitiy.findViewById(activity);
    }


    @Override
    public void handleMessage(Message msg) {
        Log.i("핸들러", i + "바꿈");
        mActivitiy.setText(String.valueOf(i++));

    }
}
