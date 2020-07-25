package com.test.chatting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Add_friend extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> memberList = new ArrayList<>();
    MemberListAdapter memberListAdapter = new MemberListAdapter(memberList);
    private GestureDetector gestureDetector; // 다양한 터치 이벤트를 처리하는 클래스, 길게누르기, 두번누르기 등등..


    //Firebase 를 이용합시다!
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    //SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        //memberList.clear();



        Log.i("테스트2", " 여긴되니?");
        //Log.i("친구추가" , 0 + "번 : " + memberList.get(0));

        for(int i=0; i<memberList.size(); i++){
            Log.i("친구추가" , i + "번 : " + memberList.get(i));
        }

        Log.i("테스트3", " 여긴되니?");

      /*  mPrefs = getSharedPreferences("LoginData", MODE_PRIVATE);
        Map<String, ?> login_map = mPrefs.getAll();
        Set<String> keyset = login_map.keySet();
        Iterator<String> Iterator_key = keyset.iterator();

        while(Iterator_key.hasNext()){
            String k = Iterator_key.next();
            if(!k.equals(MainActivity.Login_id)) {
                memberList.add(k);
                Log.i("친구추가", "저장된 아이디 : " + k);
            }

        }*/

        recyclerView = findViewById(R.id.memberlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memberListAdapter);

        gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener() {

            //누르고 뗄 때 한번만 인식하도록 하기위해서
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        // 리사이클러뷰 아이템 터치시 이벤트 구현
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child_view = rv.findChildViewUnder(e.getX(), e.getY());
                if(child_view != null  && gestureDetector.onTouchEvent(e)){
                    int currentposition = rv.getChildAdapterPosition(child_view);
                    Log.i(getClass().toString(), "Touch position : " + currentposition);
                    Intent intent = new Intent(getApplicationContext(), Friend_info.class);
                    intent.putExtra("Friend_Id", memberList.get(currentposition));
                    startActivity(intent);



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


        //Firebase 에서 저장된 정보 가져오기
        databaseReference.child("MemberData").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MemberData getData = dataSnapshot.getValue(MemberData.class);
                if(!MainActivity.Login_id.equals(getData.getMember_id())) {
                    memberList.add(getData.getMember_id());
                    recyclerView.setAdapter(memberListAdapter);
                }

                //Log.i("친구목록" , getData.getMember_id() + " / " + getData.getMember_password());

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

    public void onClick_check(View view) {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();

      /*  Set<String> setkey = getData_toFirebase.keySet();
        memberList.addAll(setkey);*/

    }
}
