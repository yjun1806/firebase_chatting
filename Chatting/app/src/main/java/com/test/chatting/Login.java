package com.test.chatting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class Login extends Activity {

    private int MEMBERSHIP_ADDING_CODE = 123;
    EditText login_id, login_password;
    CheckBox id_remember, auto_login;
    Button login;
    //SharedPreferences login_data;
    public static String ID_token = null;
    Toast toast;


    //Firebase 를 이용합시다!
    HashMap<String, String> getData_toFirebase =  new HashMap<>(); // 멤버 데이터를 받을 변수
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_id = findViewById(R.id.ln_login_id);
        login_password = findViewById(R.id.ln_login_password);
        id_remember = findViewById(R.id.ln_id_remember);
        auto_login = findViewById(R.id.ln_autologin);
        login = findViewById(R.id.ln_login);
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);



        //login_data = getSharedPreferences("LoginData", MODE_PRIVATE);

        //FCM 토큰 관련 부분





        //자동로그인이 체크되면 아이디 기억하기도 자동으로 체크되도록
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    id_remember.setChecked(true);
                }
            }
        });

        //Firebase 에서 저장된 정보 가져오기
        databaseReference.child("MemberData").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MemberData getData = dataSnapshot.getValue(MemberData.class);
                getData_toFirebase.put(getData.getMember_id(), getData.getMember_password());
                if(auto_login.isChecked()){
                    if(getData.getMember_id()!= null && getData.getMember_id().equals(login_id.getText().toString())){
                        login.callOnClick();
                    }
                }
                Log.i("로그인", "정보가져오는중 " + getData_toFirebase.size());
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("로그인", "클릭되었음");
                if(getData_toFirebase.get(login_id.getText().toString()) != null) {
                    Log.i("로그인", "아이디있네?");
                    if (getData_toFirebase.get(login_id.getText().toString()).equals(login_password.getText().toString())) {
                        toast.setText("로그인 성공!");
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        //Log.i("로그인시 키값 받아요", String.valueOf(databaseReference.child("MemberData")));
                       /* intent.putExtra("Login_id", login_id.getText().toString());
                        intent.putExtra("Login_password", login_password.getText().toString());*/

                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                        SharedPreferences mPref = getSharedPreferences("LoginData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPref.edit();
                        editor.putString("LoginId", login_id.getText().toString());
                        editor.putString("LoginPassword", login_password.getText().toString());
                        editor.putBoolean("IDremember", id_remember.isChecked()); // 체크시 트루
                        editor.putBoolean("AutoLogin", auto_login.isChecked());
                        editor.commit();

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "아이디가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == MEMBERSHIP_ADDING_CODE){
                Log.i("로그인화면", "회원가입 완료");
                login_id.setText(data.getStringExtra("login_id"));
                login_password.setText(data.getStringExtra("login_password"));

            }
        }
    }

    public void membership_add(View view) {
        Intent intent = new Intent(getApplicationContext(), Membership_add.class);
        startActivityForResult(intent, MEMBERSHIP_ADDING_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();

            SharedPreferences mPref = getSharedPreferences("LoginData", MODE_PRIVATE);
            if(mPref != null){
                id_remember.setChecked(mPref.getBoolean("IDremember", false));
                auto_login.setChecked(mPref.getBoolean("AutoLogin", false));

                if(auto_login.isChecked()){
                    Log.i("로그인", "자동로그인합니다");
                    toast.setText("자동로그인 중입니다.");
                    toast.show();
                    login_id.setText(mPref.getString("LoginId", null));
                    login_password.setText(mPref.getString("LoginPassword", null));
                }else if(!auto_login.isChecked() && id_remember.isChecked()){
                    login_id.setText(mPref.getString("LoginId", null));
                }
            }


    }
}
