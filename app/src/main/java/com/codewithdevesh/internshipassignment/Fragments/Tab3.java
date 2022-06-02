package com.codewithdevesh.internshipassignment.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codewithdevesh.internshipassignment.Activities.LoginActivity;
import com.codewithdevesh.internshipassignment.R;
import com.codewithdevesh.internshipassignment.Utils.UserModel;
import com.codewithdevesh.internshipassignment.databinding.FragmentTab3Binding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tab3 extends Fragment {
    private FragmentTab3Binding binding;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTab3Binding.inflate(inflater, container, false);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(),gso);

        UserDetailsFromGoogle();
        UserDetailsFromDB();
        return binding.getRoot();
    }

    private void UserDetailsFromDB() {
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        if(!uid.isEmpty()){
            reference.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel model = snapshot.getValue(UserModel.class);
                    binding.name.setText(model.getName());
                    binding.email.setText(model.getEmail());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Error Loading Data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        binding.signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.signOut();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                        getActivity().finish();
                    }
                });

            }
        });
    }

    private void UserDetailsFromGoogle(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String id = acct.getId();
            binding.name.setText(personName);
            binding.email.setText(personEmail);
        }
    }
}