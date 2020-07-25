package com.test.chatting;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;


public class FragmentAdapter extends FragmentStatePagerAdapter {

    public FragmentAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Fragment_myfriend_page.newInstance();
            case 1:
                return Fragment_chat_room.newInstance();
            case 2:
                Fragment_infomation fragment_infomation = new Fragment_infomation();
                return fragment_infomation;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "친구목록";
            case 1:
                return "채팅목록";
            case 2:
                return "정보";
                default:
                    return null;
        }
    }
}
