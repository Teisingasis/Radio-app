package com.example.revobanga;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfileFragment extends Fragment {
    TextView username,gender,fullname;
    Button save,cancel;
    View view;
    DatabaseReference profileUserRef;
    FirebaseAuth firebaseAuth;
    String currentUserID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_editprofile, container, false);
        username=view.findViewById(R.id.edit_username);
        gender=view.findViewById(R.id.edit_gender);
        fullname=view.findViewById(R.id.edit_fullname);
        save=view.findViewById(R.id.btnSaveEdit);
        cancel=view.findViewById(R.id.btnCancelEdit);

        firebaseAuth= FirebaseAuth.getInstance();
        currentUserID=firebaseAuth.getCurrentUser().getUid();

        profileUserRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String myUserName=dataSnapshot.child("username").getValue().toString();
                    String myName =dataSnapshot.child("fullname").getValue().toString();
                    String mygender=dataSnapshot.child("Gender").getValue().toString();

                    username.setText(myUserName);
                    fullname.setText(myName);
                    gender.setText(mygender);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        HashMap userMap=new HashMap();
        userMap.put("username",username.getText().toString());
        userMap.put("fullname",fullname.getText().toString());
        userMap.put("Gender",gender.getText().toString());
        profileUserRef.updateChildren(userMap);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }
});

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();

            }
        });

        return view;
    }
}
