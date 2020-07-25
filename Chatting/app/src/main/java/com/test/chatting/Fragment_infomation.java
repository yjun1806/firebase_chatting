package com.test.chatting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_infomation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_infomation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_infomation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button friend_button, logout;
    static TextView tv;

    public Fragment_infomation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_infomation.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_infomation newInstance(String param1, String param2) {
        Fragment_infomation fragment = new Fragment_infomation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.i("프래그먼트 정보" , "onCreate");

    }

    void handlerMEssage(Message msg){
        tv.setText(msg.arg1);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("프래그먼트 정보" , "onCreateView");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_infomation, container, false);

        //tv = view.findViewById(R.id.Hendler_test);
        friend_button = view.findViewById(R.id.add_friend);
        logout = view.findViewById(R.id.logout);
        friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Add_friend.class);
                startActivityForResult(intent, 333);
            }
        });
        TextView id = view.findViewById(R.id.if_login_id);
        id.setText(MainActivity.Login_id + "님 안녕하세요.");


        //로그아웃 버튼을 누르는 경우
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPref = getActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPref.edit();
                editor.clear();
                //editor.putBoolean("AutoLogin", false); // 자동로그인 해제
                editor.commit();
                getActivity().stopService(new Intent(getActivity().getApplicationContext(), UI_update_service.class));
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
