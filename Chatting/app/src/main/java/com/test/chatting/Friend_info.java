package com.test.chatting;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Friend_info extends Activity {
    TextView fi_id;
    /*SharedPreferences Login_pref;
    SharedPreferences.Editor editor;*/
    ArrayList<String> friend_list = new ArrayList<>();
    HashMap<String, Friend_data> friendkey = new HashMap<>();

    //Firebase 를 이용합시다!
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();


    boolean isFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_friend_info);

        fi_id = findViewById(R.id.fi_ID);
        fi_id.setText(getIntent().getStringExtra("Friend_Id"));
        /*Login_pref = getSharedPreferences(MainActivity.Login_id, MODE_PRIVATE);
        editor = Login_pref.edit();*/



        //Firebase 에서 저장된 정보 가져오기
        databaseReference.child("MemberData").child(MainActivity.Login_id).child("FriendList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Friend_data getData = dataSnapshot.getValue(Friend_data.class);
                friendkey.put(getData.Friend_id, getData);

                isFriend = Checked_friend();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean Checked_friend() {
        String friend = fi_id.getText().toString();

        if(friendkey.get(friend) == null) { // 아직 친구가 아닌 경우

            Button bt = findViewById(R.id.fi_friend_add_button); // 친구추가 버튼
            Button bt2 = findViewById(R.id.fi_friend_chatting_button); // 1:1 채팅 버튼
            bt2.setVisibility(View.GONE);
            bt.setVisibility(View.VISIBLE);
           return false; // 아직 친구가 아니면 false

        }else {
            Button bt = findViewById(R.id.fi_friend_add_button);
            Button bt2 = findViewById(R.id.fi_friend_chatting_button);
            bt2.setVisibility(View.VISIBLE);
            bt.setVisibility(View.GONE);
            return true;
        }
    }

    //친구정보에서 1:1 채팅버튼을 누르는 경우
    public void onClick_1to1Chatting(View view) {

        Intent intent = new Intent(this, Chatting_Window.class);
        String ChattingRoom_id;

        //채팅방 이름을 상대방 아이디+내아이디의 조합으로 만드는데, 알파벳 순서로 만든다.
        if(MainActivity.Login_id.compareTo(fi_id.getText().toString())>0){ // A가 B보다 큰경우
            ChattingRoom_id = MainActivity.Login_id + fi_id.getText().toString();
        }else {
            ChattingRoom_id = fi_id.getText().toString() + MainActivity.Login_id;
        }

        // 채팅방 기본 정보를 담는 부분
        Chat_room_info chat_room_info = new Chat_room_info();
        chat_room_info.id1 = MainActivity.Login_id; // 대화상대 1
        chat_room_info.id2 = fi_id.getText().toString(); // 대화상대 2
        chat_room_info.last_message = ""; // 초기 대화메세지값은 없음
        chat_room_info.last_message_time = ""; // 공백으로 채운다.
        chat_room_info.last_message_id = null;
        chat_room_info.Chatting_room_id = ChattingRoom_id; // 채팅방 아이디를 정해준다.
        intent.putExtra("Chatting_room_id", chat_room_info);
        /*databaseReference.child("MemberData").child(MainActivity.Login_id).child("Chat_room_list").child(ChattingRoom_id).setValue(chat_room_info); // 내 아이디에 채팅방정보 저장
        databaseReference.child("MemberData").child(fi_id.getText().toString()).child("Chat_room_list").child(ChattingRoom_id).setValue(chat_room_info); // 대화 상대 아이디에 채팅방 정보 저장*/
        startActivity(intent);
    }


    public void onClick_addFriend(View view) {
        Friend_data friend = new Friend_data();
        friend.Friend_id= fi_id.getText().toString();

        if(!isFriend) {
            databaseReference.child("MemberData").child(MainActivity.Login_id).child("FriendList").child(friend.Friend_id).setValue(friend);
            Toast.makeText(getApplicationContext(), fi_id.getText().toString() + "님과 친구가 되었습니다.", Toast.LENGTH_SHORT).show();


        }else {
            Toast.makeText(getApplicationContext(), fi_id.getText().toString() + "님과 이미 친구입니다.", Toast.LENGTH_SHORT).show();

        }


    }


}
