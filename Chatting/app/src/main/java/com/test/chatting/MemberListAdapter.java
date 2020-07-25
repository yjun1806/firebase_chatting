package com.test.chatting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MemberListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<String> memberList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView member_name;

        ViewHolder(View v){
            super(v);
            member_name = v.findViewById(R.id.member_id);
        }
    }

    public MemberListAdapter(ArrayList<String> memberList) {
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.memberlist_line, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;

        Log.i("친구목록 어댑터", "어댑터터터텉");
        myViewHolder.member_name.setText(memberList.get(position));


    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }
}
