package com.test.chatting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    public static String Login_id = null;
    public static String Login_password = null;
    public static String now_watching_chat_room_id = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);


        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.main_viewpager);
        mViewPager.setAdapter(fragmentAdapter);

        TabLayout mTab = findViewById(R.id.tabLayout);
        mTab.setupWithViewPager(mViewPager);

       // Intent intent = getIntent();
        /*Login_id = intent.getStringExtra("Login_id");
        Login_password = intent.getStringExtra("Login_password");*/


        SharedPreferences mPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        if(mPref != null){
            Login_id = mPref.getString("LoginId", null);
            Login_password = mPref.getString("LoginPassword", Login_password);
            setTitle(Login_id + "님 안녕하세요!");
        }

        Log.d("메인액티비티", "토큰 : " + Login.ID_token);
        startService(new Intent(getApplicationContext(), UI_update_service.class));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.groupchat:

                Intent intent = new Intent(getApplicationContext(), Group_chat_invite.class);
                startActivity(intent);

                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        /*SharedPreferences mPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();

        editor.putString("LoginId", Login_id);
        editor.putString("LoginPassword",Login_password );
        editor.commit();*/
    }
}
