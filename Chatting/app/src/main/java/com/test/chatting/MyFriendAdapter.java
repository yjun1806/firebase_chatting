package com.test.chatting;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.chatting.Fragment_myfriend_page.OnListFragmentInteractionListener;
import com.test.chatting.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFriendAdapter extends RecyclerView.Adapter<MyFriendAdapter.ViewHolder> {


    private final ArrayList<Friend_data> friend_list;

    public MyFriendAdapter(ArrayList<Friend_data> friend_list) {
        this.friend_list = friend_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memberlist_line, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(friend_list.get(position).Friend_select){
            holder.friend_id.setText(friend_list.get(position).Friend_id);
            holder.member_background.setCardBackgroundColor(Color.GRAY);

        }else {
            holder.friend_id.setText(friend_list.get(position).Friend_id);
            holder.member_background.setCardBackgroundColor(Color.WHITE);
        }


    }

    @Override
    public int getItemCount() {
        return friend_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView friend_id;
        CardView member_background;

        public ViewHolder(View view) {
            super(view);
            friend_id = view.findViewById(R.id.member_id);
            member_background = view.findViewById(R.id.memberlist_cv);

        }

    }
}
