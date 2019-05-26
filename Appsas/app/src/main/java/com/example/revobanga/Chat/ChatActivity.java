package com.example.revobanga.Chat;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    LinearLayoutManager layoutManager=new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chatas);

        layoutManager.setStackFromEnd(true);
        layoutManager.setSmoothScrollbarEnabled(true);

        rvMessage = (RecyclerView) findViewById(R.id.rv_chat);
        rvMessage.setHasFixedSize(true);

        rvMessage.setLayoutManager(layoutManager);
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
                        View userType2 = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.item_row_chat, viewGroup, false);
                        return new ChatViewHolder(userType2);
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Message model) {

                if (model.sender.equals(Username)){
                    //message
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.messageText.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.messageText.setLayoutParams(params); //causes layout update
                    holder.messageText.setBackgroundResource(R.drawable.chatoutgoing);
                    PercentRelativeLayout.LayoutParams p= (PercentRelativeLayout.LayoutParams) holder.messageText.getLayoutParams();
                    PercentLayoutHelper.PercentLayoutInfo info = p.getPercentLayoutInfo();
                    info.leftMarginPercent=35*0.01f;
                    info.rightMarginPercent=0;
                    holder.messageText.setLayoutParams(p);

                    // sender
                    params = (RelativeLayout.LayoutParams) holder.nameText.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.nameText.setLayoutParams(params);


                    // time
                    params = (RelativeLayout.LayoutParams) holder.timeText.getLayoutParams();
                      params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.tv_message);
                      params.addRule(RelativeLayout.ALIGN_LEFT,0);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.timeText.setLayoutParams(params);
                }
                else {
                    //message
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.messageText.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.messageText.setLayoutParams(params);
                    holder.messageText.setBackgroundResource(R.drawable.chatincoming);
                    PercentRelativeLayout.LayoutParams p= (PercentRelativeLayout.LayoutParams) holder.messageText.getLayoutParams();
                    PercentLayoutHelper.PercentLayoutInfo info = p.getPercentLayoutInfo();
                    info.rightMarginPercent=35*0.01f;
                    info.leftMarginPercent=0;
                    holder.messageText.setLayoutParams(p);

                    //sender
                    params = (RelativeLayout.LayoutParams) holder.nameText.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.nameText.setLayoutParams(params);

                    //time
                    params = (RelativeLayout.LayoutParams) holder.timeText.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_LEFT, R.id.tv_message);
                    params.addRule(RelativeLayout.ALIGN_RIGHT,0);
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.timeText.setLayoutParams(params);
                }
                holder.messageText.setText(model.message);
                holder.nameText.setText(model.sender);
                holder.timeText.setText(model.time);

            }
        };

        rvMessage.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                rvMessage.smoothScrollToPosition(  adapter.getItemCount());
            }
        });
        messageArea.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String time = simpleDateFormat.format(currentTime.getTime());
        if (!TextUtils.isEmpty(message)){
            Map<String, Object> param = new HashMap<>();
            param.put("sender", Username);
            param.put("message", message);
            param.put("time",time);

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

        public ChatViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.tv_message);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.tv_sender);
        }
    }


        }


