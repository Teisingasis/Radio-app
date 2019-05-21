package com.example.revobanga.Chat;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.revobanga.Message;
import com.example.revobanga.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity  extends AppCompatActivity {

    Button sendButton;
    EditText messageArea;
    RecyclerView rvMessage;
     String Username,CurrentUserID;
   FirebaseAuth firebaseAuth;
    private DatabaseReference mFirebaseDatabase;
    DatabaseReference usRef;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chatas);

        rvMessage = (RecyclerView) findViewById(R.id.rv_chat);
        rvMessage.setHasFixedSize(true);
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        sendButton = (Button) findViewById(R.id.btn);
        messageArea = (EditText) findViewById(R.id.write);
;
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
pressed(v);
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        CurrentUserID=firebaseAuth.getCurrentUser().getUid();
        GetUser();

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("chat");
        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(mFirebaseDatabase, Message.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Message, ChatViewHolder>(options) {
            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_chat, viewGroup, false);
                return new ChatViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Message model) {
                holder.tvMessage.setText(model.message);
                holder.tvEmail.setText(model.sender);
            }


        };

        rvMessage.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if( adapter!= null) {
            adapter.stopListening();
        }
    }
public void pressed(View v){
    if (v.getId() == R.id.btn){
        String message = messageArea.getText().toString().trim();
        if (!TextUtils.isEmpty(message)){
            Map<String, Object> param = new HashMap<>();
            param.put("sender", Username);
            param.put("message", message);

            mFirebaseDatabase
                    .push()
                    .setValue(param)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            messageArea.setText("");
                            if(task.isSuccessful()){
                                Log.d("SendMessage", "Success");
                            }else{
                                Log.d("SendMessage", "failed ");
                            }
                        }
                    });
        }
    }

}
public void GetUser(){

    usRef= FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserID);

    usRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()){

                String myUserName=dataSnapshot.child("username").getValue().toString();
                Username=myUserName;
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}
    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView tvEmail, tvMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);

            tvEmail = (TextView) itemView.findViewById(R.id.tv_sender);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }



        }


