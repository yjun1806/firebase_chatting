package com.test.chatting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.chatting.dummy.DummyContent;
import com.test.chatting.dummy.DummyContent.DummyItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class Fragment_myfriend_page extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    static ArrayList<Friend_data> friend_List = new ArrayList<>();
    MyFriendAdapter myFriendAdapter = new MyFriendAdapter(friend_List);
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
    public static Fragment_myfriend_page newInstance(){
        Bundle args = new Bundle();

        Fragment_myfriend_page fragment = new Fragment_myfriend_page();
        fragment.setArguments(args);
        return fragment;
    }


    public Fragment_myfriend_page() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Fragment_myfriend_page newInstance(int columnCount) {
        Fragment_myfriend_page fragment = new Fragment_myfriend_page();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(getClass().toString(), "onCreateView");
        View view = inflater.inflate(R.layout.fragment_friend, container, false);


        recyclerView = view.findViewById(R.id.friend_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myFriendAdapter);

        // Set the adapter
        /*if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyFriendAdapter(DummyContent.ITEMS, mListener));
        }*/
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
                    Intent intent = new Intent(getActivity().getApplicationContext(), Friend_info.class);
                    intent.putExtra("Friend_Id", friend_List.get(currentposition).Friend_id);
                    startActivity(intent);



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


        friend_List.clear();
        //Firebase 에서 저장된 정보 가져오기
        databaseReference.child("MemberData").child(MainActivity.Login_id).child("FriendList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Friend_data getData = dataSnapshot.getValue(Friend_data.class);
                friend_List.add(getData);
                recyclerView.setAdapter(myFriendAdapter);


                //Log.i("친구목록" , getData.getMember_id() + " / " + getData.getMember_password());

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



        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnListFragmentInteractionListener) {
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setAdapter(myFriendAdapter);
    }
}
