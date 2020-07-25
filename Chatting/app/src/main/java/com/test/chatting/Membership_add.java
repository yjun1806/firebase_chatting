package com.test.chatting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Membership_add extends Activity {
   /* SharedPreferences login_data;
    SharedPreferences.Editor editor;*/
    EditText ma_id, password, password_check;
    TextView ma_double, ma_pa_warning;

    private int MEMBERSHIP_ADDING_CODE = 123;
    ArrayList<MemberData> memberData;
    HashMap<String, String> getData_toFirebase;

    boolean password_agree;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_add);

        memberData = new ArrayList<>();
        getData_toFirebase = new HashMap<>();

        //Firebase 에서 저장된 정보 가져오기
         databaseReference.child("MemberData").addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 MemberData getData = dataSnapshot.getValue(MemberData.class);
                 getData_toFirebase.put(getData.getMember_id(), getData.getMember_password());
                 memberData.add(getData); // 저장된 개수만큼 호출됨

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



         Log.i("회원가입 테스트", "여긴 실행되나?");
        //로그인 정보를 담을 저장소 생성
        /*login_data = getSharedPreferences("LoginData", MODE_PRIVATE);
        editor = login_data.edit();*/
        password_agree = false;

        //뷰 아이디값 연결
        bind_layout();

        ma_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input_id = s.toString();

                if (getData_toFirebase.get(input_id) == null) {
                    ma_double.setText("생성 가능한 아이디 입니다.");
                    ma_double.setTextColor(Color.GREEN);
                } else {
                    ma_double.setText("이미 있는 아이디입니다.");
                    ma_double.setTextColor(Color.RED);
                }
                Log.i("회원가입 중복체크", "저장된 키값 : " + databaseReference.child("MemberData").child(input_id).getKey());
                /*if(databaseReference.child("MemberData").child(input_id) == null){
                    Log.i("회원가입 중복체크", "저장된 키값 true: " + databaseReference.child("MemberData").child(input_id).getKey());
                    ma_double.setText("생성 가능한 아이디 입니다.");
                    ma_double.setTextColor(Color.GREEN);
                }else {
                    Log.i("회원가입 중복체크", "저장된 키값 false: " + databaseReference.child("MemberData").child(input_id).getKey());
                    ma_double.setText("이미 있는 아이디입니다.");
                    ma_double.setTextColor(Color.RED);
                }*/
            }
        });

        password_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String password_ch = s.toString();
                if(password.getText().toString().equals(password_ch)){

                    ma_pa_warning.setText("비밀번호가 일치합니다");
                    ma_pa_warning.setTextColor(Color.GREEN);
                    password_agree = true;
                }else {
                    ma_pa_warning.setText("비밀번호가 일치하지 않습니다.\n다시 입력해 주세요.");
                    ma_pa_warning.setTextColor(Color.RED);

                }
            }
        });

    }

    private void bind_layout() {
        ma_id = findViewById(R.id.ma_ID);
        password = findViewById(R.id.ma_password);
        password_check = findViewById(R.id.ma_password_check);
        ma_double = findViewById(R.id.ma_double);
        ma_pa_warning = findViewById(R.id.ma_pass_warning);
    }


    public void onClick_membership_adding(View view) {
        if(ma_id.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else {
            if (password_agree) {
                Intent intent = getIntent();
                intent.putExtra("login_id", ma_id.getText().toString());
                intent.putExtra("login_password", password.getText().toString());
                //save_id_to_sharedpreference();
                setResult(RESULT_OK, intent);
                Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();

                MemberData savedata = new MemberData(ma_id.getText().toString(), password.getText().toString());
                memberData.add(savedata);
                databaseReference.child("MemberData").child(ma_id.getText().toString()).setValue(savedata);

                finish();
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();


            }
        }
    }

    /*private void save_id_to_sharedpreference() {
        editor.putString(ma_id.getText().toString(), password.getText().toString());
        editor.commit();
    }*/

    public void onClick_ma_cancel(View view) {
        finish();
    }
}
