package com.example.revobanga;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class ChatFragment extends Fragment {
    private View view;
    private MemberData member;
    private EditText bar;
    private String msg;
    Button send;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_chatas, container, false);
        member = MainActivity.member;
        bar=view.findViewById(R.id.write);
        send = view.findViewById(R.id.btn);
        send();
        return view;
    }
    public void send(){

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg=bar.getText().toString();
                bar.getText().clear();
            }
        });
    }
}
