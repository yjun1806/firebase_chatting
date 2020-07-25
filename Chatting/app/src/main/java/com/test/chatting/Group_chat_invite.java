package com.test.chatting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static com.test.chatting.Fragment_myfriend_page.friend_List;

public class Group_chat_invite extends Activity {

    RecyclerView group_chat_recycler;
    RecyclerView.LayoutManager layoutManager;
    MyFriendAdapter myFriendAdapter = new MyFriendAdapter(friend_List);
    ArrayList<String> select_friend_list = new ArrayList<>();

    private GestureDetector gestureDetector; // 다양한 터치 이벤트를 처리하는 클래스, 길게누르기, 두번누르기 등등..


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_invite);

        set_RecyclerView();
        select_friend_list.add(MainActivity.Login_id);
        for(int i=0; i< friend_List.size(); i++)
            friend_List.get(i).Friend_select = false;

        gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener() {

            //누르고 뗄 때 한번만 인식하도록 하기위해서
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        // 리사이클러뷰 아이템 터치시 이벤트 구현
        group_chat_recycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child_view = rv.findChildViewUnder(e.getX(), e.getY());
                if(child_view != null  && gestureDetector.onTouchEvent(e)){
                    int currentposition = rv.getChildAdapterPosition(child_view);
                    Log.i(getClass().toString(), "Touch position : " + currentposition);
                    if(friend_List.get(currentposition).Friend_select) {
                        friend_List.get(currentposition).Friend_select = false;

                        for(int i=0; i<select_friend_list.size(); i++){
                            if(select_friend_list.get(i).equals(friend_List.get(currentposition).Friend_id)){
                                select_friend_list.remove(i);
                            }
                        }

                    }else {
                        friend_List.get(currentposition).Friend_select = true;
                        select_friend_list.add(friend_List.get(currentposition).Friend_id);
                    }

                    myFriendAdapter.notifyDataSetChanged();

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    private void set_RecyclerView() {

        group_chat_recycler = findViewById(R.id.group_recycler);
        group_chat_recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        group_chat_recycler.setLayoutManager(layoutManager);
        group_chat_recycler.setAdapter(myFriendAdapter);
    }

    public void onClick_finish(View view) {
        finish();
    }

    public void onClick_invite(View view) {
        if(select_friend_list.size() == 2){ // 1:1 채팅이 되는 경우
            String ChattingRoom_id;
            if(select_friend_list.get(0).compareTo(select_friend_list.get(1))>0){ // A가 B보다 큰경우
                ChattingRoom_id = select_friend_list.get(0) + select_friend_list.get(1);
            }else {
                ChattingRoom_id = select_friend_list.get(1) + select_friend_list.get(0);
            }

            Chat_room_info chat_room_info = new Chat_room_info();
            chat_room_info.id1 = select_friend_list.get(0); // 대화상대 1
            chat_room_info.id2 = select_friend_list.get(1); // 대화상대 2
            chat_room_info.last_message = ""; // 초기 대화메세지값은 없음
            chat_room_info.last_message_time = ""; // 공백으로 채운다.
            chat_room_info.last_message_id = null;
            chat_room_info.Chatting_room_id = ChattingRoom_id; // 채팅방 아이디를 정해준다.
            Intent intent = new Intent(getApplicationContext(), Chatting_Window.class);
            intent.putExtra("Chatting_room_id", chat_room_info);
            startActivity(intent);
            finish();

        }else { // 그룹채팅이 되는 경우
            Chat_room_info chat_room_info = new Chat_room_info(select_friend_list);
            Intent intent = new Intent(getApplicationContext(), Chatting_Window.class);
            intent.putExtra("Chatting_room_id", chat_room_info);
            startActivity(intent);
            finish();
        }

    }


}
