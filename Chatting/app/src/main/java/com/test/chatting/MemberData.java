package com.test.chatting;


import java.util.ArrayList;

public class MemberData {
    private String member_id, member_password;
    //private ArrayList<String> friend_list;

    public MemberData(){
        //friend_list = new ArrayList<>();

    }

    public MemberData(String id, String password){
        this.member_id = id;
        this.member_password = password;
        //friend_list = new ArrayList<>();
    }

    public void add_friend(String friend_id){
        //friend_list.add(friend_id);
    }

    public String getMember_id(){
        return member_id;
    }

    public String getMember_password(){
        return member_password;
    }

}
