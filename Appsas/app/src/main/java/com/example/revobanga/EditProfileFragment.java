package com.example.revobanga;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
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
    Button save,cancel,choice;
    View view;
    DatabaseReference profileUserRef;
    FirebaseAuth firebaseAuth;
    String currentUserID;
    int checkedItem=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_editprofile, container, false);
        username=view.findViewById(R.id.edit_username);
        gender=view.findViewById(R.id.edit_gender);
        choice=view.findViewById(R.id.btnEditGender);
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
                    if(mygender.equals("Female")){
                        checkedItem=1;
                    }
                    else if(mygender.equals("Male")){
                        checkedItem=2;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Gender");
// add a radio button list
                final String[] Gender = {"none","Female", "Male"};
                builder.setSingleChoiceItems(Gender, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
// add OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem=((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        switch(checkedItem){
                            case 1:
                                gender.setText(Gender[1]);
                                break;
                            case 2:
                                gender.setText(Gender[2]);
                                break;
                            default:
                                gender.setText(Gender[0]);
                        }
                        // user clicked OK
                    }
                });
                builder.setNegativeButton("Cancel", null);
// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        MainActivity.setHideKeyboardOnTouch(getActivity(),view.findViewById(R.id.editprofileparent));
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
