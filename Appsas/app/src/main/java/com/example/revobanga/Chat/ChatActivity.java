package com.example.revobanga.Chat;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import com.example.revobanga.AppCompatPreferenceActivity;
import com.example.revobanga.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity  extends AppCompatPreferenceActivity {

    FirebaseListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chatas);

        final ListView messagesView = (ListView) findViewById(R.id.list);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        mAdapter = new FirebaseListAdapter<Chat>(this, Chat.class, android.R.layout.two_line_list_item, ref){
            @Override
            protected void populateView(View view, Chat chatMessage, int position){
                ((TextView)view.findViewById(android.R.id.text1)).setText(chatMessage.getName());
                ((TextView)view.findViewById(android.R.id.text2)).setText(chatMessage.getText());
            }
        };
        messagesView.setAdapter(mAdapter);

        final EditText mMessage = (EditText) findViewById(R.id.write);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.push().setValue(new Chat("puf", "1234", mMessage.getText().toString()));
                mMessage.setText("");
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mAdapter.cleanup();
    }
}
