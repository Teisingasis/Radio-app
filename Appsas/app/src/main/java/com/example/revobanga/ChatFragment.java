package com.example.revobanga;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cenkgun.chatbar.ChatBarView;

public class ChatFragment extends Activity {
    TextView textView;
    ChatBarView chatBarView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatas);

        textView = findViewById((R.id.textView));
        chatBarView = findViewById(R.id.chatBar);
        chatBarView.setMessageBoxHint("Enter your message");

        chatBarView.setSendClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(chatBarView.getMessageText());
            }
        });
    }
}
