package com.inu.tmi.activity.guest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inu.tmi.R;

import java.util.ArrayList;

/**
 * Created by bmj on 2018-06-07.
 */

public class ListViewAdapter extends BaseAdapter{

    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>();

    ListViewAdapter(Context context, int resourceId){}
    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public ListViewItem getItem(int i) {
        return listViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.guest_listview,viewGroup,false);
        }

       // final ImageView userIcon = (ImageView)view.findViewById(R.id.userimage);
        final TextView from = (TextView)view.findViewById(R.id.from);
        final TextView to = (TextView)view.findViewById(R.id.to);
        final TextView distance = (TextView)view.findViewById(R.id.distance);


        final ListViewItem list = listViewItems.get(i);

        from.setText(list.getFrom());
        to.setText(list.getTo());
        distance.setText(list.getDst()+"m");

        return view;
    }


    //이 함수 호출하여 리스트뷰에 추가하기!~~
    public void addListitem(String From , String To, String Distance)
    {
        ListViewItem item = new ListViewItem();

        //item.setUserIcon(Icon);
        item.setFrom(From);
        item.setTo(To);
        item.setDst(Distance);

        listViewItems.add(item);
    }
}
