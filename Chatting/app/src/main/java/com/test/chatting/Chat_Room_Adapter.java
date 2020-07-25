package com.test.chatting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.chatting.Fragment_chat_room.OnListFragmentInteractionListener;
import com.test.chatting.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class Chat_Room_Adapter extends RecyclerView.Adapter<Chat_Room_Adapter.ViewHolder> {


    private final ArrayList<Chat_room_info> chatting_room_list;

    public Chat_Room_Adapter(ArrayList<Chat_room_info> chatting_room_list) {
        this.chatting_room_list = chatting_room_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_room_line, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(chatting_room_list.get(position).isthis_chatroom_group){ // 그룹채팅방인 경우
            String id = chatting_room_list.get(position).group_id.get(0);
            for(int j=1; j< chatting_room_list.get(position).group_id.size(); j++){
                id += ", " + chatting_room_list.get(position).group_id.get(j);
            }
            holder.friend_id.setText("그룹채팅방 : ");
            holder.trash.setText(id);
        }else {
            if (chatting_room_list.get(position).id1.equals(MainActivity.Login_id)) {
                holder.friend_id.setText(chatting_room_list.get(position).id2);
            } else {
                holder.friend_id.setText(chatting_room_list.get(position).id1);
            }
        }
        holder.last_text.setText(chatting_room_list.get(position).last_message);
        holder.last_time.setText(chatting_room_list.get(position).last_message_time);
    }

    @Override
    public int getItemCount() {
        return chatting_room_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView friend_id, last_time, last_text, trash;
        ImageView iv;

        public ViewHolder(View view) {
            super(view);

            friend_id = view.findViewById(R.id.cr_friend_id);
            last_time = view.findViewById(R.id.cr_last_time);
            last_text = view.findViewById(R.id.cr_last_text);
            trash = view.findViewById(R.id.trash);
        }


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
