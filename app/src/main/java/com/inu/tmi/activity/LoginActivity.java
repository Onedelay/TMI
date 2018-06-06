package com.inu.tmi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inu.tmi.R;

/**
 * Created by bmj on 2018-06-05.
 */

public class LoginActivity extends AppCompatActivity {

    EditText ID;
    EditText PW;
    Button LoginBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        ID = (EditText)findViewById(R.id.IDtxt);
        PW = (EditText)findViewById(R.id.PWtxt);

        LoginBTN = (Button)findViewById(R.id.loginbtn);
        LoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID.getText().equals(null) )
                {
                    Toast.makeText(getApplicationContext(),"ID 입력해라", Toast.LENGTH_SHORT).show();
                }
                else if(PW.getText().equals(null))
                {
                    Toast.makeText(getApplicationContext(),"PW 입력해라", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //TODO
                    //id pw db에서 비교 후 MainActivity로
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
