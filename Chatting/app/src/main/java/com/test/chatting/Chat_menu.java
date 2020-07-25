package com.test.chatting;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import static com.test.chatting.Fragment_myfriend_page.friend_List;

public class Chat_menu extends Activity {
    ArrayList<Friend_data> chat_member_list = new ArrayList<>();
    RecyclerView group_chat_recycler;
    RecyclerView.LayoutManager layoutManager;
    MyFriendAdapter myFriendAdapter = new MyFriendAdapter(chat_member_list);
    private Chat_room_info Chatting_room_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);
        set_RecyclerView();

        Chatting_room_info = (Chat_room_info) getIntent().getSerializableExtra("Chat_member");



        if(Chatting_room_info.isthis_chatroom_group){ // 그룹대화인경우
            for(int i=0; i<Chatting_room_info.group_id.size(); i++){
                Friend_data friend_data = new Friend_data();
                friend_data.Friend_id = Chatting_room_info.group_id.get(i);
                chat_member_list.add(friend_data);
            }
        }else {
            Log.d("WOW", Chatting_room_info.id1 + " / " + Chatting_room_info.id2 + " / ");
            Friend_data friend_data1 = new Friend_data();
            friend_data1.Friend_id = Chatting_room_info.id1;
            Friend_data friend_data2 = new Friend_data();
            friend_data2.Friend_id = Chatting_room_info.id2;
            chat_member_list.add(friend_data1);
            chat_member_list.add(friend_data2);
        }

        myFriendAdapter.notifyDataSetChanged();


    }

    private void set_RecyclerView() {

        group_chat_recycler = findViewById(R.id.chat_menu_recycler);
        group_chat_recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        group_chat_recycler.setLayoutManager(layoutManager);
        group_chat_recycler.setAdapter(myFriendAdapter);
    }
}
