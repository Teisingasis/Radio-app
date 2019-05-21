package com.example.revobanga;

import android.content.Intent;
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

public class ProfileFragment extends Fragment {
    TextView username,gender,fullname;
    Button edit,changepassword;
    View view;

    DatabaseReference profileUserRef;
    FirebaseAuth firebaseAuth;
    String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_viewprofile, container, false);
username=view.findViewById(R.id.view_username);
gender=view.findViewById(R.id.view_gender);
fullname=view.findViewById(R.id.view_fullname);
edit=view.findViewById(R.id.btnEditProfile);
changepassword=view.findViewById(R.id.btnChangePassword);

firebaseAuth=FirebaseAuth.getInstance();
currentUserID=firebaseAuth.getCurrentUser().getUid();



edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditProfileFragment()).commit();
    }
});
changepassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(),NewPasswordActivity.class));
    }
});

        return view;
    }
}
