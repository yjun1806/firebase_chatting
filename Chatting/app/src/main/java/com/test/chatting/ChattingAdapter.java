package com.test.chatting;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.Calendar;

public class ChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener{
        public void onItemClick(View view, int postition);
    }

    private OnItemClickListener onItemClickListener;

    private final ArrayList<ChatData> chatting_message;

    public ChattingAdapter(ArrayList<ChatData> chatting_message, OnItemClickListener onItemClickListener) {
        this.chatting_message = chatting_message;
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mesg_text, time, sender_id;
        ImageView left_image, right_image;
        LinearLayout whole_message, message_line;
        CardView cv;

        ViewHolder(View v){
            super(v);
            mesg_text = v.findViewById(R.id.mesg_Text);
            time = v.findViewById(R.id.ch_time);
            left_image = v.findViewById(R.id.ch_left_image);
            sender_id = v.findViewById(R.id.ch_id);
            whole_message = v.findViewById(R.id.ch_whole_line);
            cv = v.findViewById(R.id.chat_bubble);
            message_line = v.findViewById(R.id.message_line);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_line, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;


        // 자신인 경우 - 오른쪽 정렬, 왼쪽이미지, 오른쪽 시간 안보임
        if(chatting_message.get(position).getUserName().equals(MainActivity.Login_id)){
            viewHolder.mesg_text.setText(chatting_message.get(position).getMessage());
            viewHolder.mesg_text.setGravity(Gravity.END);
            viewHolder.message_line.setGravity(Gravity.END);

            viewHolder.sender_id.setVisibility(View.INVISIBLE); // 자신의 아이디 안보임
            viewHolder.time.setText(chatting_message.get(position).sending_time);
            viewHolder.whole_message.setGravity(Gravity.END);

            viewHolder.left_image.setVisibility(View.GONE);

            viewHolder.cv.setBackgroundColor(Color.parseColor("#FFFFE95C"));

            // 상대방인경우 - 왼쪽 정렬, 오른쪽이미지, 왼쪽 시간 안보임
        }else {
            viewHolder.mesg_text.setText(chatting_message.get(position).getMessage());
            viewHolder.sender_id.setGravity(Gravity.START);
            viewHolder.sender_id.setText(chatting_message.get(position).getUserName());
            viewHolder.time.setText(chatting_message.get(position).sending_time);
            viewHolder.whole_message.setGravity(Gravity.START);
            viewHolder.cv.setBackgroundColor(Color.WHITE);

            //상대방 이미지를 클릭했을 시 상대방 정보를 띄워준다.
            viewHolder.left_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return chatting_message.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
