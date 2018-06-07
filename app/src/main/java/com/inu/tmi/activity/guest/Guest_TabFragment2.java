package com.inu.tmi.activity.guest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inu.tmi.R;

/**
 * Created by bmj on 2018-06-05.
 */

public class Guest_TabFragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.guest_tabfragment2,container,false);

        ListView guestlist = (ListView)view.findViewById(R.id.guest_listview);
        ListViewAdapter listViewAdapter;
        guestlist.setAdapter(listViewAdapter = new ListViewAdapter(view.getContext(),R.layout.guest_tabfragment2));

        //예시 추가
        listViewAdapter.addListitem(ContextCompat.getDrawable(view.getContext(),R.drawable.back_arrow),"인천대 헤헷","집가고 싶어요","100"+"M");
        //TODO
        //서버에서 데이터 갖고와서 표시하기
        return view;
    }
}