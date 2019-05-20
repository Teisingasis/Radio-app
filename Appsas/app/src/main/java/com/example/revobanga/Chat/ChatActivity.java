package com.example.revobanga.Chat;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import com.example.revobanga.AppCompatPreferenceActivity;
import com.example.revobanga.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity  extends AppCompatPreferenceActivity {

    FirebaseListAdapter mAdapter;
    ListView messagesView;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chatas);

         messagesView = (ListView) findViewById(R.id.list);

        ref = FirebaseDatabase.getInstance().getReference().child("Messages");

        FirebaseListOptions<Chat> options =
                new FirebaseListOptions.Builder<Chat>()
                        .setQuery(ref, Chat.class)
                        .setLayout(android.R.layout.simple_list_item_1)
                        .build();
        mAdapter = new FirebaseListAdapter<Chat>(options){
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
       // mAdapter.cleanup();
    }
}
