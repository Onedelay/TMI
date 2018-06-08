package com.inu.tmi.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.inu.tmi.R;
import com.inu.tmi.activity.guest.Guest_TabFragment1;
import com.inu.tmi.activity.guest.Guest_TabFragment2;

public class ApplyFragment extends DialogFragment {
    private TextView name;
    private TextView depart;
    private TextView dest;
    private TextView msg;
    private TextView time;
    private Button applyButton;
    private TextView remains;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.fragment_item, null);
        builder.setView(rootView);

        name = rootView.findViewById(R.id.name);
        depart = rootView.findViewById(R.id.textDepart);
        dest = rootView.findViewById(R.id.textArrive);
        msg = rootView.findViewById(R.id.message);
        time = rootView.findViewById(R.id.strTime);
        applyButton = rootView.findViewById(R.id.buttonApply);
//        remains = rootView.findViewById(R.id.);

        if(getArguments() != null){
            name.setText("["+getArguments().getString("name")+"]");
            depart.setText(getArguments().getString("depart"));
            dest.setText(getArguments().getString("dest"));
            msg.setText(getArguments().getString("msg"));
        }

        return builder.create();
    }
}
