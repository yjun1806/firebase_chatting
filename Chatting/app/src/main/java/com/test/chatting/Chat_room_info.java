package com.test.chatting;

import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Chat_room_info implements Serializable{

    String id1;
    String id2;
    String Chatting_room_id;
    String last_message_time;
    String last_message;
    String last_message_id;
    boolean isthis_chatroom_group;
    ArrayList<String> group_id;

    Chat_room_info(){
        this.id1 = null;
        this.id2 = null;
        this.last_message_time = null;
        this.last_message = null;
        this.last_message_id = null;
        this.isthis_chatroom_group = false;
        this.group_id = null;
    }

    Chat_room_info(ArrayList<String> group_id){
        this.isthis_chatroom_group = true;
        this.group_id = group_id;
        this.Chatting_room_id = make_chatroom_id(group_id);
    }

    private String make_chatroom_id(ArrayList<String> select_friend_list) {
        String maked_id = "";

        Collections.sort(select_friend_list, String.CASE_INSENSITIVE_ORDER);
        for(int i=0; i<select_friend_list.size(); i++){
            maked_id += select_friend_list.get(i);
        }

        Log.d("그룹채팅초대", "정렬된 아이디 : " + maked_id);
        return maked_id;

    }
}
