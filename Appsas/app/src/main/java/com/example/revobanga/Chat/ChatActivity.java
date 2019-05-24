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
import android.widget.RelativeLayout;
import android.widget.TextView;


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
    private static final int SENT = 0;//ct
    private static final int RECEIVED = 1;//et
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
//                switch (i) {
//                    case SENT:
//                        View userType1 = LayoutInflater.from(viewGroup.getContext())
//                                .inflate(R.layout.my_message, viewGroup, false);
//                        return new ChatViewHolder(userType1);
//                    case RECEIVED:
                        View userType2 = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.item_row_chat, viewGroup, false);
                        return new ChatViewHolder(userType2);
              //  }
             //   return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Message model) {

                if (model.sender.equals(Username)){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.messageText.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                    //  params.addRule(RelativeLayout.LEFT_OF, R.id.id_to_be_left_of);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.messageText.setLayoutParams(params); //causes layout update
                    params = (RelativeLayout.LayoutParams) holder.nameText.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                    //  params.addRule(RelativeLayout.LEFT_OF, R.id.id_to_be_left_of);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.nameText.setLayoutParams(params);
                }
                else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.messageText.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.messageText.setLayoutParams(params);
                    params = (RelativeLayout.LayoutParams) holder.nameText.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.nameText.setLayoutParams(params);
                }
                holder.messageText.setText(model.message);
                holder.nameText.setText(model.sender);

            }
//            @Override
//            public int getItemViewType(int position) {
//                Message msg = getItem(position);
//
//            }

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

        TextView messageText,timeText,nameText;
        String type;

        public ChatViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.tv_message);
          //  timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.tv_sender);
        }
    }


        }


