package com.inu.tmi.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.SharedPrefManager;
import com.inu.tmi.activity.guest.Guest_SuccessActivity;
import com.inu.tmi.api.ServerRequestBody;
import com.inu.tmi.api.TMIServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

        if (getArguments() != null) {
            name.setText("[" + getArguments().getString("name") + "]");
            depart.setText(getArguments().getString("depart"));
            dest.setText(getArguments().getString("dest"));
            msg.setText(getArguments().getString("msg"));

            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TMIServer.getInstance().addMem(SharedPrefManager.preferencesLoadString(getContext(), "token"), getArguments().getInt("roomId"), new Callback<ServerRequestBody>() {
                        @Override
                        public void onResponse(Call<ServerRequestBody> call, Response<ServerRequestBody> response) {
                            if (response.body() != null) {
                                Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                SharedPrefManager.preferenceSave(getContext(),"roomnum",getArguments().getInt("roomId"));
                                Intent it = new Intent(getContext(), Guest_SuccessActivity.class);
                                getContext().startActivity(it);
                                //TODO intent 이동
                                getDialog().dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerRequestBody> call, Throwable t) {
                            Toast.makeText(getContext(), "서버 연결 안됨", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        return builder.create();
    }
}
