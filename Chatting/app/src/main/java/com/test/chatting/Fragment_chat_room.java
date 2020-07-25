package com.test.chatting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.chatting.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class Fragment_chat_room extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Chat_room_info> chatting_room_list = new ArrayList<>();
    Chat_Room_Adapter chat_room_adapter = new Chat_Room_Adapter(chatting_room_list);
    private GestureDetector gestureDetector; // 다양한 터치 이벤트를 처리하는 클래스, 길게누르기, 두번누르기 등등..


    //Firebase 를 이용합시다!
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();



    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public static Fragment_chat_room newInstance(){
        Bundle args = new Bundle();

        Fragment_chat_room fragment = new Fragment_chat_room();
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_chat_room() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Fragment_chat_room newInstance(int columnCount) {
        Fragment_chat_room fragment = new Fragment_chat_room();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_room, container, false);
        recyclerView = view.findViewById(R.id.chat_room_re);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chat_room_adapter);



        gestureDetector = new GestureDetector(getActivity().getApplicationContext(),new GestureDetector.SimpleOnGestureListener() {

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
                    Intent intent = new Intent(getActivity().getApplicationContext(), Chatting_Window.class);
                    intent.putExtra("Chatting_room_id", chatting_room_list.get(currentposition));
                    intent.putExtra("currentposition", currentposition);
                    startActivityForResult(intent, 333);

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

        databaseReference.child("MemberData").child(MainActivity.Login_id).child("Chat_room_list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("채팅목록", "추가가 있어요");
                Chat_room_info chat_room_info = dataSnapshot.getValue(Chat_room_info.class);
                chatting_room_list.add(chat_room_info);
                chat_room_adapter.notifyDataSetChanged();
                //recyclerView.setAdapter(chat_room_adapter);


                //Log.i("친구목록" , getData.getMember_id() + " / " + getData.getMember_password());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("채팅목록", "변화가 있어요");
                Chat_room_info chat_room_info = dataSnapshot.getValue(Chat_room_info.class);
                int i=0;
                for(; i<chatting_room_list.size(); i++){
                    if(chatting_room_list.get(i).Chatting_room_id.equals(chat_room_info.Chatting_room_id)){
                        chatting_room_list.set(i, chat_room_info);
                        break;
                    }
                }
                chat_room_adapter.notifyItemChanged(i);
                //recyclerView.setAdapter(chat_room_adapter);
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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if(requestCode == 333){
            //Log.i("채팅방 프레그먼트", "코드 333 억셉트 : " + data.getIntExtra("currentposition", 0) +" / "+ data.getStringExtra("Lastmessage") + " , " + data.getStringExtra("LastmessageTime"));
            /*chatting_room_list.get(data.getIntExtra("currentposition", 0)).last_message
                    = data.getStringExtra("Lastmessage");
            chatting_room_list.get(data.getIntExtra("currentposition", 0)).last_message_time
                    = data.getStringExtra("LastmessageTime");
            chat_room_adapter.notifyItemChanged(data.getIntExtra("currentposition", 0));*/


            if(resultCode == 112){

            }

        //}

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }



}
